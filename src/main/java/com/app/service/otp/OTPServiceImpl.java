package com.app.service.otp;

import com.app.service.otp.store.OTPStore;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OTPServiceImpl implements OTPService {

    private final OTPStore otpStore;
    private final PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(OTPServiceImpl.class);

    @Autowired
    public OTPServiceImpl(@Qualifier("CacheOTPStoreImpl") OTPStore otpStore, PasswordEncoder passwordEncoder) {
        this.otpStore = otpStore;
        this.passwordEncoder = passwordEncoder;
        LOGGER.debug("OTPServiceImpl CONSTRUCTOR");
    }

    @Override
    public String generateOtp(String key) {
        String code = RandomStringUtils.randomNumeric(6);
        String otp = passwordEncoder.encode(code);
        otpStore.storeOtp(key, otp);
        return code;
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
