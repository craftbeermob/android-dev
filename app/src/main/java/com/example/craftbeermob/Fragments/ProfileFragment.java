package com.example.craftbeermob.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.example.craftbeermob.Classes.BadgesRecyclerAdapter;
import com.example.craftbeermob.Classes.BaseQuery;
import com.example.craftbeermob.Classes.MissionsRecyclerAdapter;
import com.example.craftbeermob.Interfaces.IList;
import com.example.craftbeermob.Models.Badges;
import com.example.craftbeermob.Models.Missions;
import com.example.craftbeermob.R;

import java.util.ArrayList;

/**
 * Created by ret70 on 6/9/2016.
 */
public class ProfileFragment extends Fragment implements IList {
    RecyclerView mRecyclerViewBadges;
    private RecyclerView mRecyclerViewMissions;
    private MissionsRecyclerAdapter mMissionAdapter;
    private BadgesRecyclerAdapter mBadgesAdapter;

    public ProfileFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //region /* setup tabs */
        TabHost tabHost = (TabHost) view.findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec(TabHeader.Missions.name());
        TabHost.TabSpec tab2 = tabHost.newTabSpec(TabHeader.Test.name());
        TabHost.TabSpec tab3 = tabHost.newTabSpec(TabHeader.Badges.name());

        tab1.setIndicator(TabHeader.Missions.name());
        tab1.setContent(R.id.homepage_Missions_layout);//new Intent(this,TabActivity1.class));

        tab2.setIndicator(TabHeader.Test.name());
        tab2.setContent(R.id.homepage_Test_layout);//new Intent(this,TabActivity2.class));

        tab3.setIndicator(TabHeader.Badges.name());
        tab3.setContent(R.id.homepage_Badges_layout);//new Intent(this,TabActivity3.class));

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);

        //region Setup Badges Adapter
        mRecyclerViewBadges = (RecyclerView) view.findViewById(R.id.recyclerview_profile_badges);
        mRecyclerViewBadges.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mBadgesAdapter = new BadgesRecyclerAdapter(null);
        mRecyclerViewBadges.setAdapter(mBadgesAdapter);
        //endregion

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            public void onTabChanged(String tabId) {
                if (tabId == TabHeader.Missions.name()) {

                } else if (tabId == TabHeader.Test.name()) {

                } else if (tabId == TabHeader.Badges.name()) {

                    new BaseQuery<>(getContext(), Badges.class).getWhere(ProfileFragment.this, "UserId", "1234");

                }

            }
        });
        //endregion


        mRecyclerViewMissions = (RecyclerView) view.findViewById(R.id.recyclerview_profile_missions);
        mRecyclerViewMissions.setLayoutManager(new LinearLayoutManager(getContext()));
        //get all listitems which gets called back to setList
        //params:context, table to query
        new BaseQuery(getContext(), Missions.class).getAll(this);
        mMissionAdapter = new MissionsRecyclerAdapter(null);
        mRecyclerViewMissions.setAdapter(mMissionAdapter);
        return view;
    }

    //callback to set the reyclerview
    @Override
    public void setList(ArrayList<Object> objects) {
        if (objects != null && objects.size() > 0) {
            try {
                if (objects.get(0) instanceof Missions) {
                    if (objects.size() > 0) {
                        mMissionAdapter.addItems(objects);
                    }
                } else if (objects.get(0) instanceof Badges) {
                    mBadgesAdapter.addItem(objects);
                }
            } catch (Exception ex) {

            }
        }
    }

    private enum TabHeader {
        Missions,
        Test,
        Badges
    }

}
