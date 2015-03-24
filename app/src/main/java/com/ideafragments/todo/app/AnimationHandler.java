package com.ideafragments.todo.app;

import android.os.Handler;
import android.os.Message;
import android.view.View;

/**
 * Created by Sam.I on 3/9/2015.
 */
public class AnimationHandler extends Handler {

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
//        postDelayed()
    }

    public boolean animateUI (final View view, final float offset, final MyDrawerLayout.DrawerStatus start, final MyDrawerLayout.DrawerStatus finish){


        return true;
    }


}
