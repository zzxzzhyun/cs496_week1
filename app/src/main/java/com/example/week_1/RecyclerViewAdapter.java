package com.example.week_1;

import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.test.core.app.ActivityScenario.launch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context mContext ;
    private List<Restaurant> mData;
    private List<Long> location;

    public RecyclerViewAdapter(Context mContext, List<Restaurant> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.img_book_thumbnail.setImageResource(mData.get(position).getPic()[0]);
        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FoodActivity.class);
                intent.putExtra("name", mData.get(position).getName());
                intent.putExtra("category", mData.get(position).getCategory());
                intent.putExtra("location", mData.get(position).getLocation());
                intent.putExtra("lat", mData.get(position).getLat());
                intent.putExtra("lon", mData.get(position).getLon());
                intent.putExtra("pic1", mData.get(position).getPic()[0]);
                ((Activity)mContext).finish();
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_book_title;
        ImageView img_book_thumbnail;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

//            tv_book_title = (TextView) itemView.findViewById(R.id.restaurant_id);
            img_book_thumbnail = (ImageView) itemView.findViewById(R.id.food_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }
}
