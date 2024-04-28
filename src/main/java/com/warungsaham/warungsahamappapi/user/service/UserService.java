package com.warungsaham.warungsahamappapi.user.service;

import org.springframework.data.domain.Page;

import com.warungsaham.warungsahamappapi.user.dto.request.NewUserReq;
import com.warungsaham.warungsahamappapi.user.dto.request.UpdateUserRoleReq;
import com.warungsaham.warungsahamappapi.user.model.User;

public interface UserService {
    public void addUser(NewUserReq newUserReq);

    public void updateUserRole(String userId, UpdateUserRoleReq updateUserRoleReq);

    public Page<User> getUsers(String username,int pageIndex , int size);

    public User getUserByUserId(String userId);

    public void updatePassword(String userId, String oldPassword, String newPassword);
}
