package com.oncemore.store.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class AuthService {
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.sendRedirect("/logout"); // Điều hướng đến URL logout (nếu có)
    }
}
