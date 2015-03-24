package com.ideafragments.todo.app;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

/**
 * Created by Sam.I on 3/11/2015.
 */
public class MyDrawerContainerLayout extends RelativeLayout {

    private int mTouchSlop;
    private boolean isDragging = false;
    private boolean hasStartedX = false;
    private boolean hasStartedY = false;
    private float startLocationX;
    private float startLocationY;
    private boolean shouldIntercept = true;
    private MyDrawerLayout drawerLayout;


    public MyDrawerContainerLayout(Context context) {
        super(context);
    }

    public MyDrawerContainerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyDrawerContainerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyDrawerContainerLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setDrawerLayout(MyDrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    public void setShouldIntercept(boolean shouldIntercept) {
        this.shouldIntercept = shouldIntercept;
    }

    public boolean getShouldIntercept() {
        return shouldIntercept;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!shouldIntercept) {
            return super.onInterceptTouchEvent(ev);
        }

        ViewConfiguration vc = ViewConfiguration.get(getContext());
        mTouchSlop = vc.getScaledTouchSlop();
        final int action = MotionEventCompat.getActionMasked(ev);

        if(action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            isDragging = false;
            hasStartedX = false;
            hasStartedY = false;
//            Log.i("SAM" , "isNOTDragging in X");
            return false;
        }

        switch (action) {

            case MotionEvent.ACTION_DOWN:
//                Log.i("SAM", "MyDrawerContainerLayout.interceptTouch() ACTIONDOWN action = " + action);
                break;

            case MotionEvent.ACTION_MOVE: {
                if (isDragging) {
                    return true;
                }

                final int touchDistanceX = calculateDistanceX(ev);
                final int touchDistanceY = calculateDistanceY(ev);
                if (touchDistanceX > mTouchSlop*2) {
                    isDragging = true;
//                    Log.i("SAM", "MyDrawerContainerLayout.interceptTouch() ACTIONMOVE action = " + action);
//                    Log.i("SAM" , "isDragging in X " + touchDistanceX);
                    return true;
                } else
                if (touchDistanceY > mTouchSlop*2 && !isDragging) {
                    startLocationY = 0;
                    startLocationX = 0;
                    return false;
                }

            }
            case MotionEvent.ACTION_SCROLL: {
//                Log.i("SAM", "Scrolling");
                break;
            }

        }

        return false;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
//        Log.i("SAM", "MyDrawerContainerLayout.onTouchEvent() action = " + event.getActionMasked());
        drawerLayout.startUserDrag(event);
        if(isDragging && (event.getActionMasked() == MotionEvent.ACTION_CANCEL || event.getActionMasked() == MotionEvent.ACTION_UP)){
            isDragging = false;
            hasStartedY = false;
            hasStartedX = false;
            startLocationX = 0;
            startLocationY = 0;
            return true;
        }
        return super.onTouchEvent(event);
    }

    private int calculateDistanceX(MotionEvent event) {
        if (!hasStartedX) {
            startLocationX = event.getRawX();
            hasStartedX = true;
        }
//        Log.i("SAM", "MyRelativeLayout.calculateDistanceX: " + Math.abs((int)(event.getRawX() - startLocationX)) + " " + mTouchSlop);
//        Log.i("SAM", "MyRelativeLayout.calculateDistanceX: " + event.getRawX() + " " + startLocationX);
        return Math.abs((int)(event.getRawX() - startLocationX));
    }
    private int calculateDistanceY(MotionEvent event) {
        if (!hasStartedY) {
            startLocationY = event.getRawY();
            hasStartedY = true;
        }
//        Log.i("SAM", "MyRelativeLayout.calculateDistanceY: " + Math.abs((int) (event.getRawY() - startLocationY)) + " " + mTouchSlop);
        return Math.abs((int) (event.getRawY() - startLocationY));
    }


}
