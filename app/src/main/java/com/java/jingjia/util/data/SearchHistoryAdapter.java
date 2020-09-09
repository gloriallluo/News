package com.java.jingjia.util.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.java.jingjia.R;
import com.java.jingjia.ui.news.SearchActivity;

import java.util.List;

public class SearchHistoryAdapter extends BaseAdapter {

    private final String TAG = "SearchHistoryAdapter";
    public static final int HISTORY_START = 0;
    public static final int HISTORY_KEYWORD = 1;
    public static final int HISTORY_END = 2;
    private final String historyStart = "搜索历史";
    private final String historyEnd = "清除搜索记录";

    private Context mContext;
    private List<String> mHistoryKeywords;

    public SearchHistoryAdapter(Context context, List<String> historyKeywords) {
        mContext = context;
        mHistoryKeywords = historyKeywords;
    }

    public void insertHistoryAtFront(String history) {
        mHistoryKeywords.add(0, history);
    }

    public void removeAllHistory() {
        mHistoryKeywords.clear();
    }

    @Override
    public int getCount() {
        return mHistoryKeywords.size() + 2;
    }

    @Override
    public Object getItem(int position) {
        if (position == 0) {
            return historyStart;
        } else if (position == getCount() - 1) {
            return historyEnd;
        } else {
            return mHistoryKeywords.get(position - 1);
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_history_keyword, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            holder.mText.setText(historyStart);
        } else if (position == getCount() - 1) {
            holder.mText.setText(historyEnd);
        } else {
            holder.mText.setText(mHistoryKeywords.get(position - 1));
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return HISTORY_START;
        if (position == getCount() - 1) return HISTORY_END;
        else return HISTORY_KEYWORD;
    }

    private class ViewHolder {
        TextView mText;
        ViewHolder(View view) {
            mText = view.findViewById(R.id.item_history_kw_tv);
        }
    }
}