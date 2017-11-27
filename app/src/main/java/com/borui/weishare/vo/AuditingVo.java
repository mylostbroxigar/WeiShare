package com.borui.weishare.vo;

import java.util.List;

/**
 * Created by zhuborui on 2017/11/23.
 */

public class AuditingVo extends BaseVo {

    private List<AuditingBean> data;

    public List<AuditingBean> getData() {
        return data;
    }

    public void setData(List<AuditingBean> data) {
        this.data = data;
    }

    public static class AuditingBean {
        /**
         * id : 3
         * auditingPicture1 : /20171123/auditing/10010/20171123223919_zuimei_wallpaper_b77a4c9568790ee515403c0ffd3514b0_f73796facad0fbe91970363b8b312bd4_0.jpg
         * auditingPicture2 :
         * username : boruiz
         * commission : 3
         */

        private int id;
        private String auditingPicture1;
        private String auditingPicture2;
        private String username;
        private int commission;

        private int auditingStatus;
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAuditingPicture1() {
            return auditingPicture1;
        }

        public void setAuditingPicture1(String auditingPicture1) {
            this.auditingPicture1 = auditingPicture1;
        }

        public String getAuditingPicture2() {
            return auditingPicture2;
        }

        public void setAuditingPicture2(String auditingPicture2) {
            this.auditingPicture2 = auditingPicture2;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getCommission() {
            return commission;
        }

        public void setCommission(int commission) {
            this.commission = commission;
        }

        public int getAuditingStatus() {
            return auditingStatus;
        }

        public void setAuditingStatus(int auditingStatus) {
            this.auditingStatus = auditingStatus;
        }
    }
}
