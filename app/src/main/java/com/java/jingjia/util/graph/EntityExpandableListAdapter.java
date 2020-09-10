package com.java.jingjia.util.graph;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.java.jingjia.Entity;
import com.java.jingjia.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class EntityExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    public List<Entity> gData;
    public List<List<Entity>> iData;

    public EntityExpandableListAdapter(List<Entity> gData,List<List<Entity>> iData, Context mContext) {
        this.gData = gData;
        this.iData = iData;
        this.mContext = mContext;
    }



    @Override
    public int getGroupCount() {
        return gData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return iData.get(groupPosition).size();
    }

    @Override
    public Entity getGroup(int groupPosition) {
        return gData.get(groupPosition);
    }

    @Override
    public Entity getChild(int groupPosition, int childPosition) {
        return iData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //取得用于显示给定分组的视图. 这个方法仅返回分组的视图对象
    @SuppressLint("DefaultLocale")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        EntityViewHolder groupHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_entity, parent, false);
            groupHolder = new EntityViewHolder(convertView,mContext);
            groupHolder.mLabel = convertView.findViewById(R.id.entity_item_label);
            convertView.setTag(groupHolder);
        }else{
            groupHolder = (EntityViewHolder) convertView.getTag();
        }
        groupHolder.mLabel.setText(gData.get(groupPosition).getLabel());
        return convertView;
    }

    //取得显示给定分组给定子位置的数据用的视图
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("DefaultLocale")
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        EntityDetailViewHolder itemHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_entity_detail, parent, false);
            itemHolder = new EntityDetailViewHolder(convertView, mContext);
            itemHolder.mAbstractInfo = convertView.findViewById(R.id.entity_item_info );
            itemHolder.mImg = convertView.findViewById(R.id.entity_item_img);
            itemHolder.mRelations = convertView.findViewById(R.id.entity_item_relations);
            itemHolder.mProperties = convertView.findViewById(R.id.entity_item_properties);
            convertView.setTag(itemHolder);
        }else{
            itemHolder = (EntityDetailViewHolder) convertView.getTag();
        }
        itemHolder.mAbstractInfo.setText(iData.get(groupPosition).get(childPosition).getAbstractInfo());
        if(!iData.get(groupPosition).get(childPosition).getImageUrl().equals(""))
            Picasso.get().load(iData.get(groupPosition).get(childPosition).getImageUrl())
                    .resize(2000,2000).centerCrop()
                    .placeholder(R.color.yd_grey)
                    .into(itemHolder.mImg);

        //设置表头
        TableRow firstRow = new TableRow(mContext);
        TextView tableName = new TextView(mContext);
        tableName.setText("关系");
        tableName.setTextSize(14);
        firstRow.setBackgroundColor(mContext.getResources().getColor(R.color.lightYellow));
        firstRow.addView(tableName);
        itemHolder.mRelations.addView(firstRow);
        //内容
        for(Entity.Relation e : iData.get(groupPosition).get(childPosition).getRelations()){
            TableRow row = new TableRow(mContext);
            TextView relation = new TextView(mContext);
            TextView with = new TextView(mContext);
            TextView forward = new TextView(mContext);
            relation.setText(e.relation);

            with.setText(e.label);
            with.setPadding(3,1,3,1);
            with.setBackground(mContext.getResources().getDrawable(R.drawable.bg_orange_oval));
            with.setGravity(Gravity.CENTER);
            with.setTextColor(mContext.getResources().getColor(R.color.black));

            forward.setText(e.forward.toString());
            row.addView(relation);
            row.addView(forward);
            row.addView(with);
            row.setPadding(30,10,30,10);

            itemHolder.mRelations.addView(row);
        }

        //设置表头
        TableRow firstRow2 = new TableRow(mContext);
        TextView tableName2 = new TextView(mContext);
        tableName2.setText("属性");
        tableName2.setTextSize(14);
        firstRow2.setBackgroundColor(mContext.getResources().getColor(R.color.lightYellow));
        firstRow2.addView(tableName2);
        itemHolder.mProperties.addView(firstRow2);
        //内容
        for(Object e : iData.get(groupPosition).get(childPosition).getProperties().entrySet()){
            TableRow row = new TableRow(mContext);
            TextView prop = new TextView(mContext);
            TextView content = new TextView(mContext);
            prop.setText( ((Map.Entry<String,String>) e).getKey());
            content.setText(((Map.Entry<String,String>) e).getValue());
            prop.setBackground(mContext.getResources().getDrawable(R.drawable.bg_orange_oval));
            prop.setTextColor(mContext.getResources().getColor(R.color.black));
            row.addView(prop);
            row.addView(content);
            itemHolder.mProperties.addView(row);
        }
        return convertView;
    }

    //设置子列表是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}