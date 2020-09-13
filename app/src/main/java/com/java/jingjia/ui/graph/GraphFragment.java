package com.java.jingjia.ui.graph;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.java.jingjia.Entity;
import com.java.jingjia.R;
import com.java.jingjia.request.KnowledgeGraphManager;
import com.java.jingjia.util.graph.EntityExpandableListAdapter;
import com.java.jingjia.util.graph.GraphDataHelper;
import com.java.jingjia.util.graph.GraphSearchSuggestion;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class GraphFragment extends Fragment {

    private final String TAG = "GraphFragment";
    private static final int FIND_SUGGESTION_SIMULATED_DELAY = 20;
    private Activity mActivity;

    private FloatingSearchView mFSearchView;
    private CharSequence mLastQuery;

    List<Entity> myEntityList = new ArrayList<>(); // or another object list
    private List<List<Entity>> iData = new ArrayList<>();
    private ExpandableListView entityExpandableListView;
    private EntityExpandableListAdapter mAdapter;

    KnowledgeGraphManager mManager;

    public GraphFragment(Activity activity) {
        mActivity = activity;
        mLastQuery = "";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph, container, false);
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mManager = KnowledgeGraphManager.getKnowledgeGraphManager();
        /**
         * 绑定组件
         */
        mFSearchView = view.findViewById(R.id.floating_search_view);
        entityExpandableListView = view.findViewById(R.id.graph_entity_list);
        mAdapter = new EntityExpandableListAdapter(myEntityList,iData,mActivity);
        entityExpandableListView.setAdapter(mAdapter);
        //有关List
        iniEntityItems();
        setListeners();
        return view;
    }

    private void iniEntityItems() { }

    private void setListeners() {
        // ExpandableListView
        // 不自动滑倒顶上
        entityExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (parent.isGroupExpanded(groupPosition)) {
                    parent.collapseGroup(groupPosition);
                } else {
                    parent.expandGroup(groupPosition,false);    //第二个参数false表示展开时是否触发默认滚动动画
                }
                return true;
            }
        });
        //FloatingSearchView
        mFSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {
                if (!oldQuery.equals("") && newQuery.equals("")) {
                    mFSearchView.clearSuggestions();
                } else {
                    GraphDataHelper.findSuggestions(getActivity(), newQuery, 5,
                            FIND_SUGGESTION_SIMULATED_DELAY, new GraphDataHelper.OnFindSuggestionsListener() {
                                @Override
                                public void onResults(List<GraphSearchSuggestion> results) {
                                    mFSearchView.swapSuggestions(results);
                                }
                            });
                }
            }
        });

        mFSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {
                Log.d(TAG, "onSuggestionClicked()");
                mLastQuery = searchSuggestion.getBody();
                mFSearchView.setSearchText(mLastQuery);
            }

            /**按下搜索*/
            @Override
            public void onSearchAction(String query) {
                Log.d(TAG, "onSearchAction()");
                mLastQuery = query;
                try {
                    myEntityList.clear();
                    myEntityList.addAll(mManager.query(query));
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        mFSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() { }

            @Override
            public void onFocusCleared() {
                mFSearchView.setSearchBarTitle(mLastQuery);
                mFSearchView.bringToFront();
            }
        });
    }
}
