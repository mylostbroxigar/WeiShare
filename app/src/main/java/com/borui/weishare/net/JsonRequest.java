package com.borui.weishare.net;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

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
    private Gson mGson;
    private Type mClazz;
    private Map<String,String> mParams;
    private List<String> imgFiles;
//    private MultipartEntityBuilder entity =MultipartEntityBuilder.create();
    MultipartBody mutipart;
    public JsonRequest(int method,String url, Type type, ResponseListener listener){
        super(method, url, listener);
        this.mListener = listener ;
        mGson = new Gson();
        mClazz = type ;
    }


    public JsonRequest(int method,String url, Type type, Map<String,String> params,ResponseListener listener){
        super(method, url, listener);

        this.mListener = listener ;
        mGson = new Gson();
        mClazz = type ;
        mParams=params;
    }



    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            T result ;
//            String jsonString =
//                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            String jsonString =
                    new String(response.data, PROTOCOL_CHARSET);
            Log.e(TAG, "jsonStr: "+jsonString);
            result = mGson.fromJson(jsonString,mClazz) ;
            return Response.success(result,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
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
}
