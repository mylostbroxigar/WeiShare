package com.borui.weishare.vo;

import java.io.Serializable;

/**
 * Created by borui on 2017/6/29.
 */

public class BaseVo implements Serializable {
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
