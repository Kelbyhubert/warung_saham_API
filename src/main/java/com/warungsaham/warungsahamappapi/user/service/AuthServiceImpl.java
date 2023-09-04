package com.warungsaham.warungsahamappapi.user.service;

import com.warungsaham.warungsahamappapi.user.dao.UserDao;
import com.warungsaham.warungsahamappapi.user.model.User;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    private UserDao userDao;

    @Autowired
    public AuthServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        User user = userDao.findByUsernameOrEmail(username,username);

        List<GrantedAuthority> listAuthorities = user.getRoles().stream()
                                                .map(role -> new SimpleGrantedAuthority(role.getRolename()))
                                                .collect(Collectors.toList());
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),true,true,true,true,listAuthorities);

    }
}
