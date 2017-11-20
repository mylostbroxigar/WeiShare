package com.borui.weishare.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.borui.weishare.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by borui on 2017/10/18.
 */

public class ImageUtil {
    /**
     * 压缩图片
     *
     * @param path
     * @param maxWidth
     * @param maxHeight
     */
    public static String compressImage(String path, int maxWidth, int maxHeight) {
        Bitmap bmp = BitmapFactory.decodeFile(path);
        if(bmp==null||bmp.getWidth()==0||bmp.getHeight()==0)
            return null;
        String newPath=SdcardUtil.getCompressFolder()+"/weshare_"+System.currentTimeMillis()+".jpg";
        float scalex = (float) bmp.getWidth() / (float) maxWidth;
        float scaleY = (float) bmp.getHeight() / (float) maxHeight;
        float scale = scalex > scaleY ? scalex : scaleY;
        if(scale<1)
            scale=1;
        bmp = Bitmap.createScaledBitmap(bmp, (int) (bmp.getWidth() / scale), (int) (bmp.getHeight() / scale), true);
        File file = new File(newPath);
        file.delete();
        FileOutputStream fos = null;
        try {
            file.createNewFile();
            fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return newPath;
    }

    public static void deleteImage(String path){
        if(new File(path).exists()){
            new File(path).delete();
        }
    }

    public static Drawable getErrorDrawable(Context context, int width, int height){
        Bitmap bitmap=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        canvas.drawColor(context.getResources().getColor(R.color.error_img_bg));
        Bitmap errBmp= BitmapFactory.decodeResource(context.getResources(),R.drawable.image_error);
        Rect src=new Rect(0,0,errBmp.getWidth(),errBmp.getHeight());
        Rect dst=new Rect(width*3/8,height/2-width/8,width*5/8,height/2+width/8);
        canvas.drawBitmap(errBmp,src,dst,null);
        return new BitmapDrawable(bitmap);
    }
    public static Drawable getDefultDrawable(Context context, int width, int height){
        Bitmap bitmap=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bitmap);
        canvas.drawColor(context.getResources().getColor(R.color.default_img_bg));
        Bitmap errBmp= BitmapFactory.decodeResource(context.getResources(),R.drawable.image_default);
        Rect src=new Rect(0,0,errBmp.getWidth(),errBmp.getHeight());
        Rect dst=new Rect(width*3/8,height/2-width/8,width*5/8,height/2+width/8);
        canvas.drawBitmap(errBmp,src,dst,null);
        return new BitmapDrawable(bitmap);
    }
}
