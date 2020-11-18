package com.wmz.utils.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admin on 2018/9/5.
 */

public class DBHelper extends SQLiteOpenHelper {

  private final static String DB_NAME ="test.db";//数据库名
  private final static int VERSION = 1;//版本号

  String sql = "create table student(" +
       "id integer primary key autoincrement," +
       "name varchar(20)," +
       "age int)";
  public DBHelper(Context context) {
    super(context, DB_NAME, null, VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(sql);

  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }
}
