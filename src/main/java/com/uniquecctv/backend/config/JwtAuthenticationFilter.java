package com.uniquecctv.backend.config;

import com.uniquecctv.backend.service.CustomUserDetailsService;
import com.uniquecctv.backend.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        System.out.println("=== JWT FILTER START ===");
        System.out.println("🔍 Request: " + request.getMethod() + " " + request.getRequestURI());
        System.out.println("🔍 Headers: " + Collections.list(request.getHeaderNames()).stream()
            .map(name -> name + ": " + request.getHeader(name))
            .collect(Collectors.joining(", ")));
        System.out.println("**--**--**--**--**--**---**--**--**");
        
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt)) {
                System.out.println("📄 JWT Token found");
                
                try {
                    // Try to extract username first
                    String username = jwtUtil.extractUsername(jwt);
                    System.out.println("👤 Extracted username: " + username);
                    
                    // REMOVE the null check - we should always set authentication if token is valid
                    if (username != null) {
                        System.out.println("🔄 Loading user details...");
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        System.out.println("✅ User details loaded: " + userDetails.getUsername());
                        System.out.println("🎯 User authorities: " + userDetails.getAuthorities());
                        
                        System.out.println("🔍 Validating token...");
                        if (jwtUtil.validateToken(jwt, userDetails)) {
                            System.out.println("✅ Token validation successful");
                            
                            UsernamePasswordAuthenticationToken authentication = 
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                            SecurityContextHolder.getContext().setAuthentication(authentication);
                            System.out.println("✅ Authentication set in SecurityContext");
                            
                            // Debug: Verify authentication is set
                            Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
                            System.out.println("🔍 Current SecurityContext Auth: " + 
                                (currentAuth != null ? currentAuth.getName() + " - " + currentAuth.getAuthorities() : "NULL"));
                        } else {
                            System.out.println("❌ Token validation failed");
                            SecurityContextHolder.clearContext();
                        }
                    } else {
                        System.out.println("❌ Could not extract username from token");
                        SecurityContextHolder.clearContext();
                    }
                } catch (Exception e) {
                    System.out.println("🚨 Error during JWT processing: " + e.getMessage());
                    e.printStackTrace();
                    SecurityContextHolder.clearContext();
                }
            } else {
                System.out.println("❌ No JWT token found in request");
                SecurityContextHolder.clearContext();
            }
        } catch (Exception ex) {
            System.out.println("🚨 JWT Filter exception: " + ex.getMessage());
            ex.printStackTrace();
            SecurityContextHolder.clearContext();
        }

        System.out.println("=== JWT FILTER END ===");
        
        // Debug right before passing to next filter
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("🔍 SecurityContext BEFORE chain.doFilter: " + 
            (currentAuth != null ? currentAuth.getName() + " - " + currentAuth.getAuthorities() : "NULL"));
        
        filterChain.doFilter(request, response);
        
        // Debug after filter chain
        currentAuth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("🔍 SecurityContext AFTER chain.doFilter: " + 
            (currentAuth != null ? currentAuth.getName() + " - " + currentAuth.getAuthorities() : "NULL"));
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            logger.debug("🔑 Extracted JWT token (first 20 chars): {}...", token.substring(0, Math.min(20, token.length())));
            return token;
        }
        
        // Also check for token in query parameter (for debugging)
        String tokenParam = request.getParameter("token");
        if (StringUtils.hasText(tokenParam)) {
            logger.debug("🔑 Found token in query parameter");
            return tokenParam;
        }
        
        return null;
    }
    
    
}