package com.borui.weishare.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.borui.weishare.vo.MessageVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by borui on 2017/12/12.
 */

public class SqliteUtil {
    public static SqliteUtil theSqliteUtil;
    private String lock="lock";
    private SqliteUtil(){
    }
    public static SqliteUtil getInstance(){

        if(theSqliteUtil==null)
            theSqliteUtil=new SqliteUtil();
        return theSqliteUtil;
    }

    public void insertMessage(Context context,MessageVo messageVo){
        synchronized (lock) {
            SqliteHelper helper=new SqliteHelper(context);
            SQLiteDatabase db=helper.getWritableDatabase();
            db.insert(SqliteHelper.MESSAGE_TABLE_NAME, null, messageVo.getValues());
            db.close();
            helper.close();
        }
    }

    public List<MessageVo> getMessageList(Context context, int user_id){
        ArrayList<MessageVo> messageList = new ArrayList<MessageVo>();
        synchronized (lock) {

            SqliteHelper helper=new SqliteHelper(context);
            SQLiteDatabase db=helper.getWritableDatabase();

            Cursor cursor = db.query(SqliteHelper.MESSAGE_TABLE_NAME, null, "user_id=?", new String[]{""+user_id}, null, null, "time desc");
            while (cursor.moveToNext()) {
                messageList.add(getMessage(cursor));
            }
            cursor.close();
            db.close();
            helper.close();
        }
        return messageList;
    }
    public boolean newMessageExist(Context context,int user_id){
        boolean exist=false;
        synchronized (lock) {

            SqliteHelper helper=new SqliteHelper(context);
            SQLiteDatabase db=helper.getWritableDatabase();

            Cursor cursor = db.query(SqliteHelper.MESSAGE_TABLE_NAME, null, "user_id=? and readed=?", new String[]{""+user_id,"0"}, null, null, "time desc");
            if(cursor.getCount()>0){
                exist=true;
            }
            cursor.close();
            db.close();
            helper.close();
        }
        return exist;
    }
    private MessageVo getMessage(Cursor cursor) {
        MessageVo message = new MessageVo();
        message.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
        message.setUser_id(cursor.getInt(cursor.getColumnIndex("user_id")));
        message.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        message.setContent(cursor.getString(cursor.getColumnIndex("content")));
        message.setAction(cursor.getString(cursor.getColumnIndex("action")));
        message.setTime(cursor.getLong(cursor.getColumnIndex("time")));
        message.setReaded(cursor.getInt(cursor.getColumnIndex("readed")));
        return message;
    }

}
