package com.java.jingjia.util.data;

import android.content.Context;
import android.view.View;

public class ChildViewHolder extends BaseViewHolder {
    private Context mContext;

    public ChildViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
    }
}