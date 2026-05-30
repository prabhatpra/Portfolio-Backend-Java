package com.prabhat.portfolio.util;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.prabhat.portfolio.constant.ContactConstants;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RateLimiter {

    private final Map<String, List<Long>> requestMap = new ConcurrentHashMap<>();

    public boolean isAllowed(String email) {

        long now = Instant.now().toEpochMilli();

        requestMap.putIfAbsent(email, new CopyOnWriteArrayList<>());

        List<Long> timestamps = requestMap.get(email);

        timestamps.removeIf(time ->
                (now - time) > ContactConstants.TIME_WINDOW
        );

        if (timestamps.size() >= ContactConstants.HOURLY_LIMIT) {
        	log.warn("Rate limit exceeded for email: {}", email);
        	
            return false;
        }

        timestamps.add(now);
        return true;
    }

    @Scheduled(fixedRate = 60000)
    public void cleanUp() {

        long now = Instant.now().toEpochMilli();

        requestMap.values()
                .forEach(list ->
                        list.removeIf(time ->
                                (now - time) > ContactConstants.TIME_WINDOW
                        )
                );
        
        log.info("Rate limiter cleanup completed");
    }
}