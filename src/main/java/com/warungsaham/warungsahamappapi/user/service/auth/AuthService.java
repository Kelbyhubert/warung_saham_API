package com.warungsaham.warungsahamappapi.user.service.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    public UserDetails loadUserByUsername(String username);
}
