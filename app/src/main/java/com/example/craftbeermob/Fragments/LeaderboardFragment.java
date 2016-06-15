package com.example.craftbeermob.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.craftbeermob.Classes.MyLeaderboardRecyclerAdapter;
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
public class LeaderboardFragment extends Fragment {


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

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));


            Leaderboard leaderboard = new Leaderboard();
            leaderboard.setBadges("None");
            leaderboard.setPoints(50);
            leaderboard.setUsername("Ret");

            Leaderboard leaderboard2 = new Leaderboard();
            leaderboard2.setBadges("None");
            leaderboard2.setPoints(100);
            leaderboard2.setUsername("Sylvain");

            Leaderboard leaderboard3 = new Leaderboard();
            leaderboard3.setBadges("None");
            leaderboard3.setPoints(150);
            leaderboard3.setUsername("Jace");

            Leaderboard leaderboard4 = new Leaderboard();
            leaderboard4.setBadges("None");
            leaderboard4.setPoints(200);
            leaderboard4.setUsername("Rob");

            Leaderboard leaderboard5 = new Leaderboard();
            leaderboard5.setBadges("None");
            leaderboard5.setPoints(250);
            leaderboard5.setUsername("Ron");

            ArrayList<Leaderboard> leaderboardList = new ArrayList<>();

            leaderboardList.add(leaderboard);
            leaderboardList.add(leaderboard2);
            leaderboardList.add(leaderboard3);
            leaderboardList.add(leaderboard4);
            leaderboardList.add(leaderboard5);

            recyclerView.setAdapter(new MyLeaderboardRecyclerAdapter(leaderboardList, mListener));
        }
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


}
