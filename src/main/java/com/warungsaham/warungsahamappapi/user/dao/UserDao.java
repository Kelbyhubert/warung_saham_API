package com.warungsaham.warungsahamappapi.user.dao;

import com.warungsaham.warungsahamappapi.user.model.User;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {
    User findByUsername(String username);
    User findByUserId(String userId);
    Optional<User> findByUsernameOrEmail(String username,String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Page<User> findAllRecordByUsernameContaining(String username, Pageable pageable);
}
