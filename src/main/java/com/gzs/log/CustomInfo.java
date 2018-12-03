package com.gzs.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class CustomInfo {

    private InfoTypes type;
    private String message;

    public CustomInfo(char type, String message) {
        this.type = InfoTypes.get(type);
        this.message = message;
        printMessage2Log();
    }

    private void printMessage2Log () {
        switch(this.type) {
            case DEBUG: log.debug(this.message);  break;
            case ERROR: log.error(this.message);  break;
            case INFO: log.info(this.message);  break;
            case TRACE: log.trace(this.message);  break;
            case QUESTION: log.info(this.message);  break;
            case WARN: log.warn(this.message);  break;
            default: log.info(this.message);
        }
    }
}