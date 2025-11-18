package top.toobee.spring.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import jakarta.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DynamicTtlCache {
    // 存粗每个key的过期时间
    private final ConcurrentHashMap<String, Long> ttlMap = new ConcurrentHashMap<>();

    private Cache<@NonNull String, Object> cache;

    @PostConstruct
    public void init() {
        this.cache =
                Caffeine.newBuilder()
                        .scheduler(com.github.benmanes.caffeine.cache.Scheduler.systemScheduler())
                        .expireAfter(
                                new Expiry<@NonNull String, @NonNull Object>() {
                                    @Override
                                    public long expireAfterCreate(
                                            String key, Object value, long currentTime) {
                                        // 返回该key的过期时间(纳秒)
                                        Long ttlNanos = ttlMap.get(key);
                                        return ttlNanos != null
                                                ? ttlNanos
                                                : TimeUnit.MINUTES.toNanos(10); // 默认10分钟
                                    }

                                    @Override
                                    public long expireAfterUpdate(
                                            String key,
                                            Object value,
                                            long currentTime,
                                            long currentDuration) {
                                        return currentDuration; // 更新时不改变过期时间
                                    }

                                    @Override
                                    public long expireAfterRead(
                                            String key,
                                            Object value,
                                            long currentTime,
                                            long currentDuration) {
                                        return currentDuration; // 读取时不改变
                                    }
                                })
                        .build();
    }

    // 放入缓存，并指定该key的过期时间(秒)
    public void put(String key, Object value, long expireSeconds) {
        long nanos = TimeUnit.SECONDS.toNanos(expireSeconds);
        ttlMap.put(key, nanos);
        cache.put(key, value);
    }

    // 从缓存中获取key对应的值
    public Object get(String key) {
        return cache.getIfPresent(key);
    }

    // 删除缓存中的key
    public void remove(String key) {
        cache.invalidate(key);
        ttlMap.remove(key);
    }
}
