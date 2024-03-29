package com.example.craftbeermob;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

/**
 * Created by ret70 on 6/9/2016.
 */
public class BaseActivity extends AppCompatActivity {


    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    String[] mDrawerStrings;
    CharSequence mTitle;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    public FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Add the Very First i.e Squad Fragment to the Container


    }


    @Override
    public void setContentView(int layoutResID) {
        //region Drawer
        mDrawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_home_page, null);
        FrameLayout activityContainer = (FrameLayout) mDrawerLayout.findViewById(R.id.content_frame);
        getLayoutInflater().inflate(layoutResID, activityContainer, false);
        mDrawerStrings = getResources().getStringArray(R.array.homePageNavStrings);
        mDrawerList = (ListView) mDrawerLayout.findViewById(R.id.nav_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.homepagedrawerlistitem, mDrawerStrings));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        //region toolbar
        toolbar = (Toolbar) mDrawerLayout.findViewById(R.id.toolBar);
        if (useToolbar()) {
            setSupportActionBar(toolbar);
            setTitle(getTitle());
            toolbar.setNavigationIcon(R.drawable.ic_action_name);
        } else {
            toolbar.setVisibility(View.GONE);
        }

        //endregion

        mTitle = getString(R.string.app_name);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer image to replace 'Up' caret */
                R.string.app_name,  /* "open drawer" description for accessibility */
                R.string.app_name  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        //endregion

        super.setContentView(mDrawerLayout);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    protected boolean useToolbar() {
        return true;
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {


        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);

        }
    }


    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(int position) {
        FragmentManager manager;
        FragmentTransaction transaction;
        switch (position) {
            case 0:
                manager = getSupportFragmentManager();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.content_frame, new HomePageFragment()).commit();
                break;
            case 1:
                break;
            case 2:
                manager = getSupportFragmentManager();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.content_frame, new LeaderboardFragment()).commit();
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mDrawerStrings[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }


    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

}
