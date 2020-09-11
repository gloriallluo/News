package com.java.jingjia.util.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.java.jingjia.R;
import com.java.jingjia.database.Data;

import java.util.List;

public class DataExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<Data> gData;
    private List<List<Data>> iData;

    public DataExpandableListAdapter(List<Data> gData,List<List<Data>> iData, Context mContext) {
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
    public Data getGroup(int groupPosition) {
        return gData.get(groupPosition);
    }

    @Override
    public Data getChild(int groupPosition, int childPosition) {
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
        ParentViewHolder groupHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_data_parent, parent, false);
            groupHolder = new ParentViewHolder(mContext, convertView);
            groupHolder.place = convertView.findViewById(R.id.data_item_place);
            groupHolder.confirmed = convertView.findViewById(R.id.data_item_confirmed);
            groupHolder.suspected = convertView.findViewById(R.id.data_item_suspected);
            groupHolder.cured = convertView.findViewById(R.id.data_item_cured);
            groupHolder.dead = convertView.findViewById(R.id.data_item_dead);
            convertView.setTag(groupHolder);
        }else{
            groupHolder = (ParentViewHolder) convertView.getTag();
        }
        groupHolder.place.setText(gData.get(groupPosition).getPlace());
        groupHolder.confirmed.setText(String.format("%d",gData.get(groupPosition).getConfirmed()));
        groupHolder.suspected.setText(String.format("%d", gData.get(groupPosition).getSuspected()));
        groupHolder.cured.setText(String.format("%d", gData.get(groupPosition).getCured()));
        groupHolder.dead.setText(String.format("%d", gData.get(groupPosition).getDead()));

        return convertView;
    }

    //取得显示给定分组给定子位置的数据用的视图
    @SuppressLint("DefaultLocale")
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder itemHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_data_child, parent, false);
            itemHolder = new ChildViewHolder(mContext,convertView);
            itemHolder.place = convertView.findViewById(R.id.data_item_place);
            itemHolder.confirmed = convertView.findViewById(R.id.data_item_confirmed);
            itemHolder.suspected = convertView.findViewById(R.id.data_item_suspected);
            itemHolder.cured = convertView.findViewById(R.id.data_item_cured);
            itemHolder.dead = convertView.findViewById(R.id.data_item_dead);
            convertView.setTag(itemHolder);
        }else{
            itemHolder = (ChildViewHolder) convertView.getTag();
        }
        itemHolder.place.setText(iData.get(groupPosition).get(childPosition).getPlace());
        itemHolder.confirmed.setText(String.format("%d", iData.get(groupPosition).get(childPosition).getConfirmed()));
        itemHolder.suspected.setText(String.format("%d", iData.get(groupPosition).get(childPosition).getSuspected()));
        itemHolder.cured.setText(String.format("%d", iData.get(groupPosition).get(childPosition).getCured()));
        itemHolder.dead.setText(String.format("%d", iData.get(groupPosition).get(childPosition).getDead()));
        return convertView;
    }


    //设置子列表是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}