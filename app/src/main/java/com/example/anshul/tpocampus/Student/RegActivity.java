package com.example.anshul.tpocampus.Student;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.anshul.tpocampus.R;

public class RegActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded com.example.anshul.tpocampus.fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a com.example.anshul.tpocampus.fragment for each of the three
        // primary sections of the com.example.anshul.tpocampus.activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reg, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent com.example.anshul.tpocampus.activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

  /*  @Override
    public void personal_to_project(String[] data, int size) {
        FragmentManager manager = getSupportFragmentManager();
        frag_project_intern f = (frag_project_intern) manager.findFragmentById(R.id.fragment_project_intern);
    }

    @Override
    public void academic_to_project(String[] data, int size) {
        FragmentManager manager = getSupportFragmentManager();
        frag_project_intern f = (frag_project_intern) manager.findFragmentById(R.id.fragment_project_intern);
    }

    @Override
    public void project_to_project(String[] data, int size) {
        FragmentManager manager = getSupportFragmentManager();
        frag_project_intern f = (frag_project_intern) manager.findFragmentById(R.id.fragment_project_intern);
    } */

    /**
     * A placeholder com.example.anshul.tpocampus.fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The com.example.anshul.tpocampus.fragment argument representing the section number for this
         * com.example.anshul.tpocampus.fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this com.example.anshul.tpocampus.fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_reg, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a com.example.anshul.tpocampus.fragment corresponding to
     * one of the sections/tabs/pages.
     */

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SharedPreferences sharedPreferences = getSharedPreferences("edit_profile", Context.MODE_PRIVATE);
        String is_for_editing = sharedPreferences.getString("is_to_edit_profile", "no");

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the com.example.anshul.tpocampus.fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);

            Fragment fragment = null;

            if(is_for_editing.equalsIgnoreCase("yes")) {
                if(position == 0){
                    fragment = new frag_personal();
                }
                if(position == 1){
                    fragment = new frag_academic();
                }
                if(position == 2){
                    fragment = new frag_project_intern();
                }
                if(position == 3){
                    fragment = new frag_photo_resume();
                }
            }
            else{
                if(position == 0){
                    fragment = new frag_personal();
                }
                if(position == 1){
                    fragment = new frag_academic();
                }
                if(position == 2){
                    fragment = new frag_project_intern();
                }
                if(position == 3){
                    fragment = new frag_photo_resume();
                }
                if(position == 4){
                    fragment = new frag_change_pwd();
                }
            }

            return fragment;
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            if(is_for_editing.equalsIgnoreCase("yes")) {
                return 4;
            }
            else
                return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if(is_for_editing.equalsIgnoreCase("yes")) {
                switch (position) {
                    case 0:
                        return "Personal";
                    case 1:
                        return "Academic";
                    case 2:
                        return "Project/Intern";
                    case 3:
                        return "Photo/Resume";
                }
            }
            else{
                switch (position) {
                    case 0:
                        return "Personal";
                    case 1:
                        return "Academic";
                    case 2:
                        return "Project/Intern";
                    case 3:
                        return "Photo/Resume";
                    case 4:
                        return "Set Password";
                }
            }

            return null;
        }
    }
}
