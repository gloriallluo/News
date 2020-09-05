package com.java.jingjia;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    private String TAG = "NewsListAdapter";
    private Activity mActivity;
    private List<NewsItem> mNewsItems;

    private int loadState = 2;
    private final int TYPE_ITEM = 1;        // 普通布局
    private final int TYPE_HEADER = 2;      // 头布局
    private final int TYPE_FOOTER = 3;      // 脚布局
    public final int LOADING = 1;           // 正在加载
    public final int LOADING_COMPLETE = 2;  // 加载完成
    public final int LOADING_END = 3;       // 已到最底端

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
        NewsItem item = mNewsItems.get(position);
        if (holder instanceof ViewHolder) {
            ViewHolder vHolder = (ViewHolder) holder;
            if (item != null) { // !
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
//        else if (holder instanceof HeadViewHolder) {
//            HeadViewHolder hHolder = (HeadViewHolder) holder;
//        } else if (holder instanceof FootViewHolder) {
//            FootViewHolder fHolder = (FootViewHolder) holder;
//            switch (loadState) {
//                case LOADING:
//                    fHolder.progressBar.setVisibility(View.VISIBLE);
//                    fHolder.mLinearLayout.setVisibility(View.GONE);
//                    break;
//                case LOADING_COMPLETE:
//                    fHolder.progressBar.setVisibility(View.INVISIBLE);
//                    fHolder.mLinearLayout.setVisibility(View.GONE);
//                    break;
//                case LOADING_END:
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
//        if (position == 0) {
//            return TYPE_HEADER;
//        } else if (position + 1 == getItemCount()) {
//            return TYPE_FOOTER;
//        } else {
//            return TYPE_ITEM;
//        }
    }

    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    /**
     * Inner static class ViewHolder
     */
    private class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title, source, time;
        public ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.news_item_image);
            title = view.findViewById(R.id.news_item_title);
            source = view.findViewById(R.id.news_item_source);
            time = view.findViewById(R.id.news_item_time);
        }
    }

    private class HeadViewHolder extends RecyclerView.ViewHolder {
        HeadViewHolder(View view) {
            super(view);
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
