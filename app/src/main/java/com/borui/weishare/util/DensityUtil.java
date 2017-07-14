package com.borui.weishare.util;

import android.content.Context;

/**
 * Created by borui on 2017/7/12.
 */

public class DensityUtil {


    public static int screenWidth;
    public static int screenHeight;
    public static float dpi;

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        return (int) (dpValue * dpi + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        return (int) (pxValue / dpi + 0.5f);
    }
}
