package com.java.jingjia.util.graph;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.java.jingjia.Entity;

import java.util.List;
import java.util.Map;

public class EntityDetailViewHolder extends RecyclerView.ViewHolder {

    public static final String TAG = "EntityViewHolder";
    private Context mContext;
    public TextView mAbstractInfo;
    public ImageView mImg;
    public TableLayout mProperties;
    public TableLayout mRelations;

//    String label;
//    String url;
//    String abstractInfo;
//    Map properties;
//    List<Entity.Relation> relations;
//    String imageUrl;

    public EntityDetailViewHolder(View view, Context context) {
        super(view);
        mContext =context;
    }
}
