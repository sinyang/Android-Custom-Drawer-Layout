package com.ideafragments.todo.app;

import android.content.Context;
import android.view.View;

/**
 * Created by Sam.I on 3/10/2015.
 */
public class MyParallaxDrawerLayout extends MyDrawerLayout {

    private float centerViewDX;

    public MyParallaxDrawerLayout(final View centerView, DrawerFragment leftPanel, Context context, final View leftDrawerShadowView) {
        super(centerView, leftPanel, context);
        this.centerView = centerView;
        this.leftPanel = leftPanel;
        this.context = context;

        centerViewDX = context.getResources().getDimension(R.dimen.left_drawer_width);

        setDrawerListener(new MyDrawerLayout.MyDrawerListener() {
            @Override
            public void onDrawerSlide(View view, float percent) {
                float offset = percent * centerViewDX;
                centerView.setTranslationX(offset);
                leftDrawerShadowView.setTranslationX(offset);
            }

            @Override
            public void onDrawerOpen(View view) {

            }

            @Override
            public void onDrawerClose(View view) {

            }
        });
    }

    public MyParallaxDrawerLayout(final View centerView, DrawerFragment leftPanel, DrawerFragment rightPanel, Context context, final View leftDrawerShadowView) {
        super(centerView, leftPanel, context);
        this.centerView = centerView;
        this.leftPanel = leftPanel;
        this.rightPanel = rightPanel;
        this.context = context;

        centerViewDX = context.getResources().getDimension(R.dimen.left_drawer_width);

        setDrawerListener(new MyDrawerLayout.MyDrawerListener() {
            @Override
            public void onDrawerSlide(View view, float percent) {
                float offset = percent * centerViewDX;
                centerView.setTranslationX(offset);
                leftDrawerShadowView.setTranslationX(offset);
            }

            @Override
            public void onDrawerOpen(View view) {

            }

            @Override
            public void onDrawerClose(View view) {

            }
        });
    }




}
