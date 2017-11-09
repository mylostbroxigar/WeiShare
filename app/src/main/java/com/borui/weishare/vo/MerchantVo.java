package com.borui.weishare.vo;

/**
 * Created by zhuborui on 2017/11/9.
 */

public class MerchantVo extends BaseVo {

    /**
     * data : {"id":1,"merchantName":"XX商家","merchantAddress":"XX","merchantType":"1","marchantUsers":"1","merchantCertificateUrl":"1","mercharLegalRepresentative":"1","authorize":"1"}
     */

    private Merchant data;

    public Merchant getData() {
        return data;
    }

    public void setData(Merchant data) {
        this.data = data;
    }

    public static class Merchant {
        /**
         * id : 1
         * merchantName : XX商家
         * merchantAddress : XX
         * merchantType : 1
         * marchantUsers : 1
         * merchantCertificateUrl : 1
         * mercharLegalRepresentative : 1
         * authorize : 1
         */

        private int id;
        private String merchantName;
        private String merchantAddress;
        private String merchantType;
        private String marchantUsers;
        private String merchantCertificateUrl;
        private String mercharLegalRepresentative;
        private String authorize;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public String getMerchantAddress() {
            return merchantAddress;
        }

        public void setMerchantAddress(String merchantAddress) {
            this.merchantAddress = merchantAddress;
        }

        public String getMerchantType() {
            return merchantType;
        }

        public void setMerchantType(String merchantType) {
            this.merchantType = merchantType;
        }

        public String getMarchantUsers() {
            return marchantUsers;
        }

        public void setMarchantUsers(String marchantUsers) {
            this.marchantUsers = marchantUsers;
        }

        public String getMerchantCertificateUrl() {
            return merchantCertificateUrl;
        }

        public void setMerchantCertificateUrl(String merchantCertificateUrl) {
            this.merchantCertificateUrl = merchantCertificateUrl;
        }

        public String getMercharLegalRepresentative() {
            return mercharLegalRepresentative;
        }

        public void setMercharLegalRepresentative(String mercharLegalRepresentative) {
            this.mercharLegalRepresentative = mercharLegalRepresentative;
        }

        public String getAuthorize() {
            return authorize;
        }

        public void setAuthorize(String authorize) {
            this.authorize = authorize;
        }
    }
}
