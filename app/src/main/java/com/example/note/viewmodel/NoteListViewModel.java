package com.example.note.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.note.entity.ENote;
import com.example.note.enums.NoteState;
import com.example.note.model.Note;

import java.util.ArrayList;

public class NoteListViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Note>> notes;
    private MutableLiveData<NoteState> noteState;
    private ENote eNote;

    public NoteListViewModel(){
        notes = new MutableLiveData<>();
        noteState = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<Note>> getNotes() {
        return notes;
    }

    public void setNotes(MutableLiveData<ArrayList<Note>> notes) {
        this.notes = notes;
    }

    public void getNotesFromDbByState(Context context,NoteState noteState){
        eNote = new ENote(context);
        notes.setValue(eNote.getNotesByState(noteState));
    }

    public MutableLiveData<NoteState> getNoteState() {
        return noteState;
    }

    public void setNoteState(MutableLiveData<NoteState> noteState) {
        this.noteState = noteState;
    }
}