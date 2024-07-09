package com.warungsaham.warungsahamappapi.role.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.warungsaham.warungsahamappapi.role.model.Role;
import com.warungsaham.warungsahamappapi.role.service.RoleService;

@RestController
@RequestMapping(path = "/api/v1/role")
public class RoleController {

    private RoleService roleService;

    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }
    
    @GetMapping(path = "/getAllRole")
    public ResponseEntity<List<Role>> roleList(){
        List<Role> roles =  roleService.getAllRole();
        return ResponseEntity.ok(roles);
    }
}
