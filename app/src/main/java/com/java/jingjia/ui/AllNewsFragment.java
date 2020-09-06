package com.java.jingjia.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.java.jingjia.R;
import com.java.jingjia.util.TabItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 具有显示新闻分类功能的滑动列表
 * TODO: 增加添加和删除功能
 */
public class AllNewsFragment extends Fragment {

    private final String TAG = "AllNewsFragment";
    private Activity mActivity;
    private TabLayout mTabLayout;
    private Button mBtnEdit;
    private ViewPager mViewPager;
    private FragmentManager fgManager;
    private AllNewsAdapter mFgAdapter;
    private List<NewsFragment> mFragments;
    private SharedPreferences sharedPreferences;

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
        mFgAdapter = new AllNewsAdapter(fgManager, mFragments);
        mViewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mViewPager.setAdapter(mFgAdapter);
        initTabs();
        setListeners();
        if (mFragments.size() > 0)
            mTabLayout.getTabAt(0).select();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkSharedPreferencesState();
    }

    /**
     * 绑定组件
     */
    private void bindViews(View view) {
        mTabLayout = view.findViewById(R.id.all_news_tabs);
        mBtnEdit = view.findViewById(R.id.all_news_edit);
        mViewPager = view.findViewById(R.id.all_news_vp);
    }

    /**
     * 初始化Fragment
     */
    private void initFragments() {
        if (sharedPreferences == null)
            sharedPreferences = mActivity.getSharedPreferences("user_tab", Context.MODE_PRIVATE);
        mFragments = new ArrayList<>();
        if (sharedPreferences.getBoolean("all", true)) {
            NewsFragment fragment = new NewsFragment(mActivity, NewsFragment.ALL);
            mFragments.add(fragment);
        }
        if (sharedPreferences.getBoolean("news", true)) {
            NewsFragment fragment = new NewsFragment(mActivity, NewsFragment.NEWS);
            mFragments.add(fragment);
        }
        if (sharedPreferences.getBoolean("paper", true)) {
            NewsFragment fragment = new NewsFragment(mActivity, NewsFragment.PAPER);
            mFragments.add(fragment);
        }
    }

    /**
     * 初始化标签
     */
    private void initTabs() {
        for (int i = 0; i < mFragments.size(); i++)
            mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mFragments.size(); i++)
            mTabLayout.getTabAt(i).setText(mFragments.get(i).getType());
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
                if (tab.getPosition() < mFragments.size())
                    mViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, EditTabActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkSharedPreferencesState() {
        if (sharedPreferences == null)
            sharedPreferences = mActivity.getSharedPreferences("user_tab", Context.MODE_PRIVATE);

        boolean allSelected = sharedPreferences.getBoolean("all", true);
        boolean newsSelected = sharedPreferences.getBoolean("news", true);
        boolean paperSelected = sharedPreferences.getBoolean("paper", true);
        boolean nowSelected[] = {allSelected, newsSelected, paperSelected};
        boolean preSelected[] = {false, false, false};

        for (NewsFragment fragment: mFragments) {
            if (fragment.getType() == NewsFragment.ALL)
                preSelected[0] = true;
            if (fragment.getType() == NewsFragment.NEWS)
                preSelected[1] = true;
            if (fragment.getType() == NewsFragment.PAPER)
                preSelected[2] = true;
        }

        for (int i = 0; i < 3; i++) {
            if (preSelected[i] && !nowSelected[i])
                removeNewsType(i);
            if (!preSelected[i] && nowSelected[i])
                insertNewsType(i);
        }
    }

    private void insertNewsType(int position) {
        String type = positionToType(position);
        NewsFragment fragment = new NewsFragment(mActivity, type);
        if (position == 0) {
            mFragments.add(0, fragment);
            mTabLayout.addTab(mTabLayout.newTab(), position);
        } else if (position == 2) {
            mFragments.add(fragment);
            mTabLayout.addTab(mTabLayout.newTab());
        } else {    // position == 1
            if (mFragments.size() > 0 &&
                    mFragments.get(0).getType() == TabItem.ALL) {
                mFragments.add(position, fragment);
                mTabLayout.addTab(mTabLayout.newTab(), position);
            } else {
                mFragments.add(fragment);
                mTabLayout.addTab(mTabLayout.newTab());
            }
        }
        mFgAdapter.updateFragments(mFragments);
    }

    private void removeNewsType(int type) {
        int position = -1;
        if (type == 0) {
            position = 0;
        } else if (type == 2) {
            position = mFragments.size() - 1;
        } else {    // type == 1
            if (mFragments.size() > 0 &&
                    mFragments.get(0).getType() == TabItem.ALL) {
                position = 1;
            } else {
                position = mFragments.size() - 1;
            }
        }
        if (mViewPager.getCurrentItem() == position) {
            if (position > 1)
                mTabLayout.getTabAt(position - 1).select();
        }
        mFragments.remove(position);
        mTabLayout.removeTabAt(position);
        mFgAdapter.updateFragments(mFragments);
    }

    private String positionToType(int position) {
        String ans = "";
        switch (position) {
            case 0:
                ans = TabItem.ALL; break;
            case 1:
                ans = TabItem.NEWS; break;
            case 2:
                ans = TabItem.PAPER; break;
            default:
                break;
        }
        return ans;
    }

    public class AllNewsAdapter extends FragmentPagerAdapter {
        private List<NewsFragment> mFragments;

        public AllNewsAdapter(FragmentManager manager, List<NewsFragment> fragments) {
            super(manager);
            mFragments = fragments;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            for (int i = 0; i < mTabLayout.getTabCount(); i++)
                mTabLayout.getTabAt(i).setText(mFragments.get(i).getType());
        }

        public void updateFragments(List<NewsFragment> fragments) {
            mFragments = fragments;
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            NewsFragment fragment = null;
            if (mFragments.size() > position) {
                fragment = mFragments.get(position);
                if (fragment != null) return fragment;
            }
            while (position >= mFragments.size())
                mFragments.add(null);
            String type = positionToType(position);
            fragment = new NewsFragment(mActivity, type);
            mFragments.add(position, fragment);
            return fragment;
        }

        @Override
        public int getCount() {
            if (mFragments != null)
                return mFragments.size();
            else return 0;
        }
    }
}
