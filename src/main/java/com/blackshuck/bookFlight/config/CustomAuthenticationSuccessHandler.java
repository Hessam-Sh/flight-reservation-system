package com.blackshuck.bookFlight.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();

        System.out.println("The User " + username + " has logged in successfully");

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        boolean isUser = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_USER"));

        request.getSession().setAttribute("loginMessage", "User " + username + " has successfully logged in");

        if (isAdmin)
        {
            response.sendRedirect("/admin");
        }
        else if (isUser)
        {
            response.sendRedirect("/index");
        }
    }
}
