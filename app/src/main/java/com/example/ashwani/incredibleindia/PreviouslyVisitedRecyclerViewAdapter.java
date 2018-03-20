package com.example.ashwani.incredibleindia;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ashwani on 6/10/17.
 */

public class PreviouslyVisitedRecyclerViewAdapter extends RecyclerView.Adapter<PreviouslyVisitedRecyclerViewAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.previously_visited_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public ImageView previouslyVisitedImage;
        public TextView previouslyVisitedImageTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            previouslyVisitedImage = (ImageView)itemView.findViewById(R.id.previouslyVisitedCardImage);
            previouslyVisitedImageTitle = (TextView)itemView.findViewById(R.id.previouslyVisitedCardImageTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                }
            });
        }
    }

}
