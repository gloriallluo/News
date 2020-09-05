package com.java.jingjia.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_LAND = "create table Land ("
            //primary key设置为主键，autoincrement关键字表示该列是自增长的
            + "area_id text primary key autoincrement,"
            + "flag text,"
            + "plot integer,"
            + "land_position_id text,"
            + "land_position_acreage real)";
    private Context mContext;
    private static final int VERSION = 1;

    /**
     * 构造函数
     */
    public DatabaseHelper(Context context,/**Activity对象*/
                          String name,/**数据库的名字*/
                          SQLiteDatabase.CursorFactory factory,/**暂时不去了解，调用的时候传入空值*/
                          int version/**当前数据库的版本号（正数、递增）*/
    ) {
        super(context, name, factory, version);
        mContext = context;
    }

    /**
     * 构造函数
     */
    public DatabaseHelper(Context context, String name) {
        this(context, name, VERSION);
    }

    /**
     * 构造函数
     */
    public DatabaseHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    /**
     * 构造函数
     */
    public DatabaseHelper(Context context,
                          String name,
                          SQLiteDatabase.CursorFactory factory,
                          int version,
                          DatabaseErrorHandler errorHandler
    ) {
        super(context, name, factory, version, errorHandler);
    }

    /**
     * onCreate函数在第一次创建数据库的时候执行
     * 在第一次得到SQLiteDatabase对象的时候会调用此方法
     * 所以生成一个DatabaseHelper对象不会调用此方法
     * 当调用DatabaseHelper对象的
     * getReadableDatabase() 或者 getWritableDatabase() 创建数据库这个方法才会被调用
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        System.out.println("creat a Database");
        /**
         * 官方说明：
         * Execute a single SQL statement that is NOT a SELECT or any other SQL statement that returns data.
         *
         * 创建一个用户数据表并定义表结构
         * */
        db.execSQL("create table user(id int ,name varchar(20))");

        //调用SQLiteDatabase的execSQL()方法执行建表语句
        db.execSQL(CREATE_LAND);
        //弹出一个Toast提示创建成功
        Toast.makeText(mContext, "Create succeeded.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("update a Database");
    }
}