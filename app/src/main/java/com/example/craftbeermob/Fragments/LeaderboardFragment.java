package com.example.craftbeermob.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.example.craftbeermob.Classes.BaseQuery;
import com.example.craftbeermob.Classes.LeaderboardRecyclerAdapter;
import com.example.craftbeermob.Interfaces.IList;
import com.example.craftbeermob.Interfaces.IListFragmentInteractionListener;
import com.example.craftbeermob.Models.Leaderboard;
import com.example.craftbeermob.R;

import java.util.ArrayList;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link IListFragmentInteractionListener}
 * interface.
 */
public class LeaderboardFragment extends Fragment implements IList {

    LeaderboardRecyclerAdapter mLeaderboardRecyclerAdapter;
    private IListFragmentInteractionListener mListener;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LeaderboardFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard_list, container, false);

        //region /* setup tabs */
        TabHost tabHost = (TabHost) view.findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec(TabHeader.All.name());
        TabHost.TabSpec tab2 = tabHost.newTabSpec(TabHeader.Recent.name());
        TabHost.TabSpec tab3 = tabHost.newTabSpec(TabHeader.Nearby.name());

        tab1.setIndicator(TabHeader.All.name());
        tab1.setContent(R.id.leaderboard_all_layout);//new Intent(this,TabActivity1.class));

        tab2.setIndicator(TabHeader.Recent.name());
        tab2.setContent(R.id.leadboard_Recent_layout);//new Intent(this,TabActivity2.class));

        tab3.setIndicator(TabHeader.Nearby.name());
        tab3.setContent(R.id.leaderboard_nearby_layout);//new Intent(this,TabActivity3.class));

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);

        // Set the adapter
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.leaderboard_all_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));


//        Leaderboard leaderboard1 = new Leaderboard();
//        leaderboard1.setUsername("Ron");
//        leaderboard1.setBadges("None");
//        leaderboard1.setPoints(300);
//
//        Leaderboard leaderboard2 = new Leaderboard();
//        leaderboard1.setUsername("Jace");
//        leaderboard1.setBadges("None");
//        leaderboard1.setPoints(500);
//
//        ArrayList<IObject> leaderboards= new ArrayList<>();
//        leaderboards.add(leaderboard1);
//        leaderboards.add(leaderboard2);
//        new BaseQuery<>(getContext(),Leaderboard.class).addAll(leaderboards);

        new BaseQuery<>(getContext(), Leaderboard.class).getAll(this);
        mLeaderboardRecyclerAdapter = new LeaderboardRecyclerAdapter(null, mListener);
        recyclerView.setAdapter(mLeaderboardRecyclerAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IListFragmentInteractionListener) {
            mListener = (IListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setList(ArrayList<Object> objects) {
        mLeaderboardRecyclerAdapter.addItems(objects);
    }


    private enum TabHeader {
        All,
        Recent,
        Nearby
    }
}
