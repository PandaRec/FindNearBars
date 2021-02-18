package com.example.findnearbars.ui.favourite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findnearbars.DetailsActivity;
import com.example.findnearbars.R;
import com.example.findnearbars.adapters.FavoriteAdapter;
import com.example.findnearbars.pojo.FavouriteResult;
import com.google.gson.Gson;

import java.util.List;

public class FavouriteFragment extends Fragment {

    private FavouriteViewModel favouriteViewModel;
    private FavoriteAdapter adapter;

    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        favouriteViewModel = new ViewModelProvider(this).get(FavouriteViewModel.class);
        favouriteViewModel.createFavouriteViewModel(getContext());
        View root = inflater.inflate(R.layout.fragment_favourite,container,false);
        adapter = new FavoriteAdapter();

        recyclerView = root.findViewById(R.id.recycleViewFavorite);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));

        favouriteViewModel.getFavouriteResults().observe(getViewLifecycleOwner(), new Observer<List<FavouriteResult>>() {
            @Override
            public void onChanged(List<FavouriteResult> favouriteResults) {
                adapter.setResults(favouriteResults);
            }
        });

        adapter.setOnClickResultListener(new FavoriteAdapter.OnClickResultListener() {
            @Override
            public void onClickResult(int position) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("result",new Gson().toJson(adapter.getResults().get(position)));
                startActivity(intent);
            }
        });





        return root;
    }
}
