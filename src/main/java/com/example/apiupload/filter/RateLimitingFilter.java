package com.example.apiupload.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Order(1)
public class RateLimitingFilter implements Filter {
    
    private static final Logger log = LoggerFactory.getLogger(RateLimitingFilter.class);
    
    private final ConcurrentHashMap<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> lastResetTime = new ConcurrentHashMap<>();
    private int maxUploadRequestsPerMinute = 60; // Default value
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        if (httpRequest.getRequestURI().equals("/api/upload") && 
            "POST".equalsIgnoreCase(httpRequest.getMethod())) {
            
            String clientIp = getClientIp(httpRequest);
            
            if (!isAllowed(clientIp)) {
                log.warn("Rate limit exceeded for IP: {}", clientIp);
                httpResponse.setStatus(429);
                httpResponse.getWriter().write("Rate limit exceeded. Please try again later.");
                return;
            }
        }
        
        chain.doFilter(request, response);
    }
    
    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
    
    private boolean isAllowed(String clientIp) {
        long currentTime = System.currentTimeMillis();
        long oneMinuteInMillis = 60 * 1000;
        
        Long lastReset = lastResetTime.get(clientIp);
        if (lastReset == null || (currentTime - lastReset) > oneMinuteInMillis) {
            requestCounts.put(clientIp, new AtomicInteger(0));
            lastResetTime.put(clientIp, currentTime);
        }
        
        AtomicInteger count = requestCounts.computeIfAbsent(clientIp, k -> new AtomicInteger(0));
        int currentCount = count.incrementAndGet();
        
        return currentCount <= maxUploadRequestsPerMinute;
    }
}
