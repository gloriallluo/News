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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.jingjia.NewsItem;
import com.java.jingjia.NewsListAdapter;
import com.java.jingjia.R;
import com.java.jingjia.util.MyScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * class NewsFragment
 * 展示新闻列表
 */
public class NewsFragment extends Fragment {

    private String TAG = "NewsFragment";
    private Activity mActivity;
    private NewsListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<NewsItem> mNewsItems;

    public NewsFragment(Activity activity) {
        mActivity = activity;
        mNewsItems = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: 1");
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        mAdapter = new NewsListAdapter(mActivity, mNewsItems);
        mRecyclerView = view.findViewById(R.id.news_rv);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(mActivity, DividerItemDecoration.HORIZONTAL));
        return view;
    }

    private void initNewsItems() {

    }

    private void setListeners() {
        mRecyclerView.addOnScrollListener(new MyScrollListener() {
            @Override
            public void onLoadMore() {
                mAdapter.setLoadState(mAdapter.LOADING);
                // TODO: finish this function
            }
        });

    }
}
