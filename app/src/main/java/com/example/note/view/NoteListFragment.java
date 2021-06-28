package com.example.note.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.note.adapter.NoteListAdapter;
import com.example.note.databinding.NoteListFragmentBinding;
import com.example.note.entity.ENote;
import com.example.note.enums.NoteState;
import com.example.note.model.Note;
import com.example.note.viewmodel.NoteListViewModel;
import com.example.note.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class NoteListFragment extends Fragment implements View.OnClickListener{

    private NoteListViewModel mViewModel;
    private NoteListFragmentBinding binding;
    private RecyclerView.LayoutManager layoutManager;

    public static NoteListFragment newInstance() {
        return new NoteListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = NoteListFragmentBinding.inflate(inflater,container,false);
        initUI();
        return binding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NoteListViewModel.class);
        // TODO: Use the ViewModel

        mViewModel.getNotes().observe(getViewLifecycleOwner(), new Observer<ArrayList<Note>>() {
            @Override
            public void onChanged(ArrayList<Note> notes) {
                NoteListAdapter noteListAdapter = new NoteListAdapter(NoteListFragment.this.getContext(),notes);
                binding.recyclerView.setAdapter(noteListAdapter);
            }
        });
        mViewModel.getNoteState().observe(getViewLifecycleOwner(), new Observer<NoteState>() {
            @Override
            public void onChanged(NoteState noteState) {
                mViewModel.getNotesFromDbByState(NoteListFragment.this.getContext(),noteState);
            }
        });
        mViewModel.getNoteState().setValue(NoteState.TOBEDONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabAdd:
                NavDirections action = NoteListFragmentDirections.actionNoteListFragmentToNoteDetailFragment().setSelectedNoteItem(null);
                Navigation.findNavController(view).navigate(action);
                break;
            default:
                break;
        }
    }
    private void initUI(){
        binding.recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.fabAdd.setOnClickListener(this::onClick);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewModel.getNoteState().setValue(NoteState.fromInt(tab.getPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {@link Activity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();
        mViewModel.getNotesFromDbByState(this.getContext(),mViewModel.getNoteState().getValue());
    }
}