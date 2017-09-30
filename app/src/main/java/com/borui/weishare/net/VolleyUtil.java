package com.borui.weishare.net;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.borui.weishare.vo.BaseVo;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by borui on 2017/6/29.
 */

public class VolleyUtil {
    private static VolleyUtil instance;
    private static RequestQueue mRequestQueue ;
    private VolleyUtil(){

    }

    public static VolleyUtil getInstance(){
        if(instance==null)
            instance=new VolleyUtil();
        return instance;
    }

    public void init(Context context){
        if (mRequestQueue == null){
            synchronized (VolleyUtil.class){
                if (mRequestQueue == null){
                    mRequestQueue = Volley.newRequestQueue(context) ;
                }
            }
        }
        mRequestQueue.start();
    }
    public RequestQueue getRequestQueue(){
        if (mRequestQueue == null)
            throw new RuntimeException("requestQueue has not init ") ;
        return mRequestQueue ;
    }



    public void doPost(String url, Map<String,String> params, Type type,final String tag){

        final Request requet=new JsonRequest(Request.Method.POST, url,type,params, tag,new JsonRequest.ResponseListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                BaseVo basevo=new BaseVo();
                basevo.setCode("-1");
                basevo.setMsg(error.getMessage());
                basevo.setTag(tag);
                EventBus.getDefault().post(basevo);
            }

            @Override
            public void onResponse(Object response) {
                EventBus.getDefault().post(response);
            }
        });
        requet.setShouldCache(false);
        getRequestQueue().add(requet);
    }

    public void doPost(String url, Map<String,String> params, List<String> imgPaths, Type type,final String tag){

        FileUploadUtil fileUploadUtil=new FileUploadUtil();
        fileUploadUtil.FileUpload(url, params, imgPaths, type, tag,new JsonRequest.ResponseListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                BaseVo basevo=new BaseVo();
                basevo.setCode("-1");
                basevo.setMsg(error.getMessage());
                basevo.setTag(tag);
                EventBus.getDefault().post(basevo);
            }

            @Override
            public void onResponse(Object response) {

                BaseVo vo=(BaseVo)response;
                EventBus.getDefault().post(vo);
            }
        });

    }
    /**
     *
     * @param url  eg:https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=15007167330
     * @param type
     */
    public void doGet(String url,Type type,final String tag){
        Request requet=new JsonRequest(Request.Method.GET, url,type,tag, new JsonRequest.ResponseListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                BaseVo basevo=new BaseVo();
                basevo.setCode("-1");
                basevo.setMsg(error.getMessage());
                basevo.setTag(tag);
                EventBus.getDefault().post(basevo);
            }

            @Override
            public void onResponse(Object response) {
                EventBus.getDefault().post(response);
            }
        });
        requet.setShouldCache(false);
        getRequestQueue().add(requet);
    }


    public void doGetFromAssets(final Context context, final String url, final Type type,final String tag){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    InputStreamReader inputReader = new InputStreamReader( context.getResources().getAssets().open(url) );
                    BufferedReader bufReader = new BufferedReader(inputReader);
                    String line="";
                    String result="";
                    while((line = bufReader.readLine()) != null)
                        result += line;

                    JSONObject jo=new JSONObject(result);
                    jo.put("tag",tag);
                    Gson mGson=new Gson();
                    Log.e("=====", "run: reslut="+result );
                    Object obj=mGson.fromJson(jo.toString(),type);
                    EventBus.getDefault().post(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

