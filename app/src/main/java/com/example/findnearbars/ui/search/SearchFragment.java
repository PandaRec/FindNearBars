package com.example.findnearbars.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.PrimaryKey;

import com.example.findnearbars.R;
import com.example.findnearbars.adapters.SearchAdapter;
import com.example.findnearbars.pojo.Result;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private SearchViewModel searchViewModel;
    private SearchAdapter adapter;
    private List<Result> myResults;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myResults = new ArrayList<>();
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        searchViewModel.createViewModel(getContext());
        View root = inflater.inflate(R.layout.fragment_search,container,false);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewShortView);
        SearchView searchView = root.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //click to find smth
                Log.i("my_onQuery_submit",query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //steel typing
                Log.i("my_onQuery_change",newText);
                adapter.setResults(searchViewModel.getResultsByName(myResults,newText));

                return false;
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SearchAdapter();
        recyclerView.setAdapter(adapter);

        searchViewModel.getResults().observe(getViewLifecycleOwner(), new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                myResults.addAll(results);
            }
        });



        return root;
    }
}
