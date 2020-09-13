package com.java.jingjia.util.news;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.java.jingjia.R;

import java.util.List;

public class TabGridAdapter extends BaseAdapter {

    private final String TAG = "TabGridAdapter";
    private Context mContext;
    private String mType;
    private List<TabItem> mTabItems;
    private Animation mAnimation;

    public TabGridAdapter(Context context, List<TabItem> items, String type) {
        mContext = context;
        mTabItems = items;
        mType = type;
        mAnimation = AnimationUtils.loadAnimation(context, R.anim.edit_tab);
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
        if (mTabItems.get(position).getType().equals(TabItem.ALL))
            return 0;
        if (mTabItems.get(position).getType().equals(TabItem.NEWS))
            return 1;
        if (mTabItems.get(position).getType().equals(TabItem.PAPER))
            return 2;
        else return -1;
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
        if (mTabItems.get(position).getShaking()) {
            holder.mText.startAnimation(mAnimation);
            mTabItems.get(position).setShaking(false);    // 传引用
        }
        return convertView;
    }

    public TabItem removeItem(int position) {
        TabItem item = mTabItems.remove(position);
        item.setShaking(true);
        notifyDataSetChanged();
        return item;
    }

    public void insertItem(TabItem item) {
        if (item.getType().equals(TabItem.ALL)) {
            mTabItems.add(0, item);
        } else if (item.getType().equals(TabItem.PAPER)) {
            mTabItems.add(item);
        } else {    // type == NEWS
            if (mTabItems.size() > 0 &&
                    mTabItems.get(0).getType().equals(TabItem.ALL)) {
                mTabItems.add(1, item);
            } else {    // insert at start
                mTabItems.add(0, item);
            }
        }
        notifyDataSetChanged();
    }

    private class ViewHolder {
        TextView mText;
        ViewHolder(View view) {
            mText = view.findViewById(R.id.item_tab_tv);
        }
    }
}
