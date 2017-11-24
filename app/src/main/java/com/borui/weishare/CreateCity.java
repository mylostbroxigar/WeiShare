package com.borui.weishare;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.borui.weishare.view.citypicker.model.City;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by borui on 2017/11/24.
 */

public class CreateCity {

    public void createCity(Context context){
        try {
            long starttime=System.currentTimeMillis();
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open("citys") );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String result="";
            while((line = bufReader.readLine()) != null)
                result += line;
            List<City> citys=new Gson().fromJson(result,new TypeToken<List<City>>(){}.getType());
            Log.e("boorui_city", "createCity1: "+(System.currentTimeMillis()-starttime) );
//            for (City city:citys) {
//                city.setPinyin(toHanyuPinyin(city.getName()));
//            }
            Collections.sort(citys, new Comparator<City>() {
                @Override
                public int compare(City o1, City o2) {
                    return o1.getPinyin().compareTo(o2.getPinyin());
                }
            });
            Log.e("boorui_city", "createCity2: "+(System.currentTimeMillis()-starttime) );
            String str=new Gson().toJson(citys);

            Log.e("boorui_city", "createCity3: "+(System.currentTimeMillis()-starttime) );
            output(str,"citys.txt");

            Log.e("boorui_city", "createCity4: "+(System.currentTimeMillis()-starttime) );
        }catch(Exception e){

        }

    }
    private static void output(String message,String txtName){
        try {
            File logFile=new File(getLogPath()+"/"+txtName);

            FileWriter fw = new FileWriter(logFile,true);
            fw.append(message);

            fw.flush();
            fw.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
    private static String getLogPath(){
        String folderPath = "";
        folderPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/weshanre";
        if(!new File(folderPath).isDirectory()){
            new File(folderPath).mkdirs();
        }
        return folderPath;
    }
    /**
     11      * 将文字转为汉语拼音
     12      * @param chineselanguage 要转成拼音的中文
     13      */
//     public String toHanyuPinyin(String ChineseLanguage){
//            char[] cl_chars = ChineseLanguage.trim().toCharArray();
//             String hanyupinyin = "";
//             HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
//              defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 输出拼音全部小写
//              defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不带声调
//             defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V) ;
//             try {
//                      for (int i=0; i<cl_chars.length; i++){
//                              if (String.valueOf(cl_chars[i]).matches("[\u4e00-\u9fa5]+")){// 如果字符是中文,则将中文转为汉语拼音
//                                        hanyupinyin += PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat)[0];
//                                    } else {// 如果字符不是中文,则不转换
//                                       hanyupinyin += cl_chars[i];
//                                   }
//                           }
//                   } catch (BadHanyuPinyinOutputFormatCombination e) {
//                       System.out.println("字符不能转成汉语拼音");
//                   }
//                return hanyupinyin;
//     }

}
