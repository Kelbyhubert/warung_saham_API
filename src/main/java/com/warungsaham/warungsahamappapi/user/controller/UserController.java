package com.warungsaham.warungsahamappapi.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warungsaham.warungsahamappapi.user.dto.request.NewUserReq;
import com.warungsaham.warungsahamappapi.user.service.UserService;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {
    
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }


    @PostMapping(
        path = "/add-user"
    )
    public ResponseEntity<?> addNewUser(@RequestBody NewUserReq newUserReq){
        userService.addUser(newUserReq);
        return ResponseEntity.ok("Success");
    } 
}
