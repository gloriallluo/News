package com.java.jingjia.util;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView的滑动监听
 */
public abstract class MyScrollListener extends RecyclerView.OnScrollListener {

    private final String TAG = "MyScrollListener";
    private boolean isSlidingUpward = false;        // 向上滑动

    public abstract void onLoadMore();  // 加载接口

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        isSlidingUpward = dy > 0;
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        // 不滑动时
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            int lastItemPos = manager.findLastCompletelyVisibleItemPosition();
            int itemCount = manager.getItemCount();
            if ((lastItemPos == itemCount - 1) && isSlidingUpward)
                onLoadMore();
            // isSlidingUpward = false;
        }
    }
}
