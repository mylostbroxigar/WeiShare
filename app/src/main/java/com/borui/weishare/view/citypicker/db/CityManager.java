package com.borui.weishare.view.citypicker.db;

import android.content.Context;

import com.borui.weishare.view.citypicker.model.City;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by borui on 2017/11/24.
 */

public class CityManager {
    public static void getAllCities(final Context context){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<City> citys=null;
                try {
                    InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open("citys") );
                    BufferedReader bufReader = new BufferedReader(inputReader);
                    String line="";
                    String result="";
                    while((line = bufReader.readLine()) != null)
                        result += line;
                    citys=new Gson().fromJson(result,new TypeToken<List<City>>(){}.getType());
                }catch (Exception e){

                }
                EventBus.getDefault().post(citys);
            }
        }).start();

    }
}
