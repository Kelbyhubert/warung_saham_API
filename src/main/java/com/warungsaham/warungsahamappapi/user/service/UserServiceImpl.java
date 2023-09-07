package com.warungsaham.warungsahamappapi.user.service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.warungsaham.warungsahamappapi.role.dao.RoleDao;
import com.warungsaham.warungsahamappapi.role.model.Role;
import com.warungsaham.warungsahamappapi.user.dao.UserDao;
import com.warungsaham.warungsahamappapi.user.dto.request.NewUserReq;
import com.warungsaham.warungsahamappapi.user.model.User;
import com.warungsaham.warungsahamappapi.validation.service.ValidationService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private RoleDao roleDao;
    private PasswordEncoder passwordEncoder;
    private ValidationService validationService;


    @Autowired
    public UserServiceImpl(UserDao userDao , PasswordEncoder passwordEncoder , ValidationService validationService , RoleDao roleDao){
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.validationService = validationService;
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public void addUser(NewUserReq newUserReq) {
        validationService.validate(newUserReq);

        if(userDao.existsByUsername(newUserReq.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username already registered");
        }

        if(userDao.existsByEmail(newUserReq.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"email already registered");
        }
        
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setUsername(newUserReq.getUsername());
        user.setEmail(newUserReq.getEmail());
        user.setName(newUserReq.getName());
        user.setPhoneNumber(newUserReq.getPhoneNumber());

        user.setPassword(passwordEncoder.encode(newUserReq.getPassword()));

        List<Role> roles = roleDao.findAllById(newUserReq.getRoleIdList());

        user.setRoles(new HashSet<Role>(roles));
        
        userDao.save(user);

    }

    @Override
    public Page<User> getUsers(int pageIndex, int size) {
        Pageable pageRequest = PageRequest.of(pageIndex, size);
        return userDao.findAll(pageRequest);
    }
    
}
