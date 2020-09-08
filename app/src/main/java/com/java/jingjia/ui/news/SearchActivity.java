package com.java.jingjia.ui.news;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.jingjia.NewsItem;
import com.java.jingjia.R;
import com.java.jingjia.request.NewsListManager;
import com.java.jingjia.util.MyScrollListener;
import com.java.jingjia.util.NewsListAdapter;

import java.util.ArrayList;

// TODO: 写历史搜索记录

public class SearchActivity extends AppCompatActivity {

    private final String TAG = "SearchActivity";
    private final int MINIMUM_SIZE = 10;
    private final int SEARCH_LIMIT = 5;
    private String mQuery;
    private String lastSearchId;

    private SearchView mSearchView;
    private RecyclerView mRecyclerView;

    private NewsListAdapter mAdapter;
    private NewsListManager listManager;
    private ArrayList<NewsItem> mNewsItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listManager = NewsListManager.getNewsListManager(getApplication());
        mSearchView = findViewById(R.id.search_sv);
        mRecyclerView = findViewById(R.id.search_rv);
        mNewsItems = new ArrayList<>();
        mAdapter = new NewsListAdapter(SearchActivity.this, mNewsItems);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: " + query);
                mQuery = query;
                hideSoftInput(getCurrentFocus());
                mNewsItems = searchQuery(query);
                mAdapter.updateNewsItems(mNewsItems);
                mAdapter.notifyDataSetChanged();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mRecyclerView.addOnScrollListener(new MyScrollListener() {
            @Override
            public void onLoadMore() {
                mAdapter.setLoadState(mAdapter.LOADING);
                ArrayList<NewsItem> items = searchMore(mQuery, lastSearchId);
                mNewsItems.addAll(items);
                mAdapter.notifyDataSetChanged();
                if (items.size() == 0) mAdapter.setLoadState(mAdapter.LOAD_END);
                else mAdapter.setLoadState(mAdapter.LOAD_COMPLETE);
            }
        });
    }

    private ArrayList<NewsItem> searchQuery(String query) {
        ArrayList<NewsItem> mItems = new ArrayList<>();
        ArrayList<NewsItem> newItems = listManager.getLatestNewsList("all", "");
        for (NewsItem item: newItems)
            if (item.getTitle().contains(query))
                mItems.add(item);
        int searchTime = 0; int lastSize = 0;
        while (mItems.size() < MINIMUM_SIZE &&
                mItems.size() != lastSize &&
                searchTime < SEARCH_LIMIT) {
            searchTime++; lastSize = mItems.size();
            String lastId = newItems.get(newItems.size() - 1).getId();
            newItems = listManager.getMoreNewsList("all", lastId);
            for (NewsItem item: newItems)
                if (item.getTitle().contains(query))
                    mItems.add(item);
        }
        lastSearchId = newItems.get(newItems.size() - 1).getId();
        return mItems;
    }

    private ArrayList<NewsItem> searchMore(String query, String lastId) {
        ArrayList<NewsItem> mItems = new ArrayList<>();
        ArrayList<NewsItem> newItems = new ArrayList<>();
        int searchTime = 0; int lastSize = 0;
        while (mItems.size() < MINIMUM_SIZE &&
                mItems.size() != lastSize &&
                searchTime < SEARCH_LIMIT) {
            searchTime++;
            lastSize = mItems.size();
            if (mItems.size() > 0)
                lastId = mItems.get(mItems.size() - 1).getId();
            newItems = listManager.getMoreNewsList("all", lastId);
            for (NewsItem item: newItems)
                if (item.getTitle().contains(query))
                    mItems.add(item);
        }
        if (newItems.size() > 0)
            lastSearchId = newItems.get(newItems.size() - 1).getId();
        return mItems;
    }

    private void hideSoftInput(View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
