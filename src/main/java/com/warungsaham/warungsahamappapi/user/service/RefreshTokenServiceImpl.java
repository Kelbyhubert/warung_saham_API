package com.warungsaham.warungsahamappapi.user.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.warungsaham.warungsahamappapi.global.exception.NotFoundException;
import com.warungsaham.warungsahamappapi.user.dao.RefreshTokenDao;
import com.warungsaham.warungsahamappapi.user.dao.UserDao;
import com.warungsaham.warungsahamappapi.user.exception.auth.TokenInvalidException;
import com.warungsaham.warungsahamappapi.user.exception.user.UserNotFoundException;
import com.warungsaham.warungsahamappapi.user.model.RefreshToken;
import com.warungsaham.warungsahamappapi.user.model.User;

import jakarta.transaction.Transactional;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${waroeengsaham.jwt.refreshTokenExpirationMs}")
    private int refreshTokenExpiredDate;

    private RefreshTokenDao refreshTokenDao;
    private UserDao userDao;

    @Autowired
    public RefreshTokenServiceImpl(RefreshTokenDao refreshTokenDao, UserDao userDao) {
        this.refreshTokenDao = refreshTokenDao;
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public RefreshToken createRefreshToken(String userId) {
        RefreshToken refreshToken = new RefreshToken();

        User user = userDao.findByUserId(userId);

        if(user == null){
            throw new UserNotFoundException("user not found");
        }

        // delete old refresh if user have refresh token
        refreshTokenDao.deleteByUser(user);

        refreshToken.setUser(user);
        refreshToken.setExpiredDate(Instant.now().plusMillis(refreshTokenExpiredDate));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken = refreshTokenDao.save(refreshToken);

        return refreshToken;
    }

    @Override
    @Transactional
    public RefreshToken validateRefreshToken(RefreshToken refreshToken) {
        if(refreshToken.getExpiredDate().compareTo(Instant.now()) < 0){
            refreshTokenDao.delete(refreshToken);
            throw new TokenInvalidException("Refresh token was expired");
        }
        
        return refreshToken;
    }

    @Override
    @Transactional
    public int deleteRefreshToken(int userId) {
        User user = userDao.findById(userId).orElseThrow(() -> {
            throw new UserNotFoundException("user not found");
        });
        return refreshTokenDao.deleteByUser(user);
    }

    @Override
    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenDao.findByRefreshToken(refreshToken).orElseThrow(() -> {
            throw new NotFoundException("Refresh Token Not Found");
        });
    }
    
}
