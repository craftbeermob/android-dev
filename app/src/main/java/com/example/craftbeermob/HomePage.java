package com.example.craftbeermob;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.List;

public class HomePage extends AppCompatActivity implements IList {

    ListView misssions_ListView;
    MissionAdapter mAdapter;
    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    String[] mPlanetTitles;
    String mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //region Drawer
        mPlanetTitles = getResources().getStringArray(R.array.homePageNavStrings);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.nav_homePage);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.homepagedrawerlistitem, mPlanetTitles));
        // Set the list's click listener
        // mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        //region toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_name);
        //endregion


        mTitle="Profile";
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer image to replace 'Up' caret */
                R.string.app_name,  /* "open drawer" description for accessibility */
                R.string.app_name  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        //endregion


        //region /* setup tabs */
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
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


        /* setup listview */
        misssions_ListView = (ListView) findViewById(R.id.homepage_ListView_Missions);
        mAdapter = new MissionAdapter(this, R.layout.missionrow);
        BaseQuery db = new BaseQuery(this, Missions.class);
        db.getAll(this);
        misssions_ListView.setAdapter(mAdapter);
        /* --------------------  */


    }


    @Override
    public void setList(List<Object> objects) {
        if (mAdapter.getCount() > 0 && objects.size() > 0) {
            mAdapter.clear();
        }
        for (Object obj : objects) {
            mAdapter.add((Missions) obj);
        }

        mAdapter.notifyDataSetChanged();

    }

    @Override
    public List<Object> getList() {
        return null;
    }


    /**
     * Created by ret on 6/5/16.
     */
    public class MissionAdapter extends ArrayAdapter<Missions> {

        Context mContext;

        public MissionAdapter(Context context, int resource) {
            super(context, resource);
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            TextView missionTitle = null;
            TextView missionDescription = null;
            Missions currentItem = getItem(position);

            if (row == null) {
                LayoutInflater inflater = ((AppCompatActivity) mContext).getLayoutInflater();
                row = inflater.inflate(R.layout.missionrow, null);

            }


            missionDescription = (TextView) row.findViewById(R.id.tv_missionrow_Description);
            missionTitle = (TextView) row.findViewById(R.id.tv_missionrow_Title);
            missionTitle.setText(currentItem.getTitle());
            missionDescription.setText(currentItem.getDescription());

            row.setTag(currentItem.getId());


            return row;
        }
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        String mTitle;

        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
//        Fragment fragment = new PlanetFragment();
//        Bundle args = new Bundle();
//        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
//        fragment.setArguments(args);
//
//        // Insert the fragment by replacing any existing fragment
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.content_frame, fragment)
//                .commit();
//
//        // Highlight the selected item, update the title, and close the drawer
//        mDrawerList.setItemChecked(position, true);
//        setTitle(mPlanetTitles[position]);
//        mDrawerLayout.closeDrawer(mDrawerList);
    }
//
//    @Override
//    public void setTitle(CharSequence title) {
//        mTitle = title;
//        getActionBar().setTitle(mTitle);
//    }


}
