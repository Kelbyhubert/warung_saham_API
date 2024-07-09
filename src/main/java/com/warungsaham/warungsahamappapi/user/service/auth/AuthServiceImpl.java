package com.warungsaham.warungsahamappapi.user.service.auth;

import com.warungsaham.warungsahamappapi.user.dao.UserDao;
import com.warungsaham.warungsahamappapi.user.model.CustomUserDetail;
import com.warungsaham.warungsahamappapi.user.model.User;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    private UserDao userDao;

    public AuthServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userDao.findByUsernameOrEmail(username,username);

        if(user.isPresent()){
            List<GrantedAuthority> listAuthorities = null;

            listAuthorities = user.get().getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role.getRolename()))
                                .collect(Collectors.toList());
            
    
            return new CustomUserDetail(user.get().getUserId(), 
                                        user.get().getUsername(), 
                                        user.get().getPassword(), 
                                        listAuthorities);
        }

        return null;

    }

}

