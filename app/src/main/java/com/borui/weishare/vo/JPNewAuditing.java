package com.borui.weishare.vo;

/**
 * Created by borui on 2017/12/11.
 */

public class JPNewAuditing extends JPushVo {
    private AuditingVo.AuditingBean data;

    public AuditingVo.AuditingBean getData() {
        return data;
    }

    public void setData(AuditingVo.AuditingBean data) {
        this.data = data;
    }
}
