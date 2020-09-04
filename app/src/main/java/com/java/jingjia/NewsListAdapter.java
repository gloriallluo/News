package com.java.jingjia;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsListAdapter extends BaseAdapter {

    private String TAG = "NewsListAdapter";
    private Context mContext;
    private LayoutInflater mLayoutInflator;
    private List<NewsItem> mNewsItems;

    static class ViewHolder {
        public ImageView image;
        public TextView title, source, time;
        public ViewHolder(View view) {
            image = view.findViewById(R.id.news_item_image);
            title = view.findViewById(R.id.news_item_title);
            source = view.findViewById(R.id.news_item_source);
            time = view.findViewById(R.id.news_item_time);
        }
    }   /* Inner class ViewHolder */

    public NewsListAdapter(Context context) {
        mContext = context;
        mLayoutInflator = LayoutInflater.from(context);
        // just for displaying
        mNewsItems = new ArrayList<NewsItem>();
        NewsItem item1 = new NewsItem(1, "Hello News App!");
        mNewsItems.add(item1);
        NewsItem item2 = new NewsItem(2, "Hello News App!");
        mNewsItems.add(item2);
        NewsItem item3 = new NewsItem(1, "Hello News App!");
        mNewsItems.add(item3);
        NewsItem item4 = new NewsItem(1, "Hello News App!");
        mNewsItems.add(item4);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        NewsItem item = (NewsItem) getItem(position);
        if (convertView == null) {
            convertView = mLayoutInflator.inflate(R.layout.item_news, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (item != null) {
            holder.title.setText(item.getTitle());
            holder.source.setText(item.getSource());
            holder.time.setText(item.getTime());
        }
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        if (position < mNewsItems.size()) {
            return mNewsItems.get(position).getId();
        } else {
            return -1;
        }
    }

    @Override
    public Object getItem(int position) {
        if (position < mNewsItems.size()) {
            return mNewsItems.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return mNewsItems.size();
    }
}
