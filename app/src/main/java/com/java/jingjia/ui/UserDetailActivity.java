package com.java.jingjia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.jingjia.NewsItem;
import com.java.jingjia.NewsListAdapter;
import com.java.jingjia.R;
import com.java.jingjia.request.NewsListManager;
import com.java.jingjia.util.MyScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * class UserDetailActivity
 * 展示用户的个人收藏，下载，访问记录等等
 */
public class UserDetailActivity extends AppCompatActivity {

    private final String TAG = "UserDetailActivity";
    private NewsListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private TextView mTextView;
    private List<NewsItem> mNewsItems;
    private NewsListManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        setContentView(R.layout.activity_userdetail);
        manager = NewsListManager.getNewsListManager(getApplication());
        mNewsItems = new ArrayList<>();
        List<NewsItem> items = manager.getVisitedNewsList("");
        mNewsItems.addAll(items);

        Intent intent = getIntent();
        bindViews();
        int type = intent.getIntExtra("user", -1);
        switch (type) {
            case 0:
                mTextView.setText(R.string.user_history); break;
            default:
                break;
        }
        mAdapter = new NewsListAdapter(UserDetailActivity.this, mNewsItems);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addOnScrollListener(new MyScrollListener() {
            @Override
            public void onLoadMore() {
                mAdapter.setLoadState(mAdapter.LOADING);
                Log.d(TAG, "onLoadMore: 111");
                // TODO: get data from database
                mAdapter.setLoadState(mAdapter.LOAD_COMPLETE);
            }
        });
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
    }

    private void bindViews() {
        mRecyclerView = findViewById(R.id.user_detail_rv);
        mTextView = findViewById(R.id.user_detail_tv);
    }
}
