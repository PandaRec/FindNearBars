package com.example.findnearbars.ui.near;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findnearbars.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NearSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NearSearchFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_near_search, container, false);
    }
}