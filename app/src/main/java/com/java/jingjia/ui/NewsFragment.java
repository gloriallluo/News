package com.java.jingjia.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.java.jingjia.NewsItem;
import com.java.jingjia.NewsListAdapter;
import com.java.jingjia.R;
import com.java.jingjia.request.NewsListManager;
import com.java.jingjia.util.MyScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * class NewsFragment
 * 展示新闻列表
 */
public class NewsFragment extends Fragment {

    private final String TAG = "NewsFragment";
    public static final String ALL = "all";
    public static final String NEWS = "news";
    public static final String PAPER = "paper";
    
    private String type;
    private Activity mActivity;
    private NewsListManager manager;
    private NewsListAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<NewsItem> mNewsItems;

    public NewsFragment(Activity activity, String type) {
        mActivity = activity;
        mNewsItems = new ArrayList<>();
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        manager = NewsListManager.getNewsListManager(mActivity.getApplication());
        initNewsItems();
        mAdapter = new NewsListAdapter(mActivity, mNewsItems);
        mRefreshLayout = view.findViewById(R.id.news_srl);
        mRecyclerView = view.findViewById(R.id.news_rv);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        setListeners();
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(mActivity, DividerItemDecoration.HORIZONTAL));
        return view;
    }

    private void initNewsItems() {
        ArrayList<NewsItem> items = manager.getNewsList(type);
        for (NewsItem item: items) {
            mNewsItems.add(item);
        }
    }

    private boolean addNewsItemsAtStart(ArrayList<NewsItem> items) {
        if (items.size() == 0) return false;
        ArrayList<NewsItem> temp = new ArrayList<>();
        temp.addAll(mNewsItems);
        mNewsItems.clear();
        mNewsItems.addAll(items);
        mNewsItems.addAll(temp);
        mAdapter.updateNewsItems(mNewsItems);
        return true;
    }

    private boolean addNewsItemsAtEnd(ArrayList<NewsItem> items) {
        if (items.size() == 0) return false;
        mNewsItems.addAll(items);
        mAdapter.updateNewsItems(mNewsItems);
        return true;
    }

    private void setListeners() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ArrayList<NewsItem> items = manager.getLatestNewsList(
                        type, mNewsItems.get(0).getId());
                if (!addNewsItemsAtStart(items))
                    Toast.makeText(getContext(), "No more news", Toast.LENGTH_LONG).show();
                mAdapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);
            }
        });
//        mRecyclerView.addOnScrollListener(new MyScrollListener() {
//            @Override
//            public void onRefresh() {   // 顶部下拉刷新
//                mAdapter.setRefreshState(mAdapter.REFRESHING);
//                new Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        final ArrayList<NewsItem> items = manager.getLatestNewsList(
//                                type, mNewsItems.get(0).getId());
//                        addNewsItems(items);
//                    }
//                }, 2000);   // 加载限时2秒
//                mAdapter.setRefreshState(mAdapter.REFRESH_COMPLETE);
//            }
//        });

    }
}
