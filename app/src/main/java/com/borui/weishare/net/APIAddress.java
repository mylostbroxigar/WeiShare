package com.borui.weishare.net;

/**
 * Created by borui on 2017/6/29.
 */

public class APIAddress {
    public static final String SERVERADDRESS="http://106.15.193.137:80/";

    public static final String IMAGEPATH=SERVERADDRESS+"weshare/upload/pic/";
    public static final String ROOT=SERVERADDRESS+"weshare/api/";

    public static final String LOGIN=ROOT+"login";
    public static final String REGISTER=ROOT+"user/register";
    public static final String GETVILIDATECODE=ROOT+"user/getValidateCode";
    public static final String LOCAL_SHARE=ROOT+"localshare/share";
    public static final String LOCAL_SHARE_DETAIL=ROOT+"localshare/share/infos";
    public static final String SHARE_CATE=ROOT+"dict/listDicts";
    public static final String QUERYSHARES=ROOT+"localshare/query";
    public static final String SEND_SCREENSHOT=ROOT+"auditing/application";
    public static final String GET_MERCHANT=ROOT+"merchant/getWeshareMerchant";
    public static final String MERCHANT_REGISTER=ROOT+"merchant/authentication";

    public static final String COLLECTION=ROOT+"localshare/collections";
    public static final String LIKE=ROOT+"localshare/thumbs";

    //支付相关
    public static final String ALIPAY_WEIXIN_AUTH=ROOT+"user/auth/code";
}
