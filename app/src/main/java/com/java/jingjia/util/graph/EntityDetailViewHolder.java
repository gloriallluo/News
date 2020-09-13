package com.java.jingjia.util.graph;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bin.david.form.core.SmartTable;

import java.util.List;

public class EntityDetailViewHolder extends RecyclerView.ViewHolder {

    public static final String TAG = "EntityViewHolder";
    private Context mContext;
    public TextView mAbstractInfo;
    public ImageView mImg;
    public SmartTable mProperties;
    public SmartTable mRelations;
    public RelationTable rTable;

    public class RelationTable{
        List<String> relations;
        List<Boolean> forward;
        List<String> label;
        public RelationTable(List<String> relations, List<Boolean> forward, List<String> label) {
            this.relations = relations;
            this.forward = forward;
            this.label = label;
        }
    }

    public EntityDetailViewHolder(View view, Context context) {
        super(view);
        mContext =context;
    }
}
