package com.warungsaham.warungsahamappapi.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warungsaham.warungsahamappapi.global.response.ApiResponse;
import com.warungsaham.warungsahamappapi.user.dto.request.UserLoginReq;
import com.warungsaham.warungsahamappapi.user.dto.response.TokenRefreshResponse;
import com.warungsaham.warungsahamappapi.user.dto.response.UserLoginRes;
import com.warungsaham.warungsahamappapi.user.model.CustomUserDetail;
import com.warungsaham.warungsahamappapi.user.model.RefreshToken;
import com.warungsaham.warungsahamappapi.user.service.AuthService;
import com.warungsaham.warungsahamappapi.user.service.RefreshTokenService;
import com.warungsaham.warungsahamappapi.utils.jwt.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;

    private RefreshTokenService refreshTokenService;

    private AuthService authService;

    private JwtUtils jwtUtils;

    public AuthController(JwtUtils jwtUtils, AuthenticationManager authenticationManager ,RefreshTokenService refreshTokenService,AuthService authService){
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.authService = authService;
    }
    
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<UserLoginRes>> authenticate(@Valid @RequestBody UserLoginReq userLoginReq){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userLoginReq.getUsername(), userLoginReq.getPassword()));
        
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(authentication);
        String refreshToken = refreshTokenService.createRefreshToken(customUserDetail.getUserId()).getRefreshToken();

        ApiResponse<UserLoginRes> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(new UserLoginRes(jwtToken,refreshToken, jwtUtils.getExpirDateFromToken(jwtToken)));

        return ResponseEntity.ok(apiResponse);
    }

    

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<TokenRefreshResponse>> refreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("Refresh-Token");

        RefreshToken rToken = refreshTokenService.findByRefreshToken(refreshToken);
        refreshTokenService.validateRefreshToken(rToken);

        CustomUserDetail customUserDetail = (CustomUserDetail) authService.loadUserByUsername(rToken.getUser().getUsername());

        String token = jwtUtils.generateTokenFromUserDetail(customUserDetail);
        refreshToken = rToken.getRefreshToken();

        ApiResponse<TokenRefreshResponse> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(new TokenRefreshResponse(token,refreshToken));
   
        return ResponseEntity.ok(apiResponse);

    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request) {
        String refreshToken = request.getHeader("Refresh-Token");
        RefreshToken rToken = refreshTokenService.findByRefreshToken(refreshToken);
        refreshTokenService.deleteRefreshToken(rToken.getUser().getId());


        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData("Success");

        return ResponseEntity.ok(apiResponse);
    }
     
}
