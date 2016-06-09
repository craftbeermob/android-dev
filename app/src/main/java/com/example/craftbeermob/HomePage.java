package com.example.craftbeermob;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.List;

public class HomePage extends BaseActivity implements IListFragmentInteractionListener, IList {

    ListView misssions_ListView;
    private RecyclerView mRecyclerView;
    private MissionsRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

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


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_homepage);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //get all listitems which gets called back to setList
        //params:context, table to query
        BaseQuery query = new BaseQuery(this, Missions.class);
        query.getAll(this);
        mAdapter = new MissionsRecyclerAdapter(null);
        mRecyclerView.setAdapter(mAdapter);


    }


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
    public void onListFragmentInteraction(Object item) {

    }

    @Override
    public List<Object> getList() {
        return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_home, menu);

        return super.onCreateOptionsMenu(menu);
    }


}
