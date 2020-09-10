package com.java.jingjia.ui.news;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;

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
import com.java.jingjia.util.data.SearchHistoryAdapter;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private final String TAG = "SearchActivity";
    private final int SEARCH_LIMIT = 5;
    private String lastId = "";

    private ArrayList<NewsItem> mNewsItems;
    private ArrayList<String> mSearchHistory;

    private SearchView mSearchView;
    private ListView mListView;
    private RecyclerView mRecyclerView;
    private SearchHistoryAdapter mHistoryAdapter;
    private NewsListAdapter mNewsAdapter;
    private NewsListManager listManager;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        sharedPreferences = getSharedPreferences("user_search_history", MODE_PRIVATE);
        listManager = NewsListManager.getNewsListManager(getApplication());
        mSearchView = findViewById(R.id.search_sv);
        mListView = findViewById(R.id.search_lv);
        mRecyclerView = findViewById(R.id.search_rv);

        mNewsItems = new ArrayList<>();
        mSearchHistory = new ArrayList<>();
        initSearchHistory();
        mNewsAdapter = new NewsListAdapter(SearchActivity.this, mNewsItems);
        mHistoryAdapter = new SearchHistoryAdapter(SearchActivity.this, mSearchHistory);
        mRecyclerView.setAdapter(mNewsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        mListView.setAdapter(mHistoryAdapter);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: " + query);
                lastId = "";
                mListView.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                hideSoftInput(getCurrentFocus());
                mNewsItems = searchQuery(query);
                mNewsAdapter.updateNewsItems(mNewsItems);
                mNewsAdapter.notifyDataSetChanged();
                mHistoryAdapter.insertHistoryAtFront(query);
                mHistoryAdapter.notifyDataSetChanged();
                addSharedPrefSearchHistory(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListView.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }
        });

        mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mListView.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                }
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int type = mHistoryAdapter.getItemViewType(position);
                switch (type) {
                    case SearchHistoryAdapter.HISTORY_KEYWORD:
                        mSearchView.setQuery((String)
                                mHistoryAdapter.getItem(position), false);
                        break;
                    case SearchHistoryAdapter.HISTORY_END:
                        mHistoryAdapter.removeAllHistory();
                        mHistoryAdapter.notifyDataSetChanged();
                        clearSharedPrefSearchHistory();
                        break;
                    default:
                        break;
                }
            }
        });

        mRecyclerView.addOnScrollListener(new MyScrollListener() {
            @Override
            public void onLoadMore() {
                String query = (String) mSearchView.getQuery();
                mNewsAdapter.setLoadState(mNewsAdapter.LOADING);
                ArrayList<NewsItem> items = searchQuery(query);
                mNewsItems.addAll(items);
                mNewsAdapter.updateNewsItems(mNewsItems);
                mNewsAdapter.notifyDataSetChanged();
                mNewsAdapter.setLoadState(mNewsAdapter.LOAD_COMPLETE);
            }
        });
    }

    private ArrayList<NewsItem> searchQuery(String query) {
        ArrayList<NewsItem> mItems = new ArrayList<>();
        ArrayList<NewsItem> newItems = (ArrayList<NewsItem>) listManager.getLatestNewsList("all", lastId);
        for (NewsItem item: newItems)
            if (item.getTitle().contains(query))
                mItems.add(item);
        for (int i = 0; i < SEARCH_LIMIT; i++) {
            lastId = newItems.size() > 0? newItems.get(newItems.size() - 1).getId(): "";
            newItems = listManager.getMoreNewsList("all", lastId);
            for (NewsItem item: newItems)
                if (item.getTitle().contains(query))
                    mItems.add(item);
        }
        return mItems;
    }

    private void initSearchHistory() {
        int historySize = sharedPreferences.getInt("size", 0);
        for (Integer i = historySize - 1; i >= 0; i--) {
            String keyword = (String) sharedPreferences.getString(i.toString(), "");
            mSearchHistory.add(keyword);
        }
    }

    private void addSharedPrefSearchHistory(String query) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Integer historySize = sharedPreferences.getInt("size", 0);
        editor.putString(historySize.toString(), query);
        editor.putInt("size", historySize + 1);
        editor.commit();
    }

    private void clearSharedPrefSearchHistory() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Integer historySize = sharedPreferences.getInt("size", 0);
        for (Integer i = 0; i < historySize; i++)
            editor.remove(i.toString());
        editor.putInt("size", 0);
        editor.commit();
    }

    private void hideSoftInput(View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
