package com.app.service.otp.store;

import com.app.dao.otp.OTPDao;
import com.app.model.otp.OTP;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("DaoOTPStoreImpl")
public class OTPStoreImpl implements OTPStore {

    private final OTPDao otpDao;

    @Autowired
    public OTPStoreImpl(OTPDao otpDao) {
        this.otpDao = otpDao;
    }

    @Override
    @Transactional
    public String generateOtp(String reference) {
        invalidate(reference);

        String code = RandomStringUtils.randomNumeric(6);
        OTP otp = new OTP();
        otp.setReference(reference);
        otp.setOtp(code);
        otpDao.saveOrUpdate(otp);

        return code;
    }

    @Override
    @Transactional
    public String getIfPresent(String reference) {
        String otp = null;
        OTP o = otpDao.getByReference(reference);
        if (o != null) {
            otp = o.getOtp();
        }
        return otp;
    }

    @Override
    @Transactional
    public void invalidate(String reference) {
        otpDao.delete(reference);
    }
}
