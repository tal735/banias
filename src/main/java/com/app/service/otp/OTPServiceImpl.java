package com.app.service.otp;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

//@Service
public class OTPServiceImpl implements OTPService {

    private final Cache<String, String> otpStore = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build();

    @Override
    public void generateOtp(String key) {
        invalidate(key);
        String otp = RandomStringUtils.randomNumeric(6);
        otpStore.put(key, otp);
    }

    @Override
    public String getIfPresent(String key) {
        return otpStore.getIfPresent(key);
    }

    @Override
    public void invalidate(String key) {
        otpStore.invalidate(key);
    }
}
