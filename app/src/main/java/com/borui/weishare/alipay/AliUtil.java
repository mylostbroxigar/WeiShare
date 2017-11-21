package com.borui.weishare.alipay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.alipay.sdk.app.AuthTask;
import com.borui.weishare.PickCashActivity;
import com.borui.weishare.alipay.util.OrderInfoUtil2_0;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

/**
 * Created by borui on 2017/11/21.
 */

public class AliUtil {

    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "2017111509946623";

    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String PID = "2088821768209759";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String TARGET_ID = "borui1";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCF8pzClj45xhMnbgdPJUpYW0e4Qfb2wwUky+RRd2lzRnddVCup0w3TAmKe9Y4t9RuXh9OesBETL1tWiVUAH9VhZQruSpjwdU7U6jfFep8oq2R21Q620S1rkQmRl46vmaUaMjtdgc/uMex1A7+4ELHJJJ4HAMf1ViSwoR9AbGsebsz+HLASfrFeTIJg2AECz0XBf10934R2sYjDjVuJXJmTkEb+aZ0I1rkYrV7mXk8ql01MC/5FkqvjqoFXds5aG3fD3dfNVRkNLNaKSd4r63qrlquSgvvbW6Q7wWGCYmSx4Jg+v6fu25x+XgMIfuibXZnWFOyzSpFKu/u3LH2ThtEhAgMBAAECggEAa6r7dLId7ISZytz3hRfdba5ZtPNZFzrlfJFjwlQw8BcZRWyt+EEM4KsPKOUK10gpWKE2alzuLLSpP6L+Ly5nb3Uv1yEhVE+HAUAPem45VxCFnbteq3bygn8/6MahS+c9vhPYgi4hPD7m6ZwM1GCe96a2W7y1/i+zKEMrArbjV77TU56A6iMtvVw4xu987AvIykBRdHDM22E/NZDGdwIcUJ9gvYP0RbjIhdbbAImLezxckXJ1wltM873ldLYRy4nuulVHw5nAfWMlDnAw19O0jsCCA9aV5ZjO5rwzD/4XFcRjGWRtVZfqTVtKZjoStl2L9rSIvRuQDmaLYUAvVJ/vnQKBgQD6Ax9LkugFzMBbDNA+q/OPQD/EkAnzRaQoHr8O7tM5I2zjvjaeJYNa8yLn4E5+JPCEtlctZOhnRwb6L1/mOcogt67SBFQcBx5q4mza6LgfevLAIEVIA5D6EgZfJGLSWHn5eyoe0v5oUS3z5v9SSoOUzJvDZfvjnpJxr31eFuIsZwKBgQCJJ9/FRRBNYHIwINU5gRv2d5ALQ+XrF/7b5PevAdb0QmGr9MKKSa3BanzC9owWLi+1jPL6rRR2vRjQX6Hk8fGVslCNsxvXmpjoafhRTZZ7HFJpENdxWksaIYMsVX0G/FY5dh6D2wGgjzBXlxRZpqqbjEg7hb7aLVZ6J5Wx01ohNwKBgQDRUsHmDZbUHE1FLmJAxslCvCFn2s1p8Hnz2WJQB2e0JFPsDns+R9xFzuH/0E8H0LfL9brh1+W5ar+NwxKA+Quh/wiQ8GzXlqJCDVfkpQpb2R3b/GjlVY8RwPuytw1CXi+p0RRAAJpfDvDxkAtfg+HNOM6FeCLAviiQpMTLfU9aqwKBgAqyQGJLE41lQyJ7vQVCC2M6SdKHFgwyj96oSaStWQ0tjB5z9SQBwPo/isFNpvkn2vKmrmBcVSc5chD2FFMdh0CsalnSqQ40cU5WzVXR3jV1rPuio5qkdwzvpiIz/I8p6LOMga64yrSx1gxOrPic5dtBonR55rtvI7pdEfCPUg0bAoGALHD/Ty7qdT/Mpng00ZQwYwKGKw46VjttCqf2FuHH/3jPpTIVMImLVQGUYMBfG2Bcia7dp9RW5eoNpoHBiPbOSy2BUb978SC79FDRsaw+ItlDGO2xEMwxRL6J+ZS07FLPm2uA0sOXRblUqwyTmIUjyM2oPw7vTlesYGnWd1rQbfQ=";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    /**
     * 支付宝账户授权业务
     *
     * @param v
     */
    public static void authV2(final Activity activity) {

        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
                || TextUtils.isEmpty(TARGET_ID)) {
            new AlertDialog.Builder(activity).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(activity);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);
                AuthResult authResult = new AuthResult(result, true);
                EventBus.getDefault().post(authResult);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }
}
