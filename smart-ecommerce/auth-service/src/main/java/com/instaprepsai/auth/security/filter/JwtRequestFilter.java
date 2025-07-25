package com.instaprepsai.auth.security.filter;

import com.instaprepsai.auth.security.CustomUserDetailsService;
import com.instaprepsai.auth.security.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String pathWithoutContext = requestURI.replace(contextPath, "");

        // Check if the request URI matches any of the permitted URLs
        boolean isPermitAll = true;/* Arrays.stream(AppConstant.PERMIT_ALL_URLS)
                .anyMatch(url -> pathWithoutContext.matches(url.replace("**", ".*")));*/

        // If the request is not for a permitted URL, check the Authorization header
        if (!isPermitAll) {
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header is missing or invalid");
                return; // Stop further processing
            }

            String jwt = authorizationHeader.substring(7); // Extract the JWT token
            String username = null;

            // Validate the JWT token
            if (jwtUtil.isValidJwtToken(jwt)) {
                username = jwtUtil.extractUsername(jwt);
            }

            // Authenticate the user if the username is found and no authentication is set
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtUtil.validateToken(jwt, username)) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        chain.doFilter(request, response); // Continue the filter chain
    }
}