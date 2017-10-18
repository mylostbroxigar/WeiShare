package com.borui.weishare.util;

import android.nfc.Tag;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by borui on 2017/10/18.
 */

public class SdcardUtil {

    static String TAG="sdcardUtil";
    private static String getRootFolder(){
        String folderPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/weshare";
        Log.e(TAG, "getRootFolder: 1" );
        if(!new File(folderPath).exists()){
            Log.e(TAG, "getRootFolder: 2" );
            boolean result=new File(folderPath).mkdirs();
            Log.e(TAG, "getRootFolder: mkdir="+result );
        }

        return folderPath;
    }
    public static String getCompressFolder(){
        String compressFolder=getRootFolder()+"/compress";
        if(!new File(compressFolder).exists()){
            new File(compressFolder).mkdirs();
        }

        return compressFolder;
    }
}
