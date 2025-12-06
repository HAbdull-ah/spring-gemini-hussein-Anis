package com.example.demo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Controller
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ============================
    // REGISTER
    // ============================
    @PostMapping("/register")
    public void register(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletResponse response
    ) throws IOException {

        //Check if username already exists
        if (userRepository.existsById(username)) {
            response.sendRedirect("/register.html?error=taken");
            return;
        }

        //Save user
        userRepository.save(new User(username, password));

        //After registration. Send user to login page
        response.sendRedirect("/login.html?registered=1");
    }

    // ============================
    // LOGIN
    // ============================
    @PostMapping("/login")
    public void login(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        var userOpt = userRepository.findById(username);

        // User not found OR wrong password
        if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(password)) {
            response.sendRedirect("/login.html?error=1");
            return;
        }

        // Create Session
        HttpSession session = request.getSession(true);
        session.setAttribute("username", username);

        // Redirect to home page
        response.sendRedirect("/index.html");
    }

    // ============================
    // LOGOUT
    // ============================
    @GetMapping("/logout")
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();

        response.sendRedirect("/login.html");
    }

    // ============================
    // SESSION CHECK (For index.html)
    // ============================
    @GetMapping("/session")
    @ResponseBody
    public Map<String, String> session(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("username") == null) {
            response.setStatus(401); // Not logged in
            return Map.of("error", "not_logged_in");
        }

        return Map.of("username", session.getAttribute("username").toString());
    }
}
