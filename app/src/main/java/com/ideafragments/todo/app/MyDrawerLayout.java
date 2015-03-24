package com.ideafragments.todo.app;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Sam.I on 3/9/2015.
 */
public class MyDrawerLayout {
    private byte drawerCount = 0;
    private boolean hasLeft = false;
    private boolean hasRight = false;

    //set default to 1, since a drawer fragment is required for this class
    private byte activeDrawerFragmentID = -1;

    protected View centerView;
    protected MyDrawerLayout.DrawerPanel leftPanel;
    protected MyDrawerLayout.DrawerPanel rightPanel;
    protected Context context;
    protected Timer timer;
    protected MainActivity activity;
    protected float dx;
    static final byte ANIMATION_DURATION = 80;
    float slideInterval;
    protected MyDrawerLayout.MyDrawerListener drawerListener;

    private int startPeekLocation;
    private boolean isPeeking = false;
    private static final float DRAG_THRESHOLD_DP = 100.0f;
    private int peekThreshold;
    private float dpScaleConverter;

    static final int CLOSED = 0;
    static final int OPENING = 1;
    static final int OPENED = 2;
    static final int CLOSING = 3;
    DrawerStatus drawerStatus;
    public enum DrawerStatus {
        CLOSED, OPENING_LEFT, LEFT_OPEN, CLOSING_LEFT, OPENING_RIGHT, RIGHT_OPEN, CLOSING_RIGHT

    }




    public MyDrawerLayout (View centerView, DrawerFragment leftPanel, Context context) {

        activity = new MainActivity();
        this.centerView = centerView;
        this.leftPanel = leftPanel;
        this.context = context;
        distributeDrawerID();
        drawerStatus = DrawerStatus.CLOSED;

        drawerListener = new MyDrawerLayout.MyDrawerListener() {
            @Override
            public void onDrawerSlide(View view, float percent) {}

            @Override
            public void onDrawerOpen(View view) {}

            @Override
            public void onDrawerClose(View view) {}
        };

        hasLeft = true;

        dpScaleConverter = context.getResources().getDisplayMetrics().density;
        peekThreshold = (int)(DRAG_THRESHOLD_DP * dpScaleConverter + 0.5f);

    }

    /*optional constructor for dual drawer layouts*/
    public MyDrawerLayout (View centerView, DrawerFragment leftPanel, DrawerFragment rightPanel, Context context) {

        activity = new MainActivity();
        this.centerView = centerView;
        this.leftPanel = leftPanel;
        this.rightPanel = rightPanel;
        this.context = context;
        distributeDrawerID();
        drawerStatus = DrawerStatus.CLOSED;


        drawerListener = new MyDrawerLayout.MyDrawerListener() {
            @Override
            public void onDrawerSlide(View view, float percent) {}

            @Override
            public void onDrawerOpen(View view) {}

            @Override
            public void onDrawerClose(View view) {}
        };

        hasLeft = true;
        hasRight = true;

        dpScaleConverter = context.getResources().getDisplayMetrics().density;
        peekThreshold = (int)(DRAG_THRESHOLD_DP * dpScaleConverter + 0.5f);

    }

    void distributeDrawerID() {
        if (leftPanel != null) {
            leftPanel.setDrawerID(++drawerCount);
            Log.i("SAM", "MyDrawerLayout.distributeID() leftDrawerID" + leftPanel.getDrawerID());
        }
        if (rightPanel != null) {
            rightPanel.setDrawerID(++drawerCount);
            Log.i("SAM", "MyDrawerLayout.distributeID() rightDrawerID" + rightPanel.getDrawerID());

        }
    }

    public View whichDrawer(int drawerID){
        switch (drawerID) {
            case 1:
                return leftPanel.getPanelView();
            case 2:
                return rightPanel.getPanelView();
            default:
                return leftPanel.getPanelView();
        }
    }

    private void determineDistanceX(int drawerID) {
        switch (drawerID) {
            case 1:
                dx = leftPanel.getDrawerOffset();
                break;
            case 2:
                dx = rightPanel.getDrawerOffset();
                break;
            default:
        }
    }

    public DrawerStatus getDrawerStatus () {
        return drawerStatus;
    }

