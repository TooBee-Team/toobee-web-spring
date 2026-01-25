package top.toobee.spring.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.Scheduler;
import java.time.Duration;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

@Component
public final class DynamicTtlCache {
    private final ConcurrentHashMap<String, Set<String>> nameTokensMap = new ConcurrentHashMap<>();
    private final Cache<@NonNull String, @NonNull Duration> cache =
            Caffeine.newBuilder()
                    .maximumSize(20000)
                    .scheduler(Scheduler.systemScheduler())
                    .expireAfter(Expiry.<@NonNull String, @NonNull Duration>creating((_, v) -> v))
                    .build();

    public boolean hasToken(String jwt) {
        return cache.getIfPresent(jwt) != null;
    }

    public void addToken(String name, String jwt, Duration duration) {
        nameTokensMap.compute(
                name,
                (_, set) -> {
                    if (set == null) set = ConcurrentHashMap.newKeySet();
                    set.add(jwt);
                    return set;
                });
        cache.put(jwt, duration);
    }

    public void removeToken(String name, String jwt) {
        nameTokensMap.computeIfPresent(
                name,
                (_, set) -> {
                    set.remove(jwt);
                    return set.isEmpty() ? null : set;
                });
        cache.invalidate(jwt);
    }

    public void removeAll(String name) {
        final var set = nameTokensMap.remove(name);
        if (set != null) cache.invalidateAll(set);
    }
}
