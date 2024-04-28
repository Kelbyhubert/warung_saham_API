package com.warungsaham.warungsahamappapi.middleware.auth;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.warungsaham.warungsahamappapi.global.response.ErrorResponse;
import com.warungsaham.warungsahamappapi.user.service.AuthService;
import com.warungsaham.warungsahamappapi.utils.jwt.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        log.info(request.getRequestURL().toString());

        if(request.getRequestURL().toString().contains("/auth")){
            filterChain.doFilter(request, response);
            return;
        }

        if(request.getRequestURL().toString().contains("/swagger-ui")){
            filterChain.doFilter(request, response);
            return;
        }

        if(request.getRequestURL().toString().contains("/api-docs")){
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = null;
        String auth = request.getHeader("Authorization");
        

        log.info("Token : "+ auth);

        if(auth == null){
            // auth = "";
            log.error("Authorization Missing at header");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            errorResponse.setMessage("Invalid Mandatory Field 'Authorization'");

            OutputStream out = response.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(out, errorResponse);

            return;
        }
        
        if(!auth.isEmpty() && auth.startsWith("Bearer ")){
            jwtToken = auth.substring(7);
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

        filterChain.doFilter(request, response);
    }
    
}
