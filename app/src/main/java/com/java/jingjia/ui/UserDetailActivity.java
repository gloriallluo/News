package com.java.jingjia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.jingjia.NewsItem;
import com.java.jingjia.NewsListAdapter;
import com.java.jingjia.R;

import java.util.ArrayList;
import java.util.List;

/**
 * class UserDetailActivity
 * 展示用户的个人收藏，下载，访问记录等等
 */
public class UserDetailActivity extends AppCompatActivity {

    private String TAG = "UserDetailActivity";
    private NewsListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private TextView mTextView;
    private List<NewsItem> mNewsItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        setContentView(R.layout.activity_userdetail);
        mNewsItems = new ArrayList<>();

        Intent intent = getIntent();
        bindViews();
        int type = intent.getIntExtra("user", 0);
        switch (type) {
            case 1:
                mTextView.setText(R.string.user_collection); break;
            case 2:
                mTextView.setText(R.string.user_downloads); break;
            case 3:
                mTextView.setText(R.string.user_history); break;
            default:
                break;
        }
        mAdapter = new NewsListAdapter(UserDetailActivity.this, mNewsItems);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
    }

    private void bindViews() {
        mRecyclerView = findViewById(R.id.user_detail_rv);
        mTextView = findViewById(R.id.user_detail_tv);
    }
}
