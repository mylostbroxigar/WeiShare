package com.borui.weishare.vo;

import java.io.Serializable;

/**
 * Created by borui on 2017/6/29.
 */

public class BaseVo implements Serializable {
    private String code;
    private String msg;
    private String tag;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
