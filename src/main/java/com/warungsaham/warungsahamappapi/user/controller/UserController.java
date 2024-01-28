package com.warungsaham.warungsahamappapi.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.warungsaham.warungsahamappapi.user.dto.request.NewPasswordReq;
import com.warungsaham.warungsahamappapi.user.dto.request.NewUserReq;
import com.warungsaham.warungsahamappapi.user.model.User;
import com.warungsaham.warungsahamappapi.user.service.UserService;
import com.warungsaham.warungsahamappapi.utils.jwt.JwtUtils;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {
    
    private UserService userService;

    private JwtUtils jwtUtils;

    @Autowired
    public UserController(UserService userService, JwtUtils jwtUtils){
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }


    @PostMapping(
        path = "/add-user"
    )
    public ResponseEntity<?> addNewUser(@RequestBody NewUserReq newUserReq){
        userService.addUser(newUserReq);
        return ResponseEntity.ok("Success");
    }

    @PutMapping(
        path = "/update-password"
    )
    public ResponseEntity<?> updateUserPassword(@RequestHeader("Authorization") String token, @RequestBody NewPasswordReq newPasswordReq){
        String userId = (String) jwtUtils.getUserDetailFromToken(token.replace("Bearer ", "")).get("userId");
        userService.updatePassword(userId, newPasswordReq.getOldPassword(), newPasswordReq.getNewPassword());
        return ResponseEntity.ok("Success");
    }

    @GetMapping(
        path = ""
    )
    public ResponseEntity<?> getAllUser(@RequestParam int pageIndex, @RequestParam int size,@RequestParam String username){
        Page<User> users = userService.getUsers(username,pageIndex,size);
        return ResponseEntity.ok(users);
    }

    @GetMapping(
        path = "/{userId}"
    )
    public ResponseEntity<?> getUserByUserId(@PathVariable(value = "userId") String userId){
        User user = userService.getUserByUserId(userId);
        return ResponseEntity.ok(user);
    }
}
