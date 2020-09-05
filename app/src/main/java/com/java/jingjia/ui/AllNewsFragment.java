package com.java.jingjia.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
 * 具有显示新闻分类功能的滑动列表
 * TODO: 增加添加和删除功能
 */
public class AllNewsFragment extends Fragment {

    private String TAG = "AllNewsFragment";
    private Activity mActivity;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private String[] catIndicators = {"all", "news", "paper"};
    private FragmentManager fgManager;
    private FragmentPagerAdapter mFgAdapter;
    private List<NewsFragment> mFragments;

    public AllNewsFragment(Activity activity) {
        mActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_allnews, container, false);
        bindViews(view);
        initFragments();
        fgManager = getChildFragmentManager();
        mFgAdapter = new AllNewsFragmentPagerAdapter(fgManager, mFragments);
        mViewPager.setOffscreenPageLimit(catIndicators.length - 1);
        mViewPager.setAdapter(mFgAdapter);
        initTabs();
        setListeners();
        mTabLayout.getTabAt(1).select();
        return view;
    }

    /**
     * 绑定组件
     */
    private void bindViews(View view) {
        mTabLayout = view.findViewById(R.id.all_news_tabs);
        mViewPager = view.findViewById(R.id.all_news_vp);
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
        NewsFragment a = new NewsFragment(mActivity, NewsFragment.ALL);
        NewsFragment b = new NewsFragment(mActivity, NewsFragment.NEWS);
        NewsFragment c = new NewsFragment(mActivity, NewsFragment.PAPER);
        mFragments.add(a);
        mFragments.add(b);
        mFragments.add(c);
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
     * Inner class AllNewsFragmentPagerAdapter
     */
    private class AllNewsFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<NewsFragment> mFragments;

        public AllNewsFragmentPagerAdapter(FragmentManager manager, List<NewsFragment> fragments) {
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
