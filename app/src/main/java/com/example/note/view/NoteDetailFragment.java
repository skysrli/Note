package com.example.note.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.note.databinding.NoteDetailFragmentBinding;
import com.example.note.entity.ENote;
import com.example.note.enums.NoteState;
import com.example.note.model.Note;
import com.example.note.viewmodel.NoteDetailViewModel;
import com.example.note.R;

public class NoteDetailFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = NoteDetailFragment.class.getSimpleName();
    private NoteDetailViewModel mViewModel;
    private NoteDetailFragmentBinding binding;
    private ENote eNote;
    private NoteState selectedNoteState = NoteState.TOBEDONE;

    public static NoteDetailFragment newInstance() {
        return new NoteDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = NoteDetailFragmentBinding.inflate(inflater,container,false);
        initUI();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NoteDetailViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.getNote().observe(getViewLifecycleOwner(), new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                if(note != null){//update or delete
                    binding.btnUpdate.setVisibility(View.VISIBLE);
                    binding.btnDelete.setVisibility(View.VISIBLE);
                    binding.btnAdd.setVisibility(View.GONE);
                    fillUI(note);
                }
            }
        });

        init();
    }

    private void initUI(){
        binding.btnAdd.setOnClickListener(this::onClick);
        binding.btnUpdate.setOnClickListener(this::onClick);
        binding.btnDelete.setOnClickListener(this::onClick);

        String[] stringArray = new String[]{getString(R.string.txt_to_be_done),getString(R.string.txt_doing),getString(R.string.txt_done)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), R.layout.dropdown_menu_popup_item, stringArray);
        binding.drpdwnStateType.setAdapter(adapter);

        AdapterView.OnItemClickListener onStateTypeItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedNoteState = NoteState.fromInt(i);
            }
        };
        binding.drpdwnStateType.setOnItemClickListener(onStateTypeItemClickListener);
    }
    private void fillUI(Note note){
        binding.edtHeader.setText(note.getHeader());
        binding.edtContent.setText(note.getContent());
        binding.drpdwnStateType.setText(note.getState().getText(),false);
        selectedNoteState = note.getState();

    }

    private void init(){
        eNote = new ENote(this.getContext());

        if(getArguments() != null){
            try {
                if(NoteDetailFragmentArgs.fromBundle(getArguments()).getSelectedNoteItem() != null){
                    Note note = NoteDetailFragmentArgs.fromBundle(getArguments()).getSelectedNoteItem();
                    mViewModel.getNote().setValue(note);

                }
            }catch (Exception e){
                Log.e(TAG, "onActivityCreated: Note item argumenti alınamadı.",e);;
            }
        }

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                insertNote();
                Navigation.findNavController(view).navigateUp();
                break;
            case R.id.btn_update:
                updateNote();
                Navigation.findNavController(view).navigateUp();
                break;
            case R.id.btn_delete:
                deleteNote();
                Navigation.findNavController(view).navigateUp();
                break;
            default:
                break;
        }
    }
    private void insertNote(){
        Note note = new Note();
        note.setHeader(binding.edtHeader.getText().toString());
        note.setContent(binding.edtContent.getText().toString());
        note.setState(selectedNoteState);
        eNote.insertNote(note);
    }
    private void updateNote(){
        mViewModel.getNote().getValue().setHeader(binding.edtHeader.getText().toString());
        mViewModel.getNote().getValue().setContent(binding.edtContent.getText().toString());
        mViewModel.getNote().getValue().setState(selectedNoteState);
        eNote.updateNote(mViewModel.getNote().getValue());
    }
    private void deleteNote(){
        eNote.deleteNote(mViewModel.getNote().getValue());
    }

}