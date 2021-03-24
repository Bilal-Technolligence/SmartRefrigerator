package com.example.smartrefrigerator;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int countTab;
    public PagerAdapter(@NonNull FragmentManager fm, int countTab) {
        super(fm);
        this.countTab=countTab;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                BreakFastFragment breakFastFragment=new BreakFastFragment();
                return breakFastFragment;
            case 1:
                LunchFragment lunchFragment=new LunchFragment();
                return lunchFragment;
            case 2:
                DinnerFragment dinnerFragment=new DinnerFragment();
                return dinnerFragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return countTab;
    }
}
