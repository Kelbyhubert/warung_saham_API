package com.warungsaham.warungsahamappapi.security.auth;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomDaoAuthProvider extends AbstractUserDetailsAuthenticationProvider{

    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if(authentication.getCredentials() == null){
            throw new BadCredentialsException("Credential Not Provided");
        }

        String currentPassword = authentication.getCredentials().toString();
        if(!passwordEncoder.matches(currentPassword, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Credential");
        }

        
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        log.info("Request Username/email : {}", username);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(userDetails == null){
            throw new BadCredentialsException("User Not Found");
        }

        return userDetails;
    
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }


    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    
    
}
