package com.tod.android.adapter;

/**
 * Simons project - Human Equation - http://www.equationhumaine.co
 * Created by yorouguidou on 15-11-07.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tod.android.DoctorActionsFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new DoctorActionsFragment();
            case 1:
                // Games fragment activity
                return new DoctorActionsFragment();
            case 2:
                // Movies fragment activity
                return new DoctorActionsFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}