package com.borui.weishare.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhuborui on 2017/11/9.
 */

public class MerchantVo extends BaseVo {

    /**
     * data : {"authorize":"","id":10001,"mercharLegalRepresentative":"/20171114/merchant/55555/licences/20171114215816_open_screen_sub_title_img_1339.png","deposit":12.34,"merchantType":"3","merchantName":"55555","commission":14,"pageURL":"","merchantAddress":"55555","merchantCertificateUrl":"/20171114/merchant/55555/licences/20171114215816_screen_lock_30157.png","merchantUsers":"6"}
     */

    private Merchant data;

    public Merchant getData() {
        return data;
    }

    public void setData(Merchant data) {
        this.data = data;
    }

    public static class Merchant implements Parcelable{
        /**
         * authorize :
         * id : 10001
         * mercharLegalRepresentative : /20171114/merchant/55555/licences/20171114215816_open_screen_sub_title_img_1339.png
         * deposit : 12.34
         * merchantType : 3
         * merchantName : 55555
         * commission : 14
         * pageURL :
         * merchantAddress : 55555
         * merchantCertificateUrl : /20171114/merchant/55555/licences/20171114215816_screen_lock_30157.png
         * merchantUsers : 6
         */

        private String authorize;
        private int id;
        private String mercharLegalRepresentative;
        private double deposit;
        private String merchantType;
        private String merchantName;
        private int commission;
        private String pageURL;
        private String merchantAddress;
        private String merchantCertificateUrl;
        private String merchantUsers;
        public Merchant(){

        }



        public Merchant(Parcel in){
            authorize=in.readString();
            id=in.readInt();
            mercharLegalRepresentative=in.readString();
            deposit=in.readDouble();
            merchantType=in.readString();
            merchantName=in.readString();
            commission=in.readInt();
            pageURL=in.readString();
            merchantAddress=in.readString();
            merchantCertificateUrl=in.readString();
            merchantUsers=in.readString();

        }



        public static final Parcelable.Creator<Merchant> CREATOR=new Parcelable.Creator<Merchant>() {

            @Override

            public Merchant createFromParcel(Parcel in) {

                return new Merchant(in);

            }



            @Override

            public Merchant[] newArray(int i) {

                return new Merchant[0];

            }

        };

        @Override

        public int describeContents() {

            return 0;

        }



        @Override

        public void writeToParcel(Parcel out, int i) {

            out.writeString(authorize);
            out.writeInt(id);
            out.writeString(mercharLegalRepresentative);
            out.writeDouble(deposit);
            out.writeString(merchantType);
            out.writeString(merchantName);
            out.writeInt(commission);
            out.writeString(pageURL);
            out.writeString(merchantAddress);
            out.writeString(merchantCertificateUrl);
            out.writeString(merchantUsers);

        }
        public String getAuthorize() {
            return authorize;
        }

        public void setAuthorize(String authorize) {
            this.authorize = authorize;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMercharLegalRepresentative() {
            return mercharLegalRepresentative;
        }

        public void setMercharLegalRepresentative(String mercharLegalRepresentative) {
            this.mercharLegalRepresentative = mercharLegalRepresentative;
        }

        public double getDeposit() {
            return deposit;
        }

        public void setDeposit(double deposit) {
            this.deposit = deposit;
        }

        public String getMerchantType() {
            return merchantType;
        }

        public void setMerchantType(String merchantType) {
            this.merchantType = merchantType;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public int getCommission() {
            return commission;
        }

        public void setCommission(int commission) {
            this.commission = commission;
        }

        public String getPageURL() {
            return pageURL;
        }

        public void setPageURL(String pageURL) {
            this.pageURL = pageURL;
        }

        public String getMerchantAddress() {
            return merchantAddress;
        }

        public void setMerchantAddress(String merchantAddress) {
            this.merchantAddress = merchantAddress;
        }

        public String getMerchantCertificateUrl() {
            return merchantCertificateUrl;
        }

        public void setMerchantCertificateUrl(String merchantCertificateUrl) {
            this.merchantCertificateUrl = merchantCertificateUrl;
        }

        public String getMerchantUsers() {
            return merchantUsers;
        }

        public void setMerchantUsers(String merchantUsers) {
            this.merchantUsers = merchantUsers;
        }
    }
}
