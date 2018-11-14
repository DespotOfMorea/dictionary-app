package com.gzs.main;

import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j
public class NoConnectionException extends RuntimeException {
    public NoConnectionException(Throwable error) {
        super(error);
        String msg = "SQLState: " + ((SQLException)error).getSQLState()+"; Error Code: " +((SQLException)error).getErrorCode()+"; Message: " + error.getMessage();
        if(error.getCause()!=null) {
            msg = msg+"; Cause: " + error.getCause();
        }
        log.error(msg);
    }
}
