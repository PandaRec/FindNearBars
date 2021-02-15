package com.example.findnearbars.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findnearbars.R;
import com.example.findnearbars.pojo.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchDistanceAdapter extends RecyclerView.Adapter<SearchDistanceAdapter.SearchDistanceViewHolder> {
    private List<Result> results;
    private OnClickResultListener onClickResultListener;
    public SearchDistanceAdapter(){
        results = new ArrayList<>();
    }

    public List<Result> getResults() {
        return results;
    }

    public void setOnClickResultListener(OnClickResultListener onClickResultListener) {
        this.onClickResultListener = onClickResultListener;
    }

    public void setResults(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }
    public interface OnClickResultListener{
        void onClickResult(int position);
    }

    @NonNull
    @Override
    public SearchDistanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bar_item,parent,false);
        return new SearchDistanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchDistanceViewHolder holder, int position) {
        Result result = results.get(position);
        holder.textViewTitle.setText(result.getTitle());
        holder.textViewAddress.setText(result.getAddress());
        Picasso.get().load(result.getImages().get(0).getImage()).error(R.drawable.ic_no_image_3).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public  class SearchDistanceViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewAddress;
        private TextView textViewDistance;
        private ImageView imageView;
        public SearchDistanceViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewShortTitle);
            textViewAddress = itemView.findViewById(R.id.textViewShortAddress);
            textViewAddress = itemView.findViewById(R.id.textViewShortDistance);
            imageView = itemView.findViewById(R.id.imageViewShort);
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
