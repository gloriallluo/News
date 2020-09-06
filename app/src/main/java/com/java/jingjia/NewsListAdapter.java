package com.java.jingjia;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.jingjia.ui.NewsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * class NewsListAdapter
 * pass data to RecyclerView
 */
public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String TAG = "NewsListAdapter";
    private Activity mActivity;
    private List<NewsItem> mNewsItems;

    private int refreshState = 2;   // 现在的刷新状态
    private int loadState = 4;      // 现在的加载状态

    /**
     * 布局分类
     */
    private final int TYPE_ITEM = -1;        // 普通布局
    private final int TYPE_HEADER = -2;      // 头布局
    private final int TYPE_FOOTER = -3;      // 脚布局

    /**
     * 下拉刷新的相关变量
     */
    public final int REFRESHING = 1;           // 正在刷新
    public final int REFRESH_COMPLETE = 2;  // 刷新完成

    /**
     * 上拉加载的相关变量
     */
    public final int LOADING = 3;           // 正在加载
    public final int LOAD_COMPLETE = 4;  // 加载完成
    public final int LOAD_END = 5;       // 无更多内容

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
        } else if (viewType == TYPE_HEADER) {
            view = inflater.inflate(R.layout.item_news_header, parent, false);
            return new HeadViewHolder(view);
        } else {    // TYPE_FOOTER
            view = inflater.inflate(R.layout.item_news_footer, parent, false);
            return new FootViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            NewsItem item = mNewsItems.get(position);
            ViewHolder vHolder = (ViewHolder) holder;
            if (item != null) {
                vHolder.title.setText(item.getTitle());
                vHolder.source.setText(item.getSource());
                vHolder.time.setText(item.getTime());
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, NewsActivity.class);
                    intent.putExtra("news", mNewsItems.get(position).getId());
                    mActivity.startActivity(intent);
                }
            });
        }
//        else if (getItemViewType(position) == TYPE_HEADER) {
//            HeadViewHolder hHolder = (HeadViewHolder) holder;
//            switch (refreshState) {
//                case REFRESHING:
//                    hHolder.progressBar.setVisibility(View.VISIBLE); break;
//                case REFRESH_COMPLETE:
//                    hHolder.progressBar.setVisibility(View.GONE); break;
//                default:
//                    break;
//            }
//        }
//      else if (holder instanceof FootViewHolder) {
//            FootViewHolder fHolder = (FootViewHolder) holder;
//            switch (loadState) {
//                case LOADING:
//                    fHolder.progressBar.setVisibility(View.VISIBLE);
//                    fHolder.mLinearLayout.setVisibility(View.GONE);
//                    break;
//                case LOAD_COMPLETE:
//                    fHolder.progressBar.setVisibility(View.INVISIBLE);
//                    fHolder.mLinearLayout.setVisibility(View.GONE);
//                    break;
//                case LOAD_END:
//                    fHolder.progressBar.setVisibility(View.GONE);
//                    fHolder.mLinearLayout.setVisibility(View.VISIBLE);
//                    break;
//                default:
//                    break;
//            }
//        }
    }

    @Override
    public int getItemCount() {
        return mNewsItems.size();   // TODO: change it to +2
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    /**
     * 外部设置刷新状态
     */
    public void setRefreshState(int refreshState) {
        this.refreshState = refreshState;
        notifyDataSetChanged();
    }

    /**
     * 外部设置加载状态
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, source, time;
        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.news_item_title);
            source = view.findViewById(R.id.news_item_source);
            time = view.findViewById(R.id.news_item_time);
        }
    }

    private class HeadViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        HeadViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.footer_progress_bar);
        }
    }

    private class FootViewHolder extends RecyclerView.ViewHolder {
//        ProgressBar progressBar;
//        LinearLayout mLinearLayout;
        FootViewHolder(View view) {
            super(view);
        }
    }
}
