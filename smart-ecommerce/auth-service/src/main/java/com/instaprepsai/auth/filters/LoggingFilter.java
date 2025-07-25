package com.instaprepsai.auth.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
@Slf4j
public class LoggingFilter implements Filter {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";

    @Value("${spring.application.name}")
    private String serviceName;

    private final String serviceIP = getServiceIp();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Extract Correlation ID from the request headers
        String correlationId = httpRequest.getHeader(CORRELATION_ID_HEADER);

        MDC.put("serviceName", serviceName);
        if (StringUtils.isNotBlank(serviceIP))
            MDC.put("serviceIP", serviceIP);

        if (correlationId != null && !correlationId.isEmpty()) {
            // Set the Correlation ID in MDC
            MDC.put(CORRELATION_ID_HEADER, correlationId);
        }

        try {
            // Proceed with the filter chain
            chain.doFilter(request, response);
        } finally {
            // Clear MDC after the request is processed
            MDC.clear();
        }
    }

    private String getServiceIp() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            return ip.getHostAddress();
        } catch (UnknownHostException e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }
}
