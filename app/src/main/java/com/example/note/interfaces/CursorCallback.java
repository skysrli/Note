package com.example.note.interfaces;

import android.database.Cursor;

public interface CursorCallback {
    void read(Cursor cursor) throws Exception;
}
