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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<Result> results;
    private OnClickResultListener onClickResultListener;
    public SearchAdapter(){
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
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bar_item,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Result result = results.get(position);
        holder.textViewTitle.setText(result.getTitle());
        holder.textViewAddress.setText(result.getAddress());
        Picasso.get().load(result.getImages().get(0).getImage()).error(R.drawable.ic_no_image).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public  class SearchViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewAddress;
        private ImageView imageView;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewShortTitle);
            textViewAddress = itemView.findViewById(R.id.textViewShortAddress);
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
