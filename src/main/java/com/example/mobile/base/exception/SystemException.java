package com.example.mobile.base.exception;

import com.example.mobile.utils.PropertiesUtil;
import lombok.Data;

/**
 * 自定义异常
 */
@Data
public class SystemException extends RuntimeException {


    /**
     * 错误码
     */
    private int errorCode;

    /**
     * 错误信息描述
     */
    private String errorDes;

    public SystemException() {
        super();
    }

    public SystemException(int errorCode) {
        super(PropertiesUtil.getString(errorCode));
        this.errorCode = errorCode;
        this.errorDes = PropertiesUtil.getString(errorCode);
    }

    public SystemException(int errorCode, String errorDes) {
        super(errorDes);
        this.errorCode = errorCode;
        this.errorDes = errorDes;
    }


}
