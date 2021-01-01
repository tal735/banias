package com.app.dao.otp;

import com.app.model.otp.OTP;

public interface OTPDao {
    void saveOrUpdate(OTP otp);

    OTP getByReference(String reference);

    void delete(String reference);
}
