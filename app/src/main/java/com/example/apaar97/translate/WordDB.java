package com.example.apaar97.translate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class WordDB {
    SQLiteDatabase database;
    WordDBHelper dbHelper;

    public WordDB(Context context) {
        dbHelper = new WordDBHelper(context);
        try {
            database = dbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            database = dbHelper.getReadableDatabase();
        }
    }

    public Cursor getAllWord() {
        String[] rows = {"id", "codeFrom", "codeTo", "wordFrom", "wordTo", "isFavorite", "isDelete"};
        Cursor cursor = null;
        cursor = database.query(WordDBHelper.TABLE_NAME, rows, null, null, null, null, "id" + " DESC");
        return cursor;
    }

    public long add(Word word) {
        ContentValues values = new ContentValues();
        putValue(values, word);
        return database.insert(WordDBHelper.TABLE_NAME, null, values);
    }

    public long change(Word word) {
        ContentValues values = new ContentValues();
        putValue(values, word);
        return database.update(WordDBHelper.TABLE_NAME, values, "id = " + word.getId(), null);
    }

    public long delete(Word word) {
        String id = word.getId() + "";
        return database.delete("Word", "id = " + "'" + id + "'", null);
    }

    private void putValue(ContentValues values, Word word) {
        values.put("codeFrom", word.getCodeFrom());
        values.put("codeTo", word.getCodeTo());
        values.put("wordFrom", word.getWordFrom());
        values.put("wordTo", word.getWordTo());
        values.put("isFavorite", word.getIsFavorite());
        values.put("isDelete", word.getIsDelete());
    }

}
