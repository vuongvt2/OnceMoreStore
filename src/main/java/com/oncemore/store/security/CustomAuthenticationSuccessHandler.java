package com.oncemore.store.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_ADMIN")) {
            if (null != savedRequest) {
                response.sendRedirect(savedRequest.getRedirectUrl());
            } else {
                response.sendRedirect("/admin/products");
            }

        } else if (roles.contains("ROLE_USER")) {
            if (null != savedRequest) {
                if (savedRequest.getRedirectUrl().contains("shoppingCart/addProduct")) {
                    response.sendRedirect("/home");
                } else {
                    response.sendRedirect(savedRequest.getRedirectUrl());
                }
            } else {
                response.sendRedirect("/home");
            }

        } else {
            response.sendRedirect("/home");
        }
    }
}
