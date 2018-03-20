package com.example.ashwani.incredibleindia;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by ashwani on 29/9/17.
 */

public class TourCardAdapter extends RecyclerView.Adapter<TourCardAdapter.MyViewHolder> {

    private Context ctx;
    private List<TourCard> list;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tourCardImageName,tourCardImageCity;
        public ImageView tourCardImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            tourCardImage = (ImageView) itemView.findViewById(R.id.tourCardImage);
            tourCardImageCity = (TextView) itemView.findViewById(R.id.tourCardImageCity);
            tourCardImageName = (TextView) itemView.findViewById(R.id.tourCardImageName);
            tourCardImage.setOnClickListener(this);
            tourCardImageCity.setOnClickListener(this);
            tourCardImageName.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ctx.startActivity(new Intent(ctx,TouristPlace.class));
        }
    }

    public TourCardAdapter(Context ctx,List<TourCard> list){
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tour_card,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TourCard tourCard = list.get(position);
        holder.tourCardImageName.setText(tourCard.getName());
        holder.tourCardImageCity.setText(tourCard.getCity());

        Glide.with(ctx).load(tourCard.getThumbnail()).into(holder.tourCardImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
