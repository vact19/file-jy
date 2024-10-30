package com.gmdrive.gmdrive.global.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SystemClockHolder implements ClockHolder {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
