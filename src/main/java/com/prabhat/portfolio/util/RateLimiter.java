package com.prabhat.portfolio.util;

import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.prabhat.portfolio.constants.Constants;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RateLimiter {

    private final Map<String, Deque<Long>> requestMap = new ConcurrentHashMap<>();

    public boolean isAllowed(String email) {

        long now = Instant.now().toEpochMilli();

        Deque<Long> timestamps =
                requestMap.computeIfAbsent(email, k -> new ArrayDeque<>());

        while (!timestamps.isEmpty() &&
                (now - timestamps.peekFirst()) > Constants.TIME_WINDOW) {
            timestamps.pollFirst();
        }

        if (timestamps.size() >= Constants.HOURLY_LIMIT) {
            log.warn("Rate limit exceeded for email: {}", email);
            return false;
        }

        timestamps.addLast(now);

        if (timestamps.isEmpty()) {
            requestMap.remove(email);
        }

        return true;
    }

    @Scheduled(fixedRate = Constants.RATE_LIMITER_CLEANUP_INTERVAL)
    public void cleanUp() {

        long now = Instant.now().toEpochMilli();

        requestMap.values()
                .forEach(list ->
                        list.removeIf(time ->
                                (now - time) > Constants.TIME_WINDOW
                        )
                );
        
        log.info("Rate limiter cleanup completed");
    }
}