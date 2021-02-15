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
import java.util.Locale;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bar_item_near,parent,false);
        return new SearchDistanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchDistanceViewHolder holder, int position) {
        Result result = results.get(position);
        String title = result.getTitle();
        String address = result.getAddress();
        String distance = String.format(Locale.getDefault(),"%.2f м",result.getDistance());
        if(title!=null){
            holder.textViewTitle.setText(title);
        }else {
            holder.textViewTitle.setText("");
        }

        if(address!=null){
            holder.textViewAddress.setText(address);
        }else {
            holder.textViewAddress.setText("");
        }
        if(distance!=null){
            holder.textViewDistance.setText(distance);
        }else {
            holder.textViewDistance.setText("");
        }

//        holder.textViewTitle.setText(result.getTitle());
//        holder.textViewAddress.setText(result.getAddress());
//        holder.textViewDistance.setText(String.format("%s",result.getDistance()));
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
            textViewTitle = itemView.findViewById(R.id.textViewShortTitleNear);
            textViewAddress = itemView.findViewById(R.id.textViewShortAddressNear);
            textViewDistance = itemView.findViewById(R.id.textViewShortDistanceNear);
            imageView = itemView.findViewById(R.id.imageViewShortNear);
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
