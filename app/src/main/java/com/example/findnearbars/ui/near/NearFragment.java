package com.example.findnearbars.ui.near;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.findnearbars.R;
import com.example.findnearbars.ui.search.SearchViewModel;

public class NearFragment extends Fragment {

    private NearViewModel nearViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        nearViewModel = new ViewModelProvider(this).get(NearViewModel.class);
        View root = inflater.inflate(R.layout.fragment_near,container,false);
        TextView textViewNear = root.findViewById(R.id.textViewNear);
        nearViewModel.getmText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textViewNear.setText(s);
            }
        });
        return root;
    }
}
