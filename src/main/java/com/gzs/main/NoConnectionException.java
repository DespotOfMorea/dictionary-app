package com.gzs.main;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoConnectionException extends RuntimeException {
    public NoConnectionException(Throwable cause) {
        super(cause);
    }
}