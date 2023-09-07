package com.warungsaham.warungsahamappapi.user.service;

import org.springframework.data.domain.Page;

import com.warungsaham.warungsahamappapi.user.dto.request.NewUserReq;
import com.warungsaham.warungsahamappapi.user.model.User;

public interface UserService {
    public void addUser(NewUserReq newUserReq);

    public Page<User> getUsers(int pageIndex , int size);
}
