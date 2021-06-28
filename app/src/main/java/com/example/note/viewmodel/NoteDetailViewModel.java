package com.example.note.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.note.model.Note;

public class NoteDetailViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<Note> note = new MutableLiveData();


    public MutableLiveData<Note> getNote() {
        return note;
    }

    public void setNote(MutableLiveData<Note> note) {
        this.note = note;
    }
}