    private void setDrawerStatus(int drawerID, int state) {
        if (state == 0) {
            drawerStatus = DrawerStatus.CLOSED;
        } else {
            if (drawerID == 1) {
                switch (state) {
                    case OPENING:
                        drawerStatus = DrawerStatus.OPENING_LEFT;
                        break;
                    case OPENED:
                        drawerStatus = DrawerStatus.LEFT_OPEN;
                        break;
                    case CLOSING:
                        drawerStatus = DrawerStatus.CLOSING_LEFT;
                        break;
                }
            } else {
                switch (state) {
                    case OPENING:
                        drawerStatus = DrawerStatus.OPENING_RIGHT;
                        break;
                    case OPENED:
                        drawerStatus = DrawerStatus.RIGHT_OPEN;
                        break;
                    case CLOSING:
                        drawerStatus = DrawerStatus.CLOSING_RIGHT;
                        break;
                }
            }
        }
    }

    public void activateDrawer(int drawerID) {
        if (drawerStatus == DrawerStatus.CLOSED) {
            setDrawerStatus(drawerID, OPENING);
            determineDistanceX(drawerID);
            setSlideInterval(dx, OPENING);
            openDrawer(whichDrawer(drawerID),dx , drawerID);
        } else if (drawerStatus == DrawerStatus.LEFT_OPEN) {
            setDrawerStatus(drawerID, CLOSING);
            determineDistanceX(drawerID);
            setSlideInterval(dx, CLOSING);
            closeDrawer(whichDrawer(drawerID), drawerID);
        }
    }

    private void openDrawer(View view, float finalPosition, int drawerID) {
        final int postAnimState = OPENED;
        animateDrawer(view, finalPosition, postAnimState, drawerID);

    }
    private void closeDrawer(View view, int drawerID) {
        final int position = 0;
        final int postAnimState = CLOSED;
        animateDrawer(view, position, postAnimState, drawerID);

    }

    private void setSlideInterval(float dx, int state) {
        slideInterval = dx/ANIMATION_DURATION;
        if(state == CLOSING) {
            slideInterval *= -1;
        }
    }


