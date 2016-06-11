package com.example.craftbeermob;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import java.util.List;

/**
 * Created by ret70 on 6/9/2016.
 */
public class HomePageFragment extends Fragment implements IList {
    private RecyclerView mRecyclerView;
    private MissionsRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public HomePageFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage_row, container, false);
        //region /* setup tabs */
        TabHost tabHost = (TabHost) view.findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("MissionsTab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("TestTab");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("BadgesTab");

        tab1.setIndicator("Missions");
        tab1.setContent(R.id.homepage_Missions_layout);//new Intent(this,TabActivity1.class));

        tab2.setIndicator("Test");
        tab2.setContent(R.id.homepage_Test_layout);//new Intent(this,TabActivity2.class));

        tab3.setIndicator("Badges");
        tab3.setContent(R.id.homepage_Badges_layout);//new Intent(this,TabActivity3.class));

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
        //endregion


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_homepage);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //get all listitems which gets called back to setList
        //params:context, table to query
        BaseQuery query = new BaseQuery(getContext(), Missions.class);
        query.getAll(this);
        mAdapter = new MissionsRecyclerAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    //callback to set the reyclerview
    @Override
    public void setList(List<Object> objects) {
        try {
            if (objects.size() > 0) {
                mAdapter.addItems(objects);
            }

        } catch (Exception ex) {

        }
    }

    @Override
    public List<Object> getList() {
        return null;
    }
}
