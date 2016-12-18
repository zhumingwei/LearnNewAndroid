package org.myteam.learnnewandroid.util;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.view.View;

/**
 * Created by zhumingwei on 16/12/18.
 */

public class DrawerLayoutToogle extends DrawerArrowDrawable implements DrawerLayout.DrawerListener {

//    DrawerLayout drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
//
//    toogle = new DrawerLayoutToogle(drawerLayout.getContext(), drawerLayout);
//    toogle.setColor(getResources().getColor(R.color.white));
//    imageMenu.setImageDrawable(toogle);
//    imageMenu.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            toogle.toggle();
//        }
//    });
//    toogle.syncState();
//    drawerLayout.setDrawerListener(toogle);

    DrawerLayout mDrawerLayout;

    public DrawerLayoutToogle(Context context, DrawerLayout drawerLayout) {
        super(context);
        this.mDrawerLayout = drawerLayout;
        setDirection(ARROW_DIRECTION_END);
    }

    public void syncState() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            setPosition(1);
        } else {
            setPosition(0);
        }
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        setPosition(Math.min(1f, Math.max(0, slideOffset)));

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        setPosition(1);
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        setPosition(0);
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
    public void toggle() {
        boolean b = mDrawerLayout.isDrawerOpen(GravityCompat.END);
        if (b) {
            mDrawerLayout.closeDrawer(GravityCompat.END);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.END);
        }
    }
    private void setPosition(float position) {

        if (position == 1f) {
            setVerticalMirror(true);
        } else if (position == 0f) {
            setVerticalMirror(false);
        }
        setProgress(position);
    }
}
