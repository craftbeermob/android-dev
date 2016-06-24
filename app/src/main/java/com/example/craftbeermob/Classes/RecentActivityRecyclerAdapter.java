package com.example.craftbeermob.Classes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.craftbeermob.Models.RecentActivity;
import com.example.craftbeermob.R;

import java.util.ArrayList;

/**
 * Created by ret70 on 6/14/2016.
 */
public class RecentActivityRecyclerAdapter extends RecyclerView.Adapter<RecentActivityRecyclerAdapter.ViewHolder> {

    ArrayList<RecentActivity> mActivityArrayList;
    Context mContext;

    public RecentActivityRecyclerAdapter(Context context, ArrayList<RecentActivity> activityArrayList) {
        mActivityArrayList = activityArrayList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recentactivityrow, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.beerinfo.setText(mActivityArrayList.get(position).getBrewery_Info());
        holder.date.setText(mActivityArrayList.get(position).getCreatedAt().toString());
        holder.title.setText(mActivityArrayList.get(position).getComment());
        Glide.with(mContext).load(mActivityArrayList.get(position)
                .getPhotoURI()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.beerPhoto);

    }

    public void addItems(ArrayList<Object> recentActivityArrayList) {
        //TODO:Change for pagination
        int prevSize = mActivityArrayList.size();
        for (Object recentImage : recentActivityArrayList) {
            mActivityArrayList.add((RecentActivity) recentImage);
        }
        notifyItemRangeInserted(prevSize, recentActivityArrayList.size());
    }

    @Override
    public int getItemCount() {
        if (mActivityArrayList != null) {
            return mActivityArrayList.size();
        } else {
            mActivityArrayList = new ArrayList<>();
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView beerinfo;
        TextView date;
        ImageView beerPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_summary_comment);
            beerinfo = (TextView) itemView.findViewById(R.id.tv_summary_breweryinfo);
            date = (TextView) itemView.findViewById(R.id.tv_summary_date);
            beerPhoto = (ImageView) itemView.findViewById(R.id.iv_summary_recentImage);
        }
    }
}
