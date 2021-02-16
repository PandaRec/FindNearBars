package com.example.findnearbars.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findnearbars.R;
import com.example.findnearbars.pojo.FavouriteResult;
import com.example.findnearbars.pojo.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.SearchViewHolder> {

    private List<FavouriteResult> favouriteResults;
    private FavoriteAdapter.OnClickResultListener onClickResultListener;
    public FavoriteAdapter(){
        favouriteResults = new ArrayList<>();
    }

    public List<FavouriteResult> getResults() {
        return favouriteResults;
    }

    public void setOnClickResultListener(FavoriteAdapter.OnClickResultListener onClickResultListener) {
        this.onClickResultListener = onClickResultListener;
    }

    public void setResults(List<FavouriteResult> results) {
        this.favouriteResults = results;
        notifyDataSetChanged();
    }
    public interface OnClickResultListener{
        void onClickResult(int position);
    }

    @NonNull
    @Override
    public FavoriteAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_item,parent,false);
        return new FavoriteAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.SearchViewHolder holder, int position) {
        FavouriteResult result = favouriteResults.get(position);
        holder.textViewTitle.setText(result.getTitle());
        holder.textViewAddress.setText(result.getAddress());
        holder.textViewCount.setText(String.format("%s",result.getFavoritesCount()));
        Picasso.get().load(result.getImages().get(0).getImage()).error(R.drawable.ic_no_image_3).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return favouriteResults.size();
    }

    public  class SearchViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewAddress;
        private TextView textViewCount;
        private ImageView imageView;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitleFavourite);
            textViewAddress = itemView.findViewById(R.id.textViewAddressFavourite);
            imageView = itemView.findViewById(R.id.imageViewFavorite);
            textViewCount = itemView.findViewById(R.id.textViewCountOfFavorite);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickResultListener!=null){
                        onClickResultListener.onClickResult(getAdapterPosition());
                    }
                }
            });
        }
    }
}
