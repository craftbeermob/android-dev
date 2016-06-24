package com.example.craftbeermob.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.craftbeermob.Activities.App;
import com.example.craftbeermob.Activities.SummaryActivity;
import com.example.craftbeermob.Classes.BaseQuery;
import com.example.craftbeermob.Classes.RecentActivityRecyclerAdapter;
import com.example.craftbeermob.Interfaces.IList;
import com.example.craftbeermob.Models.RecentActivity;
import com.example.craftbeermob.Models.Users;
import com.example.craftbeermob.R;

import java.util.ArrayList;

/**
 * Created by ret70 on 6/14/2016.
 */
public class SummaryFragment extends Fragment implements IList {


    RecyclerView mRecyclerView;
    RecentActivityRecyclerAdapter mRecentActivityRecyclerAdapter;
    ImageButton profileImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        profileImage = (ImageButton) view.findViewById(R.id.iv_summary_userimage);
        Users user = App.GetUserSingleton();
        try {
            if (user != null && user.getProfilePictureUri() != null) {
                Glide.with(view.getContext())
                        .load(user.getProfilePictureUri())
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(profileImage);
            }
        } catch (Exception ex) {
            Log.d("ex", ex.getMessage());
        }


        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                getActivity().startActivityForResult(cameraIntent, SummaryActivity.CAPTURE_PROFILE_IMAGE);
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_summary);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecentActivityRecyclerAdapter = new RecentActivityRecyclerAdapter(view.getContext(), null);
        mRecyclerView.setAdapter(mRecentActivityRecyclerAdapter);
        new BaseQuery<>(getContext(), RecentActivity.class).getWhere(this, "UserId", App.GetUserSingleton().getUserId());
        return view;
    }

    @Override
    public void setList(ArrayList<Object> objects) {
        if (objects != null && objects.size() > 0) {
            mRecentActivityRecyclerAdapter.addItems(objects);
        }

    }


}
