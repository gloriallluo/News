package com.java.jingjia.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.java.jingjia.R;

import java.util.List;

public class TabGridAdapter extends BaseAdapter {

    private final String TAG = "TabGridAdapter";
    private Context mContext;
    private String mType;
    private List<Long> mItemIds;
    private List<TabItem> mTabItems;

    public TabGridAdapter(Context context, List<TabItem> items, List<Long> ids, String type) {
        mContext = context;
        mTabItems = items;
        mItemIds = ids;
        mType = type;
    }

    @Override
    public int getCount() {
        return mTabItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mTabItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mItemIds.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_tab, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mText.setText(((TabItem) getItem(position)).getType());
        holder.mText.setTextColor(R.color.yd_navy_blue);
        return convertView;
    }

    private class ViewHolder {
        TextView mText;
        ViewHolder(View view) {
            mText = view.findViewById(R.id.item_tab_tv);
        }

    }
}
