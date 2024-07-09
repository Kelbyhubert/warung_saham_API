package com.warungsaham.warungsahamappapi.user.service.refreshtoken;

import com.warungsaham.warungsahamappapi.user.model.RefreshToken;

public interface RefreshTokenService {

    public RefreshToken findByRefreshToken(String refreshToken);
    
    public RefreshToken createRefreshToken(String userId);

    public RefreshToken validateRefreshToken(RefreshToken refreshToken);

    public int deleteRefreshToken(int userId);

}
