package com.java.jingjia.util.data;

import android.content.Context;
import android.view.View;

import com.java.jingjia.DataBean;
import com.java.jingjia.database.Data;
import com.java.jingjia.util.data.BaseViewHolder;

public class ChildViewHolder extends BaseViewHolder {
    private Context mContext;

    public ChildViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
    }
}