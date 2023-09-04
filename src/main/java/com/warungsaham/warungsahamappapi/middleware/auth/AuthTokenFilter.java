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

    private AuthService authService;

    private JwtUtils jwtUtils;

    public AuthTokenFilter(){
        
    }

    @Autowired
    public AuthTokenFilter(AuthService authService , JwtUtils jwtUtils){
        this.authService = authService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
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
            // TODO: handle exception
            logger.error("Cannot set user auth {}", e);
        }

        filterChain.doFilter(request, response);
    }
    
}