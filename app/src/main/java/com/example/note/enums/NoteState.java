package com.example.note.enums;

import com.example.note.R;
import com.example.note.util.StringUtil;

public enum NoteState {

    TOBEDONE(0, StringUtil.getString(R.string.txt_to_be_done)),
    DOING(1, StringUtil.getString(R.string.txt_doing)),
    DONE(2, StringUtil.getString(R.string.txt_done));

    private final int value;
    private final String  text;

    NoteState(int value, String text){
        this.value = value;
        this.text = text;
    }
    public int getValue() {
        return value;
    }
    public static NoteState fromInt(int value){
        switch (value){
            case 0:
                return TOBEDONE;
            case 1:
                return DOING;
            case 2:
                return DONE;
        }
        return null;
    }

    public String getText() {
        return text;
    }
}
