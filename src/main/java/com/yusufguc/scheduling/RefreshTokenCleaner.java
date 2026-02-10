package com.yusufguc.scheduling;

import com.yusufguc.service.impl.RefreshTokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenCleaner {

    @Autowired
    private RefreshTokenServiceImpl refreshTokenService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanExpiredTokens() {

        refreshTokenService.deleteExpiredTokens();
    }
}
