package com.java.jingjia;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class NewsFragment extends Fragment {

    private String TAG = "NewsFragment";
    private Activity mActivity;
    private NewsListAdapter mAdapter;
    private ListView mListView;

    public NewsFragment(Activity activity) {
        mActivity = activity;
        Log.d(TAG, "NewsFragment: 1");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: 1");
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        mAdapter = new NewsListAdapter(mActivity);
        Log.d(TAG, "onCreateView: 2");
        mListView = view.findViewById(R.id.news_lv);
        mListView.setAdapter(mAdapter);
        Log.d(TAG, "onCreateView: 3");
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: " + position);
            }
        });
        return view;
    }
}
