package com.java.jingjia.util;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.jingjia.NewsItem;
import com.java.jingjia.R;
import com.java.jingjia.ui.NewsActivity;

import java.util.List;

/**
 * class NewsListAdapter
 * pass data to RecyclerView
 */
public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String TAG = "NewsListAdapter";
    private Activity mActivity;
    private List<NewsItem> mNewsItems;
    public FootViewHolder footViewHolder;
    private int loadState = -4;     // 现在的加载状态

    /**
     * 布局分类
     */
    private final int TYPE_ITEM = -1;       // 普通布局
    private final int TYPE_FOOTER = -2;     // 脚布局

    /**
     * 上拉加载的相关变量
     */
    public final int LOADING = -3;          // 正在加载
    public final int LOAD_COMPLETE = -4;    // 加载完成
    public final int LOAD_END = -5;         // 无更多内容

    public NewsListAdapter(Activity activity, List<NewsItem> items) {
        mActivity = activity;
        mNewsItems = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == TYPE_ITEM) {
            view = inflater.inflate(R.layout.item_news, parent, false);
            return new ViewHolder(view);
        } else {    //  TYPE_FOOTER
            view = inflater.inflate(R.layout.item_news_footer, parent, false);
            footViewHolder = new FootViewHolder(view);
            return footViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            NewsItem item = mNewsItems.get(position);
            ViewHolder vHolder = (ViewHolder) holder;
            if (item != null) {
                vHolder.title.setText(item.getTitle());
                vHolder.source.setText(item.getSource());
                vHolder.time.setText(item.getTime());
                if (item.getVisited())  // TODO: set visited news color grey
                    vHolder.title.setTextColor(R.color.yd_grey);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, NewsActivity.class);
                    intent.putExtra("news", mNewsItems.get(position).getId());
                    mActivity.startActivity(intent);
                }
            });
        } else if (holder instanceof FootViewHolder) {
            Log.d(TAG, "onBindViewHolder: " + loadState);
            FootViewHolder fHolder = (FootViewHolder) holder;
            switch (loadState) {
                case LOADING:
                    fHolder.progressBar.setVisibility(View.VISIBLE);
                    fHolder.endView.setVisibility(View.GONE);
                    break;
                case LOAD_COMPLETE:
                    fHolder.progressBar.setVisibility(View.INVISIBLE);
                    fHolder.endView.setVisibility(View.GONE);
                    break;
                case LOAD_END:
                    fHolder.progressBar.setVisibility(View.INVISIBLE);
                    fHolder.endView.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mNewsItems.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mNewsItems.size())
            return TYPE_ITEM;
        else
            return TYPE_FOOTER;
    }

    public void updateNewsItems(List<NewsItem> items) {
        mNewsItems = items;
        notifyDataSetChanged();
    }

    /**
     * 外部设置加载状态
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
        Log.d(TAG, "setLoadState: " + this.loadState);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, source, time;
        ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.news_item_title);
            source = view.findViewById(R.id.news_item_source);
            time = view.findViewById(R.id.news_item_time);
        }
    }

    private class FootViewHolder extends RecyclerView.ViewHolder {
        // int loadState = LOAD_COMPLETE;
        ProgressBar progressBar;
        View endView;
        FootViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.item_news_footer_pb);
            endView = view.findViewById(R.id.item_news_footer_end);
        }
    }
}
