package com.borui.weishare.net;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

/**
 * Created by borui on 2017/5/16.
 */

public class JsonRequest<T> extends Request<T> {
    public static final String TAG="JsonRequest";
    public interface ResponseListener<T> extends Response.ErrorListener,Response.Listener<T> {

    }

    protected static final String PROTOCOL_CHARSET = "utf-8";
    private ResponseListener mListener;
    private Type mClazz;
    private Map<String,String> mParams;
    private List<String> imgFiles;
    private String mTag;
//    private MultipartEntityBuilder entity =MultipartEntityBuilder.create();
    MultipartBody mutipart;
    public JsonRequest(int method,String url, Type type, String tag,ResponseListener listener){
        super(method, url, listener);
        this.mListener = listener ;
        mClazz = type ;
        mTag=tag;
    }


    public JsonRequest(int method,String url, Type type, Map<String,String> params,String tag,ResponseListener listener){
        super(method, url, listener);
        this.mListener = listener ;
        mClazz = type ;
        mParams=params;
        mTag=tag;
    }



    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            T result ;
//            String jsonString =
//                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            String jsonString =
                    new String(response.data, PROTOCOL_CHARSET);
            JSONObject jo=new JSONObject(jsonString);
            jo.put("tag",mTag);
            jsonString=jo.toString();
            Log.e(TAG, "jsonStr: "+jsonString);
//            Gson mGson=new GsonBuilder().create();
            result = VolleyUtil.mGson.fromJson(jsonString,mClazz) ;
            return Response.success(result,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new VolleyError("不支持的编码"));
        } catch (JSONException e){
            return Response.error(new VolleyError("JSON格式错误"));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }


    public static final TypeAdapter<String> StringAdapter = new TypeAdapter<String>() {
        public String read(JsonReader reader) {
            try {
                Log.e(TAG, "read:reader.peek= "+reader.peek() );
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
}
