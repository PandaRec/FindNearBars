package com.example.findnearbars.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findnearbars.R;
import com.example.findnearbars.pojo.Image;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailImagesAdapter extends RecyclerView.Adapter<DetailImagesAdapter.DetailImagesViewHolder> {
    List<Image> results;

    public void setResults(List<Image> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public DetailImagesAdapter() {
        results = new ArrayList<>();
    }

    @NonNull
    @Override
    public DetailImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_images_item,parent,false);

        return new DetailImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailImagesViewHolder holder, int position) {
        Image result = results.get(position);
            Picasso.get().load(result.getImage()).into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class DetailImagesViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public DetailImagesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
