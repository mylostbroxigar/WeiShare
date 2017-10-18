package com.borui.weishare.net;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by borui on 2017/9/28.
 */

public class FileUploadUtil {
    private String TAG="borui_fileupload";

    private static final String BOUNDARY="******";
    private Handler handler;

    public void FileUpload(final String uploadUrl, final Map<String,String> params, final List<String> imgFiles, final Type type,final String tag, final JsonRequest.ResponseListener listener){
        handler=new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String result=upload(uploadUrl,params,imgFiles);
                Log.e(TAG, "run: result="+result );
                if(listener!=null){
                    if(TextUtils.isEmpty(result)){

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onErrorResponse(new VolleyError("网络错误"));
                            }
                        });
                    }else{
                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    JSONObject jo=new JSONObject(result);
                                    jo.put("tag",tag);
                                    listener.onResponse(new Gson().fromJson(jo.toString(),type));
                                } catch (JSONException e) {
                                    listener.onErrorResponse(new VolleyError("JSON解析错误"));
                                }

                            }
                        });
                    }
                }

            }
        }).start();
    }
    public String upload(String uploadUrl, Map<String,String> params, List<String> imgFiles){
        String resultStr="";
        try {
            URL url = new URL(uploadUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
//            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type","multipart/form-data;boundary=" + BOUNDARY);

//            httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());

            //传参数
            StringBuilder textEntity = new StringBuilder();
//            for (Map.Entry<String, String> entry : params.entrySet()) {
//                textEntity.append( entry.getKey() + "="+entry.getValue());
//                textEntity.append("&");
//            }

            for (Map.Entry<String, String> entry : params.entrySet()) {
                textEntity.append("--"+BOUNDARY+"\r\n");
                textEntity.append("Content-Disposition: form-data; name=\""+ entry.getKey() + "\"\r\n\r\n");
                textEntity.append(entry.getValue());
                textEntity.append("\r\n");
            }
            textEntity.append("--"+BOUNDARY+"\r\n");
            Log.e(TAG, "FileUpload: upload params="+textEntity.toString() );
            dos.write(textEntity.toString().getBytes());
            //传文件
            int N = imgFiles==null?0:imgFiles.size() ;
            for (int i = 0; i < N ;i++){
                Log.e(TAG, "FileUpload: strat upload "+i);
                File imagefile=new File(imgFiles.get(i));
                StringBuffer sb= new StringBuffer() ;
            /*第一行*/
                sb.append("--"+BOUNDARY+"\r\n");
            /*第二行*/
                sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"") ;
                sb.append(imagefile.getName()) ;
                sb.append("\"\r\n") ;
            /*第三行*/
                sb.append("Content-Type: image/*\r\n") ;
            /*第四行*/
                sb.append("\r\n") ;
            /*第五行*/
                dos.write(sb.toString().getBytes());

                FileInputStream fis = new FileInputStream(imagefile);
                byte[] buffer = new byte[8192]; // 8k
                int count = 0;
                while ((count = fis.read(buffer)) != -1)
                {
                    dos.write(buffer, 0, count);

                }
                fis.close();
                dos.write("\r\n".getBytes());

            }
        /*结尾行*/
            String endLine = "--" + BOUNDARY + "--" + "\r\n" ;
            dos.write(endLine.toString().getBytes("utf-8"));

            int responseCode=httpURLConnection.getResponseCode();
            Log.e(TAG, "upload: responseCode="+responseCode );
            if(responseCode==200){

                //获取返回数据
                InputStream is = httpURLConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is, "utf-8");
                BufferedReader br = new BufferedReader(isr);
                resultStr = br.readLine();

                is.close();
            }

            dos.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return resultStr;
    }

}
