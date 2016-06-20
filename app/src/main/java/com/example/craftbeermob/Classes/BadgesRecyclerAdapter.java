package com.example.craftbeermob.Classes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.craftbeermob.Interfaces.IObject;
import com.example.craftbeermob.Models.Badges;
import com.example.craftbeermob.R;

import java.util.ArrayList;

/**
 * Created by ret70 on 6/19/2016.
 */
public class BadgesRecyclerAdapter extends RecyclerView.Adapter<BadgesRecyclerAdapter.ViewHolder> {

    ArrayList<IObject> mBadgesArrayList;

    public BadgesRecyclerAdapter(ArrayList<IObject> mIObjectArrayList) {
        this.mBadgesArrayList = mIObjectArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.badgesrow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mBadgesArrayList != null && mBadgesArrayList.size() > 0) {
            Glide.with(holder.badgeItem.getContext())
                    .load(((Badges) mBadgesArrayList.get(position))
                            .getBadgeUri())
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.badgeItem);
        }
    }


    public void addItem(ArrayList<Object> objects) {
        int prevSize = mBadgesArrayList.size();
        for (Object badge : objects) {
            mBadgesArrayList.add((Badges) badge);
        }
        notifyItemRangeInserted(prevSize, objects.size());
    }

    @Override
    public int getItemCount() {
        if (mBadgesArrayList != null) {
            return mBadgesArrayList.size();
        } else {
            mBadgesArrayList = new ArrayList<>();
            return 0;
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton badgeItem;

        public ViewHolder(View itemView) {
            super(itemView);
            badgeItem = (ImageButton) itemView.findViewById(R.id.badgesrow_imagebutton);
        }
    }
}
