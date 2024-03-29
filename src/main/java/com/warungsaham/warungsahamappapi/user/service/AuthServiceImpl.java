package com.warungsaham.warungsahamappapi.user.service;

import com.warungsaham.warungsahamappapi.user.dao.UserDao;
import com.warungsaham.warungsahamappapi.user.exception.auth.CredentialException;
import com.warungsaham.warungsahamappapi.user.exception.user.UserNotFoundException;
import com.warungsaham.warungsahamappapi.user.model.CustomUserDetail;
import com.warungsaham.warungsahamappapi.user.model.User;

import jakarta.transaction.Transactional;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthServiceImpl implements AuthService{

    private UserDao userDao;

    @Autowired
    public AuthServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = userDao.findByUsernameOrEmail(username,username).orElseThrow(() -> {
            throw new CredentialException("Invalid Credential");
        });

        List<GrantedAuthority> listAuthorities = null;

        listAuthorities = user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.getRolename()))
                            .collect(Collectors.toList());
        

        return new CustomUserDetail(user.getUserId(), 
                                    user.getUsername(), 
                                    user.getPassword(), 
                                    listAuthorities);
        }

}

