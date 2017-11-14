package com.borui.weishare.vo;

import android.os.Parcel;
import android.os.Parcelable;

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

    public static class Merchant implements Parcelable {
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

        public Merchant(){

        }

        public Merchant(Parcel in){
            id=in.readInt();
            merchantName=in.readString();
            merchantAddress=in.readString();
            merchantType=in.readString();
            marchantUsers=in.readString();
            merchantCertificateUrl=in.readString();
            mercharLegalRepresentative=in.readString();
            authorize=in.readString();
        }

        public static final Parcelable.Creator<Merchant> CREATOR=new Creator<Merchant>() {
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
            out.writeInt(id);
            out.writeString("merchantName");
            out.writeString("merchantAddress");
            out.writeString("merchantType");
            out.writeString("marchantUsers");
            out.writeString("merchantCertificateUrl");
            out.writeString("mercharLegalRepresentative");
            out.writeString("authorize");
        }

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
