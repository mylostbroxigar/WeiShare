package com.borui.weishare.vo;

import android.content.ContentValues;

/**
 * Created by borui on 2017/12/12.
 */

public class MessageVo {
    private int _id;
    private int user_id;
    private String title;
    private String content;
    private long time;
    private String action;
    private int readed;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getReaded() {
        return readed;
    }

    public void setReaded(int readed) {
        this.readed = readed;
    }

    public ContentValues getValues(){
        ContentValues values=new ContentValues();
        values.put("user_id",user_id);
        values.put("title",title);
        values.put("content",content);
        values.put("action",action);
        values.put("time",time);
        values.put("readed",readed);
        return values;
    }
}
