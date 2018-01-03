package com.example.anshul.tpocampus.Student;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anshul.tpocampus.R;

/**
 * Created by ANSHUL KISHORE on 08-10-2017.
 */

public class profile_profile extends Fragment {

    View view;
    ViewPager viewPager;
    TabLayout tabLayout;

    public profile_profile(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_profile, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new sliderAdapter(getChildFragmentManager()));
        tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return view;
    }

    private class sliderAdapter extends FragmentPagerAdapter {

        public sliderAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;

//            if(position == 0) {
//                fragment = new display_personal();
//            }
//            if(position == 1) {
//                fragment = new display_academic();
//            }
//            if(position == 2) {
//                fragment = new display_projects();
//            }
//            if(position == 3) {
//                fragment = new display_resume();
//            }
//            if(position == 4) {
//                fragment = new display_change_password();
//            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Tab1";
                case 1:
                    return "Tab2";
                case 2:
                    return "Tab3";
                case 3:
                    return "Tab4";
                case 4:
                    return "Tab5";
            }
            return null;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Profile");
    }
}
