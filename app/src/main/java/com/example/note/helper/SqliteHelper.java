package com.example.note.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.note.Constans;
import com.example.note.interfaces.CursorCallback;


public class SqliteHelper extends SQLiteOpenHelper {

    private static final String TAG = "SqliteHelper";
    private SQLiteDatabase database;

    public SqliteHelper(Context context){
        super(context, Constans.DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: worked");
        db.execSQL("CREATE TABLE NOTE (ID INTEGER PRIMARY KEY,HEADER TEXT,CONTENT TEXT,STATE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: worked");
    }

    public boolean executeNonQuery(String query) {
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            database.execSQL(query);
            database.close();
            Log.d(TAG, "executeNonQuery: successful:"+query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "executeNonQuery:failed:"+query, e);
            return false;
        }
    }
    public void executeReader(String query, CursorCallback listener) {
        try {
            SQLiteDatabase database = this.getReadableDatabase();
            Cursor cursor = database.rawQuery(query, null);
            Log.d(TAG, "executeReader: successful:"+query);
            listener.read(cursor);
            cursor.close();
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "executeReader: failed:"+query, e);
        }
    }
}
