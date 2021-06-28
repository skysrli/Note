package com.example.note.entity;

import android.content.Context;
import android.database.Cursor;

import com.example.note.enums.NoteState;
import com.example.note.helper.SqliteHelper;
import com.example.note.interfaces.CursorCallback;
import com.example.note.model.Note;
import com.example.note.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class ENote {

    private SqliteHelper sqliteHelper;

    public ENote(Context context){
        sqliteHelper = new SqliteHelper(context);
    }

    public void insertNote(Note note){
        String query = "INSERT INTO NOTE (HEADER,CONTENT,STATE) VALUES ('${header}','${content}',${state})";
        HashMap<String,String> map = new HashMap<>();
        map.put("header",note.getHeader());
        map.put("content",note.getContent());
        map.put("state",String.valueOf(note.getState().getValue()));
        query = StringUtil.format(query,map);
        sqliteHelper.executeNonQuery(query);
    }
    public void updateNote(Note note){
        String query = "UPDATE NOTE SET HEADER = '${header}', CONTENT = '${content}', STATE = ${state} WHERE ID = ${id}";
        HashMap<String,String> map = new HashMap<>();
        map.put("id",String.valueOf(note.getId()));
        map.put("header",note.getHeader());
        map.put("content",note.getContent());
        map.put("state",String.valueOf(note.getState().getValue()));
        query = StringUtil.format(query,map);
        sqliteHelper.executeNonQuery(query);
    }
    public void deleteNote(Note note){
        String query = "DELETE FROM NOTE WHERE ID = ${id}";
        HashMap<String,String> map = new HashMap<>();
        map.put("id",String.valueOf(note.getId()));
        query = StringUtil.format(query,map);
        sqliteHelper.executeNonQuery(query);
    }

    public ArrayList<Note> getNotes(){
        ArrayList<Note> notes = new ArrayList<>();
        String query = "SELECT ID,HEADER,CONTENT,STATE FROM NOTE";
        sqliteHelper.executeReader(query, new CursorCallback() {
            @Override
            public void read(Cursor cursor) throws Exception {
                if(cursor.moveToFirst()){
                    do{
                        Note note = new Note();
                        note.setId(cursor.getLong(0));
                        note.setHeader(cursor.getString(1));
                        note.setContent(cursor.getString(2));
                        note.setState(NoteState.fromInt(cursor.getInt(3)));
                        notes.add(note);
                    }while (cursor.moveToNext());
                }

            }
        });
        return notes;
    }
    public ArrayList<Note> getNotesByState(NoteState state){
        ArrayList<Note> notes = new ArrayList<>();
        String query = "SELECT ID,HEADER,CONTENT,STATE FROM NOTE WHERE STATE = ${state}";
        HashMap<String,String> map = new HashMap<>();
        map.put("state",String.valueOf(state.getValue()));
        query = StringUtil.format(query,map);
        sqliteHelper.executeReader(query, new CursorCallback() {
            @Override
            public void read(Cursor cursor) throws Exception {
                if(cursor.moveToFirst()){
                    do{
                        Note note = new Note();
                        note.setId(cursor.getLong(0));
                        note.setHeader(cursor.getString(1));
                        note.setContent(cursor.getString(2));
                        note.setState(NoteState.fromInt(cursor.getInt(3)));
                        notes.add(note);
                    }while (cursor.moveToNext());
                }

            }
        });
        return notes;
    }
}
