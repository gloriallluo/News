package com.java.jingjia.util.data;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.java.jingjia.R;
import com.java.jingjia.database.Data;

/**
 * Created by hbh on 2017/4/20.
 * 父布局ViewHolder
 */

public class ParentViewHolder extends BaseViewHolder {

    private Context mContext;

    public ParentViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
    }
}
