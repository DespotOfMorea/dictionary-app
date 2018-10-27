package com.gzs.log;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Data
@NoArgsConstructor
@Slf4j
public class CustomInfo {

    enum CustomInfos {DEBUG,ERROR,INFO,TRACE,QUESTION,WARN}
    private CustomInfos type;
    private String message;

    public CustomInfo(char type, String message) {
        this.message = message;
        this.type = getType(type);
    }

    private CustomInfos getType(char type){
        CustomInfos enumType;
        type=Character.toLowerCase(type);
        switch(type) {
            case 'd' : enumType=CustomInfos.DEBUG; log.debug(this.message);  break;
            case 'e' : enumType=CustomInfos.ERROR; log.error(this.message);  break;
            case 'i' : enumType=CustomInfos.INFO; log.info(this.message);  break;
            case 't' : enumType=CustomInfos.TRACE; log.trace(this.message);  break;
            case 'q' : enumType=CustomInfos.QUESTION; log.info(this.message);  break;
            case 'w' : enumType=CustomInfos.WARN; log.warn(this.message);  break;
            default : enumType=CustomInfos.INFO;  log.info(this.message);
        }
        return enumType;
    }
}
