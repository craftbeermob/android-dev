package com.example.craftbeermob.Classes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.craftbeermob.Interfaces.IListFragmentInteractionListener;
import com.example.craftbeermob.Models.Leaderboard;
import com.example.craftbeermob.R;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link }.
 * TODO: Replace the implementation with code for your data type.
 */
public class LeaderboardRecyclerAdapter extends RecyclerView.Adapter<LeaderboardRecyclerAdapter.ViewHolder> {

    private final IListFragmentInteractionListener mListener;
    private ArrayList<Leaderboard> mLeaderboardList;

    public LeaderboardRecyclerAdapter(ArrayList<Leaderboard> items, IListFragmentInteractionListener listener) {
        mLeaderboardList = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_leaderboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mLeaderboardList.get(position);
        holder.mUsernameView.setText(holder.mItem.getUsername());
        holder.mPointsView.setText(Integer.toString(holder.mItem.getPoints()));

//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
//                }
//            }
//        });
    }


    public void addItems(List<Object> leaderboardList) {
        int prevSize = mLeaderboardList.size();

        for (Object mission : leaderboardList) {
            mLeaderboardList.add((Leaderboard) mission);
        }

        notifyItemRangeInserted(prevSize, leaderboardList.size());

    }

    @Override
    public int getItemCount() {
        if (mLeaderboardList != null) {
            return mLeaderboardList.size();
        } else {
            mLeaderboardList = new ArrayList<>();
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mUsernameView;
        public final TextView mPointsView;
        public Leaderboard mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mUsernameView = (TextView) view.findViewById(R.id.tv_leaderboard_username);
            mPointsView = (TextView) view.findViewById(R.id.tv_leaderboard_points);
        }


    }
}
