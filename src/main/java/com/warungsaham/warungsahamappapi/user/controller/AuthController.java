package com.warungsaham.warungsahamappapi.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warungsaham.warungsahamappapi.user.dto.request.UserLoginReq;
import com.warungsaham.warungsahamappapi.user.dto.response.UserLoginRes;
import com.warungsaham.warungsahamappapi.utils.jwt.JwtUtils;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;

    private JwtUtils jwtUtils;

    @Autowired
    public AuthController(JwtUtils jwtUtils, AuthenticationManager authenticationManager){
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }
    
    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserLoginReq userLoginReq){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userLoginReq.getUsername(), userLoginReq.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        return ResponseEntity.ok(new UserLoginRes(jwtToken, jwtUtils.getExpirDateFromToken(jwtToken)));
    }
}
