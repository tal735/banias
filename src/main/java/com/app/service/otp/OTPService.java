package com.app.service.otp;

public interface OTPService {
    String generateOtp(String key);

    String getIfPresent(String key);

    void invalidate(String key);
}
