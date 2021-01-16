package com.app.service.otp;

import com.app.service.otp.store.OTPStore;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OTPServiceImpl implements OTPService {

    private final OTPStore otpStore;

    private static final Logger LOGGER = LoggerFactory.getLogger(OTPServiceImpl.class);

    @Autowired
    public OTPServiceImpl(@Qualifier("DaoOTPStoreImpl") OTPStore otpStore) {
        this.otpStore = otpStore;
        LOGGER.debug("OTPServiceImpl CONSTRUCTOR");
    }

    @Override
    public String generateOtp(String key) {
        String code = RandomStringUtils.randomNumeric(6);
        otpStore.storeOtp(key, code);
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
