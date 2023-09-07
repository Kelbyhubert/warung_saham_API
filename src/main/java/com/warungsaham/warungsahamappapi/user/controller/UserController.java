package com.warungsaham.warungsahamappapi.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.warungsaham.warungsahamappapi.user.dto.request.NewUserReq;
import com.warungsaham.warungsahamappapi.user.model.User;
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

    @GetMapping(
        path = ""
    )
    public ResponseEntity<?> getAllUser(@RequestParam int pageIndex, @RequestParam int size){
        Page<User> users = userService.getUsers(pageIndex,size);
        return ResponseEntity.ok(users);
    }
}
