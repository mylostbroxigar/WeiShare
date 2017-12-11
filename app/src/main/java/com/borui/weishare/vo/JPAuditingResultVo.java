package com.borui.weishare.vo;

/**
 * Created by borui on 2017/12/11.
 */

public class JPAuditingResultVo extends JPushVo {
    private AuditingResult data;

    public AuditingResult getData() {
        return data;
    }

    public void setData(AuditingResult data) {
        this.data = data;
    }
    public static class AuditingResult{
        private int auditingStatus;
        private int commission;
        private String merchantName;
        private String time;

        public int getAuditingStatus() {
            return auditingStatus;
        }

        public void setAuditingStatus(int auditingStatus) {
            this.auditingStatus = auditingStatus;
        }

        public int getCommission() {
            return commission;
        }

        public void setCommission(int commission) {
            this.commission = commission;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
