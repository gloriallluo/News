package com.java.jingjia.ui.cluster;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.java.jingjia.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

public class AllClusterFragment extends Fragment
        implements ViewAnimator.ViewAnimatorListener {

    private final String TAG = "AllClusterFragment";
    private static final int clusterNum = 6;
    private View mView;
    private AppCompatActivity mActivity;
    private FragmentManager fgManager;

    private DrawerLayout mDrawerLayout;             // 聚类标签
    private FloatingActionButton mFloatingButton;   // 控制抽屉的按钮
    private List<SlideMenuItem> mMenuItems = new ArrayList<>(); // 标签图标
    private List<ClusterFragment> mFragments;       // 展示每个聚类的Fragment
    private ViewAnimator mViewAnimator;             // 动画
    private LinearLayout mLinearLayout;             // 左边的抽屉

    public AllClusterFragment() { }
    public AllClusterFragment(AppCompatActivity activity) {
        mActivity = activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_allcluster, container, false);
        mView = view;
        bindViews(view);
        fgManager = getChildFragmentManager();
        mFragments = new ArrayList<>();
        initFragments();
        createMenuList();
        mViewAnimator = new ViewAnimator<SlideMenuItem>(     // 初始化动画
                mActivity, mMenuItems, mFragments.get(0), mDrawerLayout, this);
        return view;
    }

    private void initFragments() {
        String jsonString = "";
        try {
            InputStream inputStream = getContext().getResources().openRawResource(R.raw.events_clustering);
            int length = inputStream.available();
            byte[] buffer = new byte[length];
            inputStream.read(buffer);
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            for (Integer i = 0; i < clusterNum; i++) {  // for each cluster
                List<EventItem> nowEvents = new ArrayList<>();
                JSONArray oneCluster = jsonObject.getJSONArray(i.toString());
                for (int j = 0; j < oneCluster.length(); j++) { // for each event
                    JSONObject event = oneCluster.getJSONObject(j);
                    EventItem item = new EventItem(
                            event.getString("id"),
                            event.getString("title"),
                            event.getString("date"));
                    nowEvents.add(item);
                }   // end: for each event
                ClusterFragment fragment = new ClusterFragment(mActivity, nowEvents);
                mFragments.add(fragment);
                Log.d(TAG, "initFragments: " + mFragments.size());
            }   // end: for each cluster
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentTransaction fgTransaction = fgManager.beginTransaction();
        for (int i = 0; i < clusterNum; i++)
            fgTransaction.add(R.id.all_cluster_content_frame, mFragments.get(i));
        showFragmentAt(0, fgTransaction);
    }

    @SuppressLint("ResourceAsColor")
    private void bindViews(View view) {
        mDrawerLayout = view.findViewById(R.id.all_cluster_dl);
        mFloatingButton = view.findViewById(R.id.all_cluster_floating_btn);
        mLinearLayout = view.findViewById(R.id.all_cluster_left_drawer);

        mDrawerLayout.setScrimColor(R.color.transparent);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                mFloatingButton.setVisibility(View.GONE);
            }
            @SuppressLint("RestrictedApi")
            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                mLinearLayout.removeAllViews();
                mLinearLayout.invalidate(); // 隐藏装载抽屉的线性布局
                mFloatingButton.setVisibility(View.VISIBLE);
            }
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                if (slideOffset > 0.6 && mLinearLayout.getChildCount() == 0)
                    mViewAnimator.showMenuContent();
            }

            @Override
            public void onDrawerStateChanged(int newState) { }
        });

        mFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT, true);  // ?
            }
        });

        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();   // 点击关闭抽屉
            }
        });
    }

    /**
     * 初始化左侧抽屉菜单
     */
    private void createMenuList() {
        SlideMenuItem item0 = new SlideMenuItem("close", R.mipmap.icn_close);
        SlideMenuItem item1 = new SlideMenuItem("1", R.mipmap.icn_1);
        SlideMenuItem item2 = new SlideMenuItem("2", R.mipmap.icn_2);
        SlideMenuItem item3 = new SlideMenuItem("3", R.mipmap.icn_3);
        SlideMenuItem item4 = new SlideMenuItem("4", R.mipmap.icn_4);
        SlideMenuItem item5 = new SlideMenuItem("5", R.mipmap.icn_5);
        SlideMenuItem item6 = new SlideMenuItem("6", R.mipmap.icn_6);
        mMenuItems.add(item0);  // 叉叉
        mMenuItems.add(item1); mMenuItems.add(item2);
        mMenuItems.add(item3); mMenuItems.add(item4);
        mMenuItems.add(item5); mMenuItems.add(item6);
    }

    private void showFragmentAt(int position, FragmentTransaction fgTransaction) {
        for (int i = 0; i < clusterNum; i++)
            fgTransaction.hide(mFragments.get(i));
        fgTransaction.show(mFragments.get(position));
        fgTransaction.commit();
    }

    /**
     * 用于初始化该菜单
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);   // add menu to the action bar
    }

    /**
     * 菜单项选择的响应事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * Fragment切换时的特效
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private ScreenShotable replaceFragment(View view, ScreenShotable screenShotable, int position, int topPosition) {
        View cfView = view.findViewById(R.id.all_cluster_content_frame);    // 装载菜单项的
        int finalRadius = Math.max(cfView.getHeight(), cfView.getWidth());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(
                cfView, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
        view.findViewById(R.id.all_cluster_content_overlay).setBackground(
                new BitmapDrawable(getResources(), screenShotable.getBitmap()));    // 这是在干嘛？
        animator.start();
        FragmentTransaction fgTransaction = fgManager.beginTransaction();
        showFragmentAt(position - 1, fgTransaction);
        return mFragments.get(position - 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case ClusterFragment.CLOSE:
                return screenShotable;  // 点击关闭则收起菜单栏显示原来的碎片
            default:
                return replaceFragment(
                        mView, screenShotable, Integer.parseInt(slideMenuItem.getName()), position);
        }
    }

    @Override
    public void addViewToContainer(View view) {
        ViewParent parentView;
        if ((parentView = view.getParent()) != null)
            ((ViewGroup) parentView).removeView(view);
        mLinearLayout.addView(view);
    }

    @Override
    public void enableHomeButton() {
        mDrawerLayout.closeDrawers();
    }

    @Override
    public void disableHomeButton() { }
}
