package com.app.dao.log;

import com.app.model.log.BookingLog;

public interface BookingLogDao {
    void saveOrUpdate(BookingLog bookingLog);
}
