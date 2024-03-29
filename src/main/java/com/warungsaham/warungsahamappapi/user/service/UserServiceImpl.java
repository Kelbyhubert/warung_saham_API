package com.warungsaham.warungsahamappapi.user.service;

import java.util.Calendar;
import java.util.HashMap;
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
import com.warungsaham.warungsahamappapi.user.exception.user.UserExistsException;
import com.warungsaham.warungsahamappapi.user.exception.user.UserNotFoundException;
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

        boolean usernameExists = userDao.existsByUsername(newUserReq.getUsername());
        boolean emailExists = userDao.existsByEmail(newUserReq.getEmail());

        boolean recordExists = usernameExists || emailExists;
        
        if(recordExists){
            HashMap<String,Boolean> validation = new HashMap<>();
            validation.put("usernameExist", usernameExists);
            validation.put("emailExists", emailExists);
            throw new UserExistsException("Data Already Exists", validation);
        }
        
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setUsername(newUserReq.getUsername());
        user.setEmail(newUserReq.getEmail());
        user.setName(newUserReq.getName());
        user.setPhoneNumber(newUserReq.getPhoneNumber());
        user.setDob(newUserReq.getDob());
        user.setActive(1);
        user.setStatus(1);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(user.getDob());

        StringBuilder sb = new StringBuilder();
        sb.append(calendar.get(Calendar.YEAR));
        sb.append(user.getUsername());
        sb.append(calendar.get(Calendar.DATE));
        sb.append(calendar.get(Calendar.MONTH) + 1);


        user.setPassword(passwordEncoder.encode(sb.toString()));

        newUserReq.getRoleIdList().add(3);

        List<Role> roles = roleDao.findAllById(newUserReq.getRoleIdList());

        user.setRoles(new HashSet<Role>(roles));
        
        userDao.save(user);

    }

    @Override
    public Page<User> getUsers(String username,int pageIndex, int size) {

        Pageable pageRequest = PageRequest.of(pageIndex, size);
        if(username == null || username.trim() == ""){
            return userDao.findAll(pageRequest);
        }
        return userDao.findAllRecordByUsernameContaining(username, pageRequest);
    }

    @Override
    public User getUserByUserId(String userId) {
        User user = userDao.findByUserId(userId);

        if(user == null){
            throw new UserNotFoundException("User Not Found");
        }

        return user;
    }

    @Override
    @Transactional
    public void updatePassword(String userId, String oldPassword, String newPassword) {
        User user = userDao.findByUserId(userId);

        if(user == null){
            throw new UserNotFoundException("User Not Found");
        }

        if(user.getStatus() == 0 && !passwordEncoder.matches(oldPassword, user.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password Invalid");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setStatus(0);

        userDao.save(user);

    }

    
}
