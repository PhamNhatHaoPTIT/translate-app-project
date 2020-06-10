package com.example.apaar97.translate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WordDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "DEMO";
    public static final String TABLE_NAME = "Word";

    private static final String CREATE_TABLE = ""
            + "create table " + TABLE_NAME + " ( "
            + "id" + " integer primary key autoincrement ,"
            + "codeFrom" + " text not null, "
            + "codeTo" + " text not null, "
            + "wordFrom" + " text not null, "
            + "wordTo" + " text not null, "
            + "isFavorite" + " interger not null, "
            + "isDelete" + " integer not null );";

    public WordDBHelper(Context context) {
        super(context,DB_NAME,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
