package com.app.service.otp.store;

public interface OTPStore {
    void storeOtp(String key, String otp);
    String getIfPresent(String reference);
    void invalidate(String reference);
}
