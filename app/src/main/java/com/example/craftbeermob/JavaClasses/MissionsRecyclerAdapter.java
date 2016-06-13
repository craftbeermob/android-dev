package com.example.craftbeermob.JavaClasses;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.craftbeermob.Models.Missions;
import com.example.craftbeermob.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ret70 on 6/9/2016.
 */
public class MissionsRecyclerAdapter extends RecyclerView.Adapter<MissionsRecyclerAdapter.ViewHolder> {

    ArrayList<Missions> mMissionTitles;
    public MissionsRecyclerAdapter( ArrayList<Missions> missionTitles)
    {


    }
    @Override
    public MissionsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.missionrow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MissionsRecyclerAdapter.ViewHolder holder, int position) {

        holder.tv_mission.setText(mMissionTitles.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        if(mMissionTitles != null) {
            return mMissionTitles.size();
        }else
        {
            mMissionTitles=new ArrayList<>();
          return 0;
        }
    }

    public void addItems(List<Object> missionsList)
    {
        int prevSize=mMissionTitles.size();

        for (Object mission:missionsList) {
           mMissionTitles.add((Missions)mission);
        }

        notifyItemRangeInserted(prevSize,missionsList.size());

    }


    public  class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv_mission;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_mission=(TextView)itemView.findViewById(R.id.tv_missionrow_Title);
        }
    }
}
