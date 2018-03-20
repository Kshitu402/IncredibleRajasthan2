package com.example.ashwani.incredibleindia;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;


import java.util.ArrayList;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{


    private ArrayList<Reviews> itemlist;
    private Context context;

    public ItemAdapter(Context context, ArrayList<Reviews> itemlist)
    {
        this.context=context;
        this.itemlist=itemlist;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item_cardviewlayout,parent,false);
        ItemViewHolder itemviewholder=new ItemViewHolder(view);


        return itemviewholder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Reviews item=itemlist.get(position);
        holder.reviewperso.setText(item.reviewperson);
        holder.reviewcountry.setText(item.reviewcountry);
        holder.rate.setRating(item.reviewrate);
        holder.restcomment.setText(item.reviewinfo);
    }

    @Override
    public int getItemCount() {
        if (itemlist != null) {
            return itemlist.size();
        }
        else {
            return 0;
        }
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView reviewperso;
        public TextView reviewcountry;
        public RatingBar rate;
        public TextView restcomment;

        public ItemViewHolder(View itemView) {
            super(itemView);

            card=(CardView)itemView.findViewById(R.id.card);
            reviewperso= (TextView) itemView.findViewById(R.id.reviewperson);
            reviewcountry= (TextView) itemView.findViewById(R.id.reviewcountry);
            rate= (RatingBar) itemView.findViewById(R.id.ratingrest);
            restcomment= (TextView) itemView.findViewById(R.id.restcomment);


        }
    }
}
