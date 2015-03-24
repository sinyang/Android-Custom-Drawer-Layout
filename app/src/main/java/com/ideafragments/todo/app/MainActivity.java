package com.ideafragments.todo.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class MainActivity extends ActionBarActivity {

    Toolbar toolbar;
    FragmentManager manager;
    ScheduleListFragment scheduleListFragment;
    RelativeLayout leftDrawerButton;
    ImageView rightDrawerButton;
    MyParallaxDrawerLayout myDrawerLayout;
    LeftDrawerFragment leftDrawerFragment;
    MyDrawerContainerLayout activityContainer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(20);

        manager = getSupportFragmentManager();
        scheduleListFragment = new ScheduleListFragment();
        changeFragment(scheduleListFragment);

        leftDrawerFragment = (LeftDrawerFragment) manager.findFragmentById(R.id.leftPanel);
        myDrawerLayout = new MyParallaxDrawerLayout(findViewById(R.id.centerViewContainer),leftDrawerFragment, this, findViewById(R.id.left_drawer_shadow));

        rightDrawerButton = (ImageView) toolbar.findViewById(R.id.rightDrawerButton);
        leftDrawerButton = (RelativeLayout) toolbar.findViewById(R.id.leftDrawerButton);

        activityContainer = (MyDrawerContainerLayout) findViewById(R.id.activity_container);
        activityContainer.setDrawerLayout(myDrawerLayout);
        
        leftDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDrawerLayout.activateDrawer(leftDrawerFragment.getDrawerID());
            }
        });



    }





    public void changeFragment(Fragment fragment) {
        manager.beginTransaction().replace(R.id.centerView, fragment).addToBackStack(null).commit();
    }



}
