package com.warungsaham.warungsahamappapi.user.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.warungsaham.warungsahamappapi.global.response.ApiResponse;
import com.warungsaham.warungsahamappapi.user.dto.request.NewPasswordReq;
import com.warungsaham.warungsahamappapi.user.dto.request.NewUserReq;
import com.warungsaham.warungsahamappapi.user.dto.request.UpdateUserRoleReq;
import com.warungsaham.warungsahamappapi.user.model.User;
import com.warungsaham.warungsahamappapi.user.service.UserService;
import com.warungsaham.warungsahamappapi.utils.jwt.JwtUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {
    
    private UserService userService;

    private JwtUtils jwtUtils;

    public UserController(UserService userService, JwtUtils jwtUtils){
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }


    @PostMapping(
        path = "/add-user",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse<String>> addNewUser(@Valid @RequestBody NewUserReq newUserReq){
        
        userService.addUser(newUserReq);

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.CREATED.value());
        apiResponse.setData("Success");

        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }

    
    @PutMapping(
        path = "{userId}/update",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse<String>> updateUserRole(@PathVariable String userId, @RequestBody UpdateUserRoleReq requestBody) {

        userService.updateUserRole(userId, requestBody);
        
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData("Success");

        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }


    @PutMapping(
        path = "/update-password",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse<String>> updateUserPassword(@RequestHeader("Authorization") String token, 
                                                                    @Valid @RequestBody NewPasswordReq newPasswordReq){
        
        String userId = (String) jwtUtils.getUserDetailFromToken(token.replace("Bearer ", "")).get("userId");
        userService.updatePassword(userId, newPasswordReq.getOldPassword(), newPasswordReq.getNewPassword());

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData("Success");

        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping(
        path = "",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse<Page<User>>> getAllUser(@RequestParam int pageIndex, @RequestParam int size,@RequestParam String username){
        Page<User> users = userService.getUsers(username,pageIndex,size);

        ApiResponse<Page<User>> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(users);

        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping(
        path = "/{userId}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponse<User>> getUserByUserId(@PathVariable(value = "userId") String userId){
        User user = userService.getUserByUserId(userId);

        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK.value());
        apiResponse.setData(user);

        return ResponseEntity.ok(apiResponse);
    }
}
