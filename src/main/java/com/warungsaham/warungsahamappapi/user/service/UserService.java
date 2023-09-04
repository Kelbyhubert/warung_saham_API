package com.warungsaham.warungsahamappapi.user.service;

import com.warungsaham.warungsahamappapi.user.dto.request.NewUserReq;
import com.warungsaham.warungsahamappapi.user.model.User;

public interface UserService {
    public void addUser(NewUserReq newUserReq);
}
