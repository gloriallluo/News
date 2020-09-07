package com.java.jingjia.util.data;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.java.jingjia.R;
import com.java.jingjia.database.Data;


public class BaseViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "BaseViewHolder";
    public TextView place, confirmed, suspected, cured, dead;

    public BaseViewHolder(View view) {
        super(view);
    }
}

