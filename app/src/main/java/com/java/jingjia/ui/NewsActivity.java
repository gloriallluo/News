package com.java.jingjia.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.java.jingjia.NewsItem;
import com.java.jingjia.R;
import com.java.jingjia.request.NewsContentManager;

/**
 * 新闻详情页Activity
 */
public class NewsActivity extends AppCompatActivity {

    private final String TAG = "NewsActivity";
    private NewsItem mItem;
    private TextView mTitle, mSource, mTime, mContent;
    private Button mBtnLike, mBtnCollect, mBtnWb, mBtnWx;
    private NewsContentManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: 2");
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        setContentView(R.layout.activity_news);
        bindViews();
        mContent.setVerticalScrollBarEnabled(true);

        Intent intent = getIntent();
        String mItemId = intent.getStringExtra("news");
        manager = NewsContentManager.getNewsContentManager();
        mItem = manager.getNewsItem(mItemId);

        setListeners();
        mTitle.setText(mItem.getTitle());
        mSource.setText(mItem.getSource());
        mTime.setText(mItem.getTime());
        mContent.setText(mItem.getContent());
    }

    private void bindViews() {
        mTitle = findViewById(R.id.news_page_title);
        mSource = findViewById(R.id.news_page_source);
        mTime = findViewById(R.id.news_page_time);
        mContent = findViewById(R.id.news_page_content);
        mBtnLike = findViewById(R.id.news_page_btn_like);
        mBtnCollect = findViewById(R.id.news_page_btn_collect);
        mBtnWb = findViewById(R.id.news_page_btn_wb);
        mBtnWx = findViewById(R.id.news_page_btn_wx);
    }

    private void setListeners() {
        mBtnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: you liked this news");
            }
        });
        mBtnCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: you collected this news");
            }
        });
        mBtnWb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: you share this news to Weibo");
            }
        });
        mBtnWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: you share this news to Wechat");
            }
        });
    }
}
