package com.example.demo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Public pages that do NOT require login
        boolean isPublic =
                path.startsWith("/login") ||
                path.startsWith("/auth") ||
                path.startsWith("/css") ||
                path.startsWith("/js") ||
                path.startsWith("/images") ||
                path.equals("/favicon.ico");

        if (isPublic) {
            filterChain.doFilter(request, response);
            return;
        }

        // Check if user is authenticated
        HttpSession session = request.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("username") != null);

        if (!loggedIn) {
            response.sendRedirect("/login.html");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
