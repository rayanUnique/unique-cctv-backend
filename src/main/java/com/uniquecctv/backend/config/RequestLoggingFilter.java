package com.uniquecctv.backend.config;

import org.springframework.stereotype.Component;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class RequestLoggingFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        System.out.println("🌐 REQUEST LOGGER:");
        System.out.println("   Method: " + httpRequest.getMethod());
        System.out.println("   URI: " + httpRequest.getRequestURI());
        System.out.println("   Query String: " + httpRequest.getQueryString());
        System.out.println("   Content-Type: " + httpRequest.getContentType());
        System.out.println("   Content-Length: " + httpRequest.getContentLength());
        
        chain.doFilter(request, response);
    }
}