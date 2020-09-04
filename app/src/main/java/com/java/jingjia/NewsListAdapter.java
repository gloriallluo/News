package com.java.jingjia;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * class NewsListAdapter
 * pass data to RecyclerView
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private String TAG = "NewsListAdapter";
    private List<NewsItem> mNewsItems;

    /**
     * Inner static class ViewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title, source, time;
        public ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.news_item_image);
            title = view.findViewById(R.id.news_item_title);
            source = view.findViewById(R.id.news_item_source);
            time = view.findViewById(R.id.news_item_time);
        }
    }   /* Inner class ViewHolder */

    public NewsListAdapter() {
        // just for displaying
        mNewsItems = new ArrayList<NewsItem>();
        NewsItem item1 = new NewsItem(1, "Hello News App!");
        mNewsItems.add(item1);
        NewsItem item2 = new NewsItem(2, "Hello News App!");
        mNewsItems.add(item2);
        NewsItem item3 = new NewsItem(3, "Hello News App!");
        mNewsItems.add(item3);
        NewsItem item4 = new NewsItem(4, "Hello News App!");
        mNewsItems.add(item4);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        NewsItem item = mNewsItems.get(position);
        if (item != null) {
            holder.title.setText(item.getTitle());
            holder.source.setText(item.getSource());
            holder.time.setText(item.getTime());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + position);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mNewsItems.size();
    }
}
