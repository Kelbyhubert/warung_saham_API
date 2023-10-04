package com.warungsaham.warungsahamappapi.middleware.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.warungsaham.warungsahamappapi.user.service.AuthService;
import com.warungsaham.warungsahamappapi.utils.jwt.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        if(!request.getRequestURL().toString().endsWith("/signin")){
            
            String jwtToken = null;
            String header = request.getHeader("Authorization");
                

            if(!header.isEmpty() && header.startsWith("Bearer ")){
                jwtToken = header.substring(7);
            }

            try {
                if(jwtToken != null && jwtUtils.validateJwtToken(jwtToken)){
                    String username = jwtUtils.getUsernameFromToken(jwtToken);

                    UserDetails userDetails = authService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                }
            } catch (Exception e) {
                logger.error("Cannot set user auth {}", e);
            }
        }
        filterChain.doFilter(request, response);
    }
    
}