    private void animateDrawer(final View view, final float finalPosition, final int postAnimState, final int drawerID) {

        timer = new Timer();
        timer.schedule(new TimerTask() {
            boolean sliding = true;
            int milliSecCount = 0;
            @Override
            public void run() {
                if (sliding) {

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            sliding = isSliding(view, finalPosition, postAnimState, drawerID, milliSecCount);
                            milliSecCount++;
                        }
                    });
                }
            }
        }, 0, 1);
    }

    private boolean isSliding(View view, float finalPosition, int postAnimState, int drawerID, int duration) {

        if (duration < ANIMATION_DURATION) {
            float position = view.getTranslationX() + slideInterval;
            view.setTranslationX(position);
            float percentChange = position/dx;
            drawerListener.onDrawerSlide(view, percentChange);

        } else {
            view.setTranslationX(finalPosition);
            timer.cancel();

            float percentChange = view.getTranslationX()/dx;
            drawerListener.onDrawerSlide(view, percentChange);

            setDrawerStatus(drawerID, postAnimState);
            return false;
        }
        return true;

    }

    private void isSliding(View view, float currentPosition) {
        if (currentPosition >= 0 && currentPosition <= dx) {
            view.setTranslationX(currentPosition);
            float percentChange = currentPosition / dx;
            drawerListener.onDrawerSlide(view, percentChange);

        }
    }


    public void startUserDrag(MotionEvent event) {
        /*check to see if the panels have been instantiated
        * if not stop method*/
        if (leftPanel == null && rightPanel == null ) {
            Log.i("SAM", "MyDrawerLayout.startUserDrag() return null");
            return;
        }



        /*if user just began to peek, store the location of the pointer
        * and tell program that the user is peeking*/
        if (!isPeeking && drawerStatus == DrawerStatus.CLOSED) {
            startPeekLocation = (int)event.getRawX();
            if (startPeekLocation <= context.getResources().getDisplayMetrics().widthPixels/2) {
                //moving left panel
                if (leftPanel != null) {
                    activeDrawerFragmentID = leftPanel.getDrawerID();
                    determineDistanceX(leftPanel.getDrawerID());
                    setDrawerStatus(activeDrawerFragmentID, OPENING);
                } else {
                    startPeekLocation = 0;
                    return;
                }
            } else {
                //moving right panel
                if (rightPanel != null) {
                    activeDrawerFragmentID = rightPanel.getDrawerID();
                    determineDistanceX(rightPanel.getDrawerID());
                    setDrawerStatus(activeDrawerFragmentID, OPENING);
                }else {
                    startPeekLocation = 0;
                    return;
                }
            }
            isPeeking = true;
        } else if( drawerStatus == DrawerStatus.LEFT_OPEN || drawerStatus == DrawerStatus.RIGHT_OPEN) {
            setDrawerStatus(activeDrawerFragmentID, CLOSING);
            startPeekLocation = (int)event.getRawX();
            isPeeking = true;
            Log.i("SAM", "h;jui;ji;ojio;ijo;");
        }

        /*create memory space for event distance*/
        int peekDistance = ((int)event.getRawX() - startPeekLocation);
        int action = MotionEventCompat.getActionMasked(event);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP){
//            View view = whichDrawer(activeDrawerFragmentID);
            if (activeDrawerFragmentID == leftPanel.getDrawerID() && peekDistance >= dx/2) {
                completeUserOpenDrag();
            } else if (drawerCount > 1) {
                if (activeDrawerFragmentID == rightPanel.getDrawerID() && peekDistance <= dx/2) {
                    completeUserOpenDrag();
                }
            } else {
                Log.i("SAM", "MyDrawerLayout.startUserDrag() Method ACTION " + action + " Fully Close");
                Log.i("SAM", "MyDrawerLayout.startUserDrag() Peek Distance = " + peekDistance + " dx = " + dx );
                //fully close the drawer
                setSlideInterval(dx, CLOSING);
                closeDrawer(whichDrawer(activeDrawerFragmentID), activeDrawerFragmentID);
                setDrawerStatus(activeDrawerFragmentID, CLOSED);
                isPeeking = false;
            }
            startPeekLocation = 0;
            return;
        }

        /*switch to determine to do, depending on the motion event value*/
        switch (action) {
            case MotionEvent.ACTION_DOWN:
//                Log.i("SAM", "MyDrawerLayout.startUserDrag() ACTION DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
//                Log.i("SAM", "MyDrawerLayout.startUserDrag() ACTION MOVE");
                if (hasLeft && activeDrawerFragmentID == leftPanel.getDrawerID()) {
                    System.out.println("HasLeft: " + hasLeft + "HasRight: " + hasRight);
                    isSliding(leftPanel.getPanelView(), peekDistance);
                } else if (hasRight && activeDrawerFragmentID == rightPanel.getDrawerID()){
                    System.out.println("HasLeft: " + hasLeft + "HasRight: " + hasRight);
                    isSliding(rightPanel.getPanelView(), peekDistance);
                }

                break;
        }
    }

    private void completeUserOpenDrag() {
        openDrawer(whichDrawer(activeDrawerFragmentID), dx, activeDrawerFragmentID);
        setSlideInterval(dx, OPENING);
        setDrawerStatus(activeDrawerFragmentID, OPENED);
        isPeeking = false;
        startPeekLocation = 0;
    }

    public int getDragDirection(int distance){
        if (distance >= 0) {
            return 1;
        } else return 1;
    }


//    private int determineDirection() {
//        switch (drawerStatus) {
//            case OPENING_LEFT:
//                return 1;
//            case CLOSING_LEFT:
//                return -1;
//            case OPENING_RIGHT:
//                return -1;
//            case RIGHT_OPEN:
//                return 0;
//            case CLOSING_RIGHT:
//                return 1;
//            default:
//                return 1;
//        }
//    }

    protected int incrementDrawerCount() {
        return ++drawerCount;
    }

    public void setDrawerListener(MyDrawerLayout.MyDrawerListener drawerListener) {
        this.drawerListener = drawerListener;
    }

    public interface MyDrawerListener {
       public void onDrawerSlide (View view, float percent);
        public void onDrawerOpen (View view);
        public void onDrawerClose (View view);
    }

    public interface DrawerPanel {
        byte getDrawerID();
        void setDrawerID(byte id);
        float getDrawerDimension();
        float getDrawerOffset();
        View getPanelView();
    }


}

