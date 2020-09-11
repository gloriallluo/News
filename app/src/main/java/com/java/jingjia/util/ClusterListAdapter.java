package com.java.jingjia.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.java.jingjia.R;
import com.java.jingjia.ui.cluster.EventItem;

import java.util.List;

public class ClusterListAdapter extends RecyclerView.Adapter<ClusterListAdapter.ViewHolder> {

    private final String TAG = "ClusterListAdapter";
    private AppCompatActivity mActivity;
    private List<EventItem> mEventItems;

    public ClusterListAdapter(AppCompatActivity activity, List<EventItem> items) {
        mActivity = activity;
        mEventItems = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EventItem item = mEventItems.get(position);
        if (item != null) {
            holder.mTitle.setText(item.getTitle());
            holder.mDate.setText(item.getDate());
        }
    }

    @Override
    public int getItemCount() {
        return mEventItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle, mDate;
        ViewHolder(View view) {
            super(view);
            mTitle = view.findViewById(R.id.item_event_title);
            mDate = view.findViewById(R.id.item_event_date);
        }
    }
}
