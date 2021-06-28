package com.example.note.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.note.enums.NoteState;

public class Note implements Parcelable {

    private long id;
    private String header;
    private String content;
    private NoteState state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public NoteState getState() {
        return state;
    }

    public void setState(NoteState state) {
        this.state = state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
