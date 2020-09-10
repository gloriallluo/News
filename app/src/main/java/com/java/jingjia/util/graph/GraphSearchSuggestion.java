package com.java.jingjia.util.graph;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

public class GraphSearchSuggestion implements SearchSuggestion {

    private String mEntityName;
    private boolean mIsHistory = false;

    public GraphSearchSuggestion(String suggestion) {
        this.mEntityName = suggestion.toLowerCase();
    }

    public GraphSearchSuggestion(Parcel source) {
        this.mEntityName = source.readString();
        this.mIsHistory = source.readInt() != 0;
    }

    public void setIsHistory(boolean isHistory) {
        this.mIsHistory = isHistory;
    }

    public boolean getIsHistory() {
        return this.mIsHistory;
    }

    @Override
    public String getBody() {
        return mEntityName;
    }

    public static final Creator<GraphSearchSuggestion> CREATOR = new Creator<GraphSearchSuggestion>() {
        @Override
        public GraphSearchSuggestion createFromParcel(Parcel in) {
            return new GraphSearchSuggestion(in);
        }

        @Override
        public GraphSearchSuggestion[] newArray(int size) {
            return new GraphSearchSuggestion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mEntityName);
        dest.writeInt(mIsHistory ? 1 : 0);
    }
}