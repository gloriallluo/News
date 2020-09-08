package com.java.jingjia.ui.data;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.java.jingjia.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 具有显示Data功能的滑动列表
 */
public class AllDataFragment extends Fragment {

    private final String TAG = "AllDataFragment";
    private Activity mActivity;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private String[] catIndicators = {"china", "global"};
    private FragmentManager fgManager;
    private FragmentPagerAdapter mFgAdapter;
    private List<DataFragment> mFragments;

    public AllDataFragment(Activity activity) {
        mActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alldata, container, false);
        bindViews(view);
        initFragments();
        fgManager = getChildFragmentManager();
        mFgAdapter = new AllDataFragmentPagerAdapter(fgManager, mFragments);
        mViewPager.setOffscreenPageLimit(catIndicators.length - 1);
        mViewPager.setAdapter(mFgAdapter);
        initTabs();
        setListeners();
        mTabLayout.getTabAt(0).select();
        return view;
    }

    /**
     * 绑定组件
     */
    private void bindViews(View view) {
        mTabLayout = view.findViewById(R.id.all_data_tabs);
        mViewPager = view.findViewById(R.id.all_data_vp);
    }

    /**
     * 初始化顶端的标签
     */
    private void initTabs() {
        for (String cat: catIndicators) {
            mTabLayout.addTab(mTabLayout.newTab());
        }
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).setText(catIndicators[i]);
        }
    }

    /**
     * 初始化每个类别新闻的Fragment
     */
    private void initFragments() {
        mFragments = new ArrayList<>();
        DataFragment a = new DataFragment(mActivity, "china");
        DataFragment b = new DataFragment(mActivity, "global");
        mFragments.add(a);
        mFragments.add(b);
    }

    /**
     * 设置监听器
     * 页面滑动时以及改变所选的标签时都可以更换显示的新闻碎片
     */
    private void setListeners() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                TabLayout.Tab tab = mTabLayout.getTabAt(position);
                try {
                    tab.select();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    /**
     * Inner class AllDataFragmentPagerAdapter
     */
    private class AllDataFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<DataFragment> mFragments;

        public AllDataFragmentPagerAdapter(FragmentManager manager, List<DataFragment> fragments) {
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
    }
}
