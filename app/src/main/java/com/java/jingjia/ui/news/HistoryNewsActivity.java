package com.java.jingjia.ui.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.jingjia.NewsItem;
import com.java.jingjia.R;
import com.java.jingjia.request.NewsListManager;
import com.java.jingjia.util.NewsListAdapter;

import java.util.ArrayList;
import java.util.List;

public class HistoryNewsActivity extends Activity {

    private final String TAG = "HistoryNewsActivity";
    private List<NewsItem> mHistoryNewsList;
    private List<String> visitedId;
    private RecyclerView mRecyclerView;
    private NewsListAdapter mAdapter;
    private NewsListManager mManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_history);
        mRecyclerView = findViewById(R.id.history_news_rv);
        mHistoryNewsList = new ArrayList<>();
        visitedId = new ArrayList<>();
        mManager = NewsListManager.getNewsListManager(this.getApplication());
        initHistory();

        mAdapter = new NewsListAdapter(HistoryNewsActivity.this, mHistoryNewsList, visitedId);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(HistoryNewsActivity.this));

    }

    private void initHistory() {
        mHistoryNewsList = mManager.getHistoryNewsList("all");
    }


}
