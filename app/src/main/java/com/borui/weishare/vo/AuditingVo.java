package com.borui.weishare.vo;

import java.util.List;

/**
 * Created by zhuborui on 2017/11/23.
 */

public class AuditingVo extends BaseVo {

    private List<AuditingItem> data;

    public List<AuditingItem> getData() {
        return data;
    }

    public void setData(List<AuditingItem> data) {
        this.data = data;
    }

    public static class AuditingItem {
        /**
         * id : 3
         * merchantId : 10010
         * userId : 2
         * auditingPicture1 : /20171123/auditing/10010/20171123223919_zuimei_wallpaper_b77a4c9568790ee515403c0ffd3514b0_f73796facad0fbe91970363b8b312bd4_0.jpg
         * auditingPicture2 :
         * auditingTime : 2017-11-23 22:39:19
         * auditingStatus : 1
         * comment :
         * uploadTime : 2017-11-23 22:39:19
         */

        private int id;
        private int merchantId;
        private int userId;
        private String auditingPicture1;
        private String auditingPicture2;
        private String auditingTime;
        private int auditingStatus;
        private String comment;
        private String uploadTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
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

        public String getAuditingTime() {
            return auditingTime;
        }

        public void setAuditingTime(String auditingTime) {
            this.auditingTime = auditingTime;
        }

        public int getAuditingStatus() {
            return auditingStatus;
        }

        public void setAuditingStatus(int auditingStatus) {
            this.auditingStatus = auditingStatus;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(String uploadTime) {
            this.uploadTime = uploadTime;
        }
    }
}
