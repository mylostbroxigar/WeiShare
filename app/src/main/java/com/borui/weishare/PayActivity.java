package com.borui.weishare;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.borui.weishare.alipay.AuthResult;
import com.borui.weishare.alipay.PayResult;
import com.borui.weishare.alipay.util.OrderInfoUtil2_0;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by borui on 2017/11/14.
 */

public class PayActivity extends Activity {
    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "2017111509946623";

    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String PID = "2088821768209759";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String TARGET_ID = "borui2";

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
    @BindView(R.id.rb_100)
    RadioButton rb100;
    @BindView(R.id.rb_200)
    RadioButton rb200;
    @BindView(R.id.rb_500)
    RadioButton rb500;
    @BindView(R.id.rb_1000)
    RadioButton rb1000;
    @BindView(R.id.rg_money)
    RadioGroup rgMoney;
    @BindView(R.id.btn_pay)
    Button btnPay;
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(PayActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(PayActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_pay)
    public void onViewClicked() {
        payV2(btnPay);
    }

    /**
     * 支付宝支付业务
     *
     * @param v
     */
    public void payV2(View v) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

}
