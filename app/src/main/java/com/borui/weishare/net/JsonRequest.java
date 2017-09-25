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
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

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
            jsonString=jsonString.substring(jsonString.indexOf("{"));
            Log.i(TAG, "jsonStr: "+jsonString);
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

//    @Override
//    public byte[] getBody() {
//        Log.e("JsonRequest", "JsonRequest getBody: "+mRequestBody );
//        try {
//            return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
//        } catch (UnsupportedEncodingException uee) {
//            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
//                    mRequestBody, PROTOCOL_CHARSET);
//            return null;
//        }
//    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }
}
