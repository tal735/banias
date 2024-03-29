package com.app.service.otp.store;

import com.app.dao.otp.OTPDao;
import com.app.model.otp.OTP;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("DaoOTPStoreImpl")
public class OTPStoreImpl implements OTPStore {

    private static final int TOKEN_VALIDITY_LENGTH_MINUTES = 5;

    private final OTPDao otpDao;

    @Autowired
    public OTPStoreImpl(OTPDao otpDao) {
        this.otpDao = otpDao;
    }

    @Override
    @Transactional
    public void storeOtp(String key, String code) {
        invalidate(key);
        OTP otp = new OTP();
        otp.setReference(key);
        otp.setOtp(code);
        otpDao.saveOrUpdate(otp);
    }

    @Override
    @Transactional
    public String getIfPresent(String key) {
        OTP o = otpDao.getByReference(key);
        if (o == null) {
            return null;
        }
        if (o.getDateCreated().before(DateTime.now().minusMinutes(TOKEN_VALIDITY_LENGTH_MINUTES).toDate())) {
            return null;
        }
        return o.getOtp();
    }

    @Override
    @Transactional
    public void invalidate(String key) {
        otpDao.delete(key);
    }
}
