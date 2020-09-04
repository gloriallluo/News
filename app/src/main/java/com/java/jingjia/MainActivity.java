package com.java.jingjia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.LinkedList;
import java.util.List;

/**
 * Main page of the News App.
 */
public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private List<Fragment> mFragments;
    private FragmentManager fgManager;
    private FragmentPagerAdapter mFgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: 1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragments = new LinkedList<>();
        bindViews();
        initFragments();
        fgManager = getSupportFragmentManager();
        mFgAdapter = new MainFragmentPagerAdapter(fgManager, mFragments);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mFgAdapter);
        setListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void bindViews() {
        mViewPager = findViewById(R.id.main_vp);
        mRadioGroup = findViewById(R.id.tabs_rg);
    }

    /**
     * Create three Fragments,
     * which are the news page, covid-data page and the user page
     */
    private void initFragments() {
        Fragment fgNews = new NewsFragment(MainActivity.this);
        Fragment fgData = new DataFragment(MainActivity.this);
        Fragment fgUser = new UserFragment(MainActivity.this);
        mFragments.add(fgNews);
        mFragments.add(fgData);
        mFragments.add(fgUser);
    }

    /**
     * Set OnPageChangeListener to the ViewPager and
     * OnCheckedChangeListener to the RadioGroup
     */
    private void setListeners() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                RadioButton r = (RadioButton) mRadioGroup.getChildAt(position);
                r.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tab_news:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.tab_data:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.tab_user:
                        mViewPager.setCurrentItem(2);
                        break;
                    default:
                        break;
                }
        }});
    }

    /**
     * Inner class MainFragmentPagerAdapter
     * to manage the Fragments
     */
    private class MainFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments;

        public MainFragmentPagerAdapter(FragmentManager manager, List<Fragment> fragments) {
            super(manager);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            if (mFragments != null) return mFragments.get(position);
            else return null;
        }

        @Override
        public int getCount() {
            if (mFragments != null) return mFragments.size();
            else return 0;
        }
    }   /* inner class mainFragmentPagerAdapter */
}
