package com.example.findnearbars.ui.favourite;

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

public class FavouriteFragment extends Fragment {

    private FavouriteViewModel favouriteViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        favouriteViewModel = new ViewModelProvider(this).get(FavouriteViewModel.class);
        View root = inflater.inflate(R.layout.fragment_favourite,container,false);

        return root;
    }
}
