package com.example.craftbeermob.Classes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.craftbeermob.R;

import java.util.ArrayList;

/**
 * Created by ret70 on 6/14/2016.
 */
public class RecentActivityRecyclerAdapter extends RecyclerView.Adapter<RecentActivityRecyclerAdapter.ViewHolder> {

    ArrayList<GetBlob.RecentImageAndPhoto> mActivityArrayList;

    public RecentActivityRecyclerAdapter(ArrayList<GetBlob.RecentImageAndPhoto> activityArrayList) {
        mActivityArrayList = activityArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recentactivityrow, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.beerinfo.setText(mActivityArrayList.get(position).getRecentActivity().getBrewery_Info());
        holder.date.setText(mActivityArrayList.get(position).getRecentActivity().getCreatedAt().toString());
        holder.title.setText(mActivityArrayList.get(position).getRecentActivity().getTitle());
        holder.beerPhoto.setImageBitmap(mActivityArrayList.get(position).getBitmap());
    }

    public void addItems(ArrayList<GetBlob.RecentImageAndPhoto> recentActivityArrayList) {
        //TODO:Change for pagination
        int prevSize = mActivityArrayList.size();
        for (GetBlob.RecentImageAndPhoto recentActivityAndImage : recentActivityArrayList) {
            mActivityArrayList.add(recentActivityAndImage);
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
            title = (TextView) itemView.findViewById(R.id.tv_summary_beertitle);
            beerinfo = (TextView) itemView.findViewById(R.id.tv_summary_breweryinfo);
            date = (TextView) itemView.findViewById(R.id.tv_summary_date);
            beerPhoto = (ImageView) itemView.findViewById(R.id.iv_summary_recentImage);
        }
    }
}
