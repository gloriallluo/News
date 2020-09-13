package com.java.jingjia.ui.news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.java.jingjia.R;
import com.java.jingjia.util.news.TabItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 具有显示新闻分类功能的滑动列表
 */
public class AllNewsFragment extends Fragment {

    private final String TAG = "AllNewsFragment";
    private Activity mActivity;

    private SearchView mSearchView;
    private TabLayout mTabLayout;
    private Button mBtnEdit;
    private ViewPager mViewPager;
    private FragmentManager fgManager;
    private AllNewsAdapter mFgAdapter;
    private List<NewsFragment> mFragments;
    private SharedPreferences sharedPreferences;
    private Button mHistoryBtn;

    public AllNewsFragment() { }
    public AllNewsFragment(Activity activity) {
        mActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_allnews, container, false);
        bindViews(view);
        fgManager = getChildFragmentManager();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mActivity == null) return;
        // moved from onCreateView
        initFragments();
        mFgAdapter = new AllNewsAdapter(fgManager, mFragments);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(mFgAdapter);
        initTabs();
        setListeners();
        if (mFragments.size() > 0)
            mTabLayout.getTabAt(0).select();
    }

    /**
     * 绑定组件
     */
    private void bindViews(View view) {
        mSearchView = view.findViewById(R.id.all_news_sv);
        mTabLayout = view.findViewById(R.id.all_news_tabs);
        mBtnEdit = view.findViewById(R.id.all_news_edit);
        mHistoryBtn = view.findViewById(R.id.all_news_history_button);
        mViewPager = view.findViewById(R.id.all_news_vp);
        initButtonImg();
    }

    private void initButtonImg() {
        //定义底部标签图片大小和位置
        Drawable drawableHistory = getResources().getDrawable(R.drawable.history_button);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawableHistory.setBounds(0, 0, 50, 50);
        //设置图片在文字的哪个方向
        mHistoryBtn.setCompoundDrawables(null, drawableHistory, null, null);
        Drawable drawableEdit = getResources().getDrawable(R.drawable.edit_tab);
        drawableEdit.setBounds(0, 0, 45, 45);
        mBtnEdit.setCompoundDrawables(drawableEdit, null, null, null);
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
        for (NewsFragment fragment: mFragments)
            Log.d(TAG, "initFragments: " + fragment.getType());
    }

    /**
     * 初始化标签
     * 应该initFragments之后调用本函数
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

        mHistoryBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, HistoryNewsActivity.class);
                startActivity(intent);
            }
        });

        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: onClick");
                Intent intent = new Intent(mActivity, SearchActivity.class);
                startActivity(intent);
            }
        });

        mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Log.d(TAG, "onFocusChange: 111");
                    Intent intent = new Intent(mActivity, SearchActivity.class);
                    startActivity(intent);
                    mSearchView.clearFocus();
                } else {
                    hideSoftInput(v);
                }
            }
        });
    }

    private void hideSoftInput(View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

    public class AllNewsAdapter extends FragmentStatePagerAdapter {
        private List<NewsFragment> mFragments;

        public AllNewsAdapter(FragmentManager manager, List<NewsFragment> fragments) {
            super(manager);
            mFragments = fragments;
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

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }
}
