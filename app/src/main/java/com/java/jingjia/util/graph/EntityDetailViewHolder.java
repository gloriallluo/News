package com.java.jingjia.util.graph;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.java.jingjia.Entity;
import com.java.jingjia.R;

import java.util.List;
import java.util.Map;

public class EntityDetailViewHolder extends RecyclerView.ViewHolder {

    public static final String TAG = "EntityViewHolder";
    private Context mContext;
    public TextView mAbstractInfo;
    public ImageView mImg;
    public SmartTable mProperties;
    public SmartTable mRelations;
    public RelationTable rTable;

//    String label;
//    String url;
//    String abstractInfo;
//    Map properties;
//    List<Entity.Relation> relations;
//    String imageUrl;


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
