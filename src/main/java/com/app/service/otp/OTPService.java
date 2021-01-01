package com.app.service.otp;

public interface OTPService {
    void generateOtp(String key);

    String getIfPresent(String key);

    void invalidate(String key);
}
