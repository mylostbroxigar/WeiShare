package com.borui.weishare.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.borui.weishare.vo.ImagePath;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by borui on 2017/6/29.
 */

public class VolleyUtil {
    private static VolleyUtil instance;
    private static RequestQueue mRequestQueue ;
    public static Gson mGson;
    private VolleyUtil(){
        mGson=new GsonBuilder().registerTypeAdapter(String.class,stringNullAdapter).registerTypeAdapter(Double.class,doubleNullAdapter).create();
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



    public void doPost(String url, Map<String,String> params, final Type type,final String tag){

        final Request requet=new JsonRequest(Request.Method.POST, url,type,params, tag,new JsonRequest.ResponseListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                String errorjson="{\"code\"=\"-1\",\"msg\"=\""+error.getMessage()+"\",\"tag\"=\""+tag+"\"}";


                EventBus.getDefault().post(mGson.fromJson(errorjson,type));
            }

            @Override
            public void onResponse(Object response) {
                EventBus.getDefault().post(response);
            }
        });
        requet.setShouldCache(false);
        getRequestQueue().add(requet);
    }

    public void doPost(String url, Map<String,String> params, List<ImagePath> imgPaths, final Type type, final String tag){

        FileUploadUtil fileUploadUtil=new FileUploadUtil();
        fileUploadUtil.FileUpload(url, params, imgPaths, type, tag,new JsonRequest.ResponseListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String errorjson="{\"code\"=\"-1\",\"msg\"=\""+error.getMessage()+"\",\"tag\"=\""+tag+"\"}";

                EventBus.getDefault().post(mGson.fromJson(errorjson,type));
            }

            @Override
            public void onResponse(Object response) {

                EventBus.getDefault().post(response);
            }
        });

    }
//    /**
//     *
//     * @param url  eg:https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=15007167330
//     * @param type
//     */
//    public void doGet(String url,Type type,final String tag){
//        Request requet=new JsonRequest(Request.Method.GET, url,type,tag, new JsonRequest.ResponseListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                BaseVo basevo=new BaseVo();
//                basevo.setCode("-1");
//                basevo.setMsg(error.getMessage());
//                basevo.setTag(tag);
//                EventBus.getDefault().post(basevo);
//            }
//
//            @Override
//            public void onResponse(Object response) {
//                EventBus.getDefault().post(response);
//            }
//        });
//        requet.setShouldCache(false);
//        getRequestQueue().add(requet);
//    }
//
//
//    public void doGetFromAssets(final Context context, final String url, final Type type,final String tag){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                    InputStreamReader inputReader = new InputStreamReader( context.getResources().getAssets().open(url) );
//                    BufferedReader bufReader = new BufferedReader(inputReader);
//                    String line="";
//                    String result="";
//                    while((line = bufReader.readLine()) != null)
//                        result += line;
//
//                    JSONObject jo=new JSONObject(result);
//                    jo.put("tag",tag);
//                    Log.e("=====", "run: reslut="+result );
//                    Object obj=mGson.fromJson(jo.toString(),type);
//                    EventBus.getDefault().post(obj);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }


    public static final TypeAdapter<String> stringNullAdapter = new TypeAdapter<String>() {
        public String read(JsonReader reader) {
            try {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull();
                    return "";//原先是返回Null，这里改为返回空字符串
                }
                return reader.nextString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        public void write(JsonWriter writer, String value) {
            try {
                if (value == null) {
                    writer.nullValue();
                    return;
                }
                writer.value(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public static final TypeAdapter<Double> doubleNullAdapter = new TypeAdapter<Double>() {
        @Override
        public Double read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return 0d;
            }
            return in.nextDouble();
        }

        @Override
        public void write(JsonWriter out, Double value) throws IOException {
            out.value(value);
        }
    };
}

