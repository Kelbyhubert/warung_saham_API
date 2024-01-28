package com.warungsaham.warungsahamappapi.user.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.warungsaham.warungsahamappapi.user.model.RefreshToken;
import com.warungsaham.warungsahamappapi.user.model.User;

@Repository
public interface RefreshTokenDao extends JpaRepository<RefreshToken,Integer> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    
    int deleteByUser(User user);
}
