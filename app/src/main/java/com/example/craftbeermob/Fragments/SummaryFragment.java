package com.example.craftbeermob.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.craftbeermob.Classes.BaseQuery;
import com.example.craftbeermob.Classes.GetBlob;
import com.example.craftbeermob.Classes.RecentActivityRecyclerAdapter;
import com.example.craftbeermob.Interfaces.IList;
import com.example.craftbeermob.Models.RecentActivity;
import com.example.craftbeermob.R;

import java.util.ArrayList;

/**
 * Created by ret70 on 6/14/2016.
 */
public class SummaryFragment extends Fragment implements IList {

    Spinner spinner_favhideout;
    Spinner spinner_favbrewer;
    Spinner spinner_favbeertype;

    TextView tv_title;
    TextView tv_breweryinfo;
    TextView tv_date;

    RecyclerView mRecyclerView;
    RecentActivityRecyclerAdapter mRecentActivityRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);


        spinner_favhideout = (Spinner) view.findViewById(R.id.spinner_favhideout);
        spinner_favbrewer = (Spinner) view.findViewById(R.id.spinner_summary_favbrewer);
        spinner_favbeertype = (Spinner) view.findViewById(R.id.spinner_summary_favbeertype);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_summary);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecentActivityRecyclerAdapter = new RecentActivityRecyclerAdapter(null);
        mRecyclerView.setAdapter(mRecentActivityRecyclerAdapter);
        new BaseQuery<>(getContext(), RecentActivity.class).getWhere(this, "UserId", "ae4d7d37-f9e0-41ba-a316-a5ec07a63b9f");
        return view;
    }

    @Override
    public void setList(ArrayList<Object> objects) {
        GetBlob getBlob = new GetBlob(mRecentActivityRecyclerAdapter);
        getBlob.execute(objects);

    }
}
