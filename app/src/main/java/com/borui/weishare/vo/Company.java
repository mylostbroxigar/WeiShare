package com.borui.weishare.vo;

/**
 * Created by zhuborui on 2017/7/4.
 */

public class Company extends BaseVo {

    /**
     * commission : 5
     */

    private int commission;
    private int isElebus;
    private String thumbUrl;

    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }

    public int getIsElebus() {
        return isElebus;
    }

    public void setIsElebus(int isElebus) {
        this.isElebus = isElebus;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }
}
