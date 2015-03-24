package com.ideafragments.todo.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Sam.I on 3/11/2015.
 */
public class DrawerFragment extends Fragment implements MyDrawerLayout.DrawerPanel {
    protected byte drawerID;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }


    @Override
    public byte getDrawerID() {
        return drawerID;
    }

    @Override
    public void setDrawerID(byte id) {
        drawerID = id;
    }

    @Override
    public float getDrawerDimension() {
        return 0;
    }

    @Override
    public float getDrawerOffset() {
        return 0;
    }

    @Override
    public View getPanelView() {
        return getView();
    }
}
