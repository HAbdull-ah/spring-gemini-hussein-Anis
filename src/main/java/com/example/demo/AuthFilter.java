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

        // PUBLIC pages (no session required)
        boolean isPublic =
                path.startsWith("/login") ||
                path.startsWith("/register") ||
                path.endsWith("login.html") ||
                path.endsWith("register.html") ||
                path.endsWith(".css") ||
                path.endsWith(".js") ||
                path.endsWith(".png") ||
                path.endsWith(".jpg") ||
                path.endsWith(".jpeg") ||
                path.endsWith(".ico");

        if (isPublic) {
            filterChain.doFilter(request, response);
            return;
        }

        // PROTECT ALL AI ENDPOINTS
        if (path.startsWith("/ai")) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("username") == null) {
                response.sendRedirect("/login.html");
                return;
            }
        }

        // PROTECTED pages (index, root, anything else)
        HttpSession session = request.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("username") != null);

        if (!loggedIn) {
            response.sendRedirect("/login.html");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
