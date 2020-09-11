package com.java.jingjia.util.graph;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.java.jingjia.Entity;

import java.util.List;
import java.util.Map;

public class EntityViewHolder extends RecyclerView.ViewHolder {

    public static final String TAG = "EntityViewHolder";
    private Context mContext;
    public TextView mLabel;

    public EntityViewHolder(View view, Context context) {
        super(view);
        mContext =context;
    }
}
