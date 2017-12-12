package com.borui.weishare.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by borui on 2017/12/12.
 */

public class SqliteHelper extends SQLiteOpenHelper {

    public static final int DATA_VERSION=1;
    public static final String DB_NAME="weishare.db";
    public static final String MESSAGE_TABLE_NAME="message_table";
    private String createMessageTable;
    public SqliteHelper(Context context) {
        super(context, DB_NAME, null, DATA_VERSION);
        createMessageTable();
    }
    private void createMessageTable(){
        StringBuilder sb=new StringBuilder();
        sb.append("create table "+MESSAGE_TABLE_NAME)
                .append("(")
                .append("_id integer not null primary key autoincrement,")
                .append("user_id integer not null,")
                .append("title text not null,")
                .append("content text not null,")
                .append("action text,")
                .append("time integer not null,")
                .append(")");
        createMessageTable=sb.toString();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createMessageTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
