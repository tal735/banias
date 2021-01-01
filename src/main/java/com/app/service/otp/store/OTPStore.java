package com.app.service.otp.store;

public interface OTPStore {
    String generateOtp(String reference);
    String getIfPresent(String reference);
    void invalidate(String reference);
}
