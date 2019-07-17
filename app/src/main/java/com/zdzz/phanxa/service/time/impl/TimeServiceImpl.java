package com.zdzz.phanxa.service.time.impl;

import com.zdzz.phanxa.service.time.TimeService;

import java.util.Calendar;

public class TimeServiceImpl implements TimeService {

    @Override
    public long getMillisecondTime() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long millis = (System.currentTimeMillis() - c.getTimeInMillis());
        return millis;
    }
}
