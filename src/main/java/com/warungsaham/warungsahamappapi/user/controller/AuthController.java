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
import com.warungsaham.warungsahamappapi.user.dto.response.TokenRefreshResponse;
import com.warungsaham.warungsahamappapi.user.dto.response.UserLoginRes;
import com.warungsaham.warungsahamappapi.user.model.CustomUserDetail;
import com.warungsaham.warungsahamappapi.user.model.RefreshToken;
import com.warungsaham.warungsahamappapi.user.service.AuthService;
import com.warungsaham.warungsahamappapi.user.service.RefreshTokenService;
import com.warungsaham.warungsahamappapi.utils.jwt.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;

    private RefreshTokenService refreshTokenService;

    private AuthService authService;

    private JwtUtils jwtUtils;

    @Autowired
    public AuthController(JwtUtils jwtUtils, AuthenticationManager authenticationManager ,RefreshTokenService refreshTokenService,AuthService authService){
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.authService = authService;
    }
    
    @PostMapping("/signin")
    public ResponseEntity<UserLoginRes> authenticate(@RequestBody UserLoginReq userLoginReq){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userLoginReq.getUsername(), userLoginReq.getPassword()));
        
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(authentication);
        String refreshToken = refreshTokenService.createRefreshToken(customUserDetail.getUserId()).getRefreshToken();

        return ResponseEntity.ok(new UserLoginRes(jwtToken,refreshToken, jwtUtils.getExpirDateFromToken(jwtToken)));
    }

    

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("Refresh-Token");

        RefreshToken rToken = refreshTokenService.findByRefreshToken(refreshToken);
        refreshTokenService.validateRefreshToken(rToken);

        CustomUserDetail customUserDetail = (CustomUserDetail) authService.loadUserByUsername(rToken.getUser().getUsername());

        String token = jwtUtils.generateTokenFromUserDetail(customUserDetail);
        refreshToken = rToken.getRefreshToken();
   
        return ResponseEntity.ok(new TokenRefreshResponse(token,refreshToken));

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String refreshToken = request.getHeader("Refresh-Token");
        RefreshToken rToken = refreshTokenService.findByRefreshToken(refreshToken);
        refreshTokenService.deleteRefreshToken(rToken.getUser().getId());

        return ResponseEntity.ok("Success");
    }
     
}
