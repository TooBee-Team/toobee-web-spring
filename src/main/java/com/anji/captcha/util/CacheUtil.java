package com.anji.captcha.util;

import jakarta.annotation.Nullable;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Lazy(false)
public final class CacheUtil {
    private static final Logger logger = LoggerFactory.getLogger(CacheUtil.class);
    private static final Map<String, Object> CACHE_MAP = new ConcurrentHashMap<String, Object>();
    private static Integer CACHE_MAX_NUMBER = 1000;
    private static volatile ScheduledExecutorService scheduledExecutor;
    private static final AtomicBoolean initialized = new AtomicBoolean(false);

    /**
     * 初始化。修复了原方法中，定时任务线程无法正常关闭的问题。
     * @param cacheMaxNumber 缓存最大个数
     * @param second 定时任务 秒执行清除过期缓存
     */
    public static void init(int cacheMaxNumber, long second) {
        if (!initialized.compareAndSet(false, true))
            return;
        CACHE_MAX_NUMBER = cacheMaxNumber;
        if (second > 0L) {
            scheduledExecutor = new ScheduledThreadPoolExecutor(1, r -> {
                final var t = new Thread(r,"thd-captcha-cache-clean");
                t.setDaemon(true);
                return t;
            }, new ThreadPoolExecutor.CallerRunsPolicy());
            final var _ = scheduledExecutor.scheduleAtFixedRate(CacheUtil::refresh, 10, second, TimeUnit.SECONDS);
        }
    }

    @PreDestroy
    public void destroy() {
        logger.info("CacheUtil shutting down...");
        if (Objects.nonNull(scheduledExecutor)) {
            clear();
            scheduledExecutor.shutdownNow();
            try {
                if (scheduledExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                    logger.info("CacheUtil scheduledExecutor terminated successfully.");
                } else {
                    logger.warn("CacheUtil scheduledExecutor did not terminate in the expected time.");
                    Thread.currentThread().interrupt();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        initialized.set(false);
    }

    /**
     * 缓存刷新,清除过期数据
     */
    public static void refresh() {
        logger.debug("local缓存刷新,清除过期数据");
        for (String key : CACHE_MAP.keySet()) {
            exists(key);
        }
    }


    public static void set(String key, String value, long expiresInSeconds) {
        //设置阈值，达到即clear缓存
        if (CACHE_MAP.size() > CACHE_MAX_NUMBER * 2) {
            logger.info("CACHE_MAP达到阈值，clear map");
            clear();
        }
        CACHE_MAP.put(key, value);
        if(expiresInSeconds >0) {
            CACHE_MAP.put(key + "_HoldTime", System.currentTimeMillis() + expiresInSeconds * 1000);//缓存失效时间
        }
    }

    public static void delete(String key) {
        CACHE_MAP.remove(key);
        CACHE_MAP.remove(key + "_HoldTime");
    }

    public static boolean exists(String key) {
        final var cacheHoldTime = (Long) CACHE_MAP.get(key + "_HoldTime");
        if (cacheHoldTime == null || cacheHoldTime == 0L) {
            return false;
        }
        if (cacheHoldTime < System.currentTimeMillis()) {
            delete(key);
            return false;
        }
        return true;
    }


    public static @Nullable String get(String key){
        if (exists(key)) {
            return (String)CACHE_MAP.get(key);
        }
        return null;
    }

    /**
     * 删除所有缓存
     */
    public static void clear() {
        logger.debug("have clean all key !");
        CACHE_MAP.clear();
    }

    /**
     * 设置过期时间
     */
    public static void setExpire(String key, long expiresInSeconds) {
        CACHE_MAP.put(key + "_HoldTime", System.currentTimeMillis() + expiresInSeconds * 1000);//缓存失效时间
    }
}
