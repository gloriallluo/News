package com.java.jingjia.ui.graph;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.java.jingjia.Entity;
import com.java.jingjia.R;
import com.java.jingjia.database.Data;
import com.java.jingjia.request.KnowledgeGraphManager;
import com.java.jingjia.util.data.DataExpandableListAdapter;
import com.java.jingjia.util.graph.EntityExpandableListAdapter;
import com.java.jingjia.util.graph.EntityViewHolder;
import com.java.jingjia.util.graph.GraphDataHelper;
import com.java.jingjia.util.graph.GraphSearchSuggestion;
import com.poliveira.parallaxrecyclerview.ParallaxRecyclerAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphFragment extends Fragment {

    private static final int FIND_SUGGESTION_SIMULATED_DELAY = 20;
    private final String TAG = "GraphFragment";
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

    private void iniEntityItems() {
    }



    private void setListeners() {
        Log.i(TAG, "setListeners:");
        //ExpandableListView
        //不自动滑倒顶上
        entityExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if(parent.isGroupExpanded(groupPosition)){
                    parent.collapseGroup(groupPosition);
                }else{
                    parent.expandGroup(groupPosition,false);//第二个参数false表示展开时是否触发默认滚动动画
                }
                //telling the listView we have handled the group click, and don't want the default actions.
                return true;
            }
        });
        //FloatingSearchView
        mFSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {
                Log.i(TAG, "onSearchTextChanged: ");
//                //get suggestions based on newQuery
//                List<GraphSearchSuggestion> newSuggestions = new ArrayList<>();
//                newSuggestions.add(new GraphSearchSuggestion("sug1"));
//                newSuggestions.add(new GraphSearchSuggestion("sug2"));
//                newSuggestions.add(new GraphSearchSuggestion("sug3"));
//                //pass them on to the search view
//                mFSearchView.swapSuggestions(newSuggestions);
                if (!oldQuery.equals("") && newQuery.equals("")) {
                    mFSearchView.clearSuggestions();
                } else {
                    GraphDataHelper.findSuggestions(getActivity(), newQuery, 5,
                            FIND_SUGGESTION_SIMULATED_DELAY, new GraphDataHelper.OnFindSuggestionsListener() {
                                @Override
                                public void onResults(List<GraphSearchSuggestion> results) {
//                                    mFSearchView.hideProgress();
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
                    Log.d(TAG, "onSearchAction: myEntityList size " + myEntityList.size());
                    Log.d(TAG, "onSearchAction: mAdapter.gData size " + mAdapter.gData.size());
//                    iData.clear();
//                    for(Entity e : myEntityList){
//                        List<Entity> newE = new ArrayList<>();
//                        newE.add(e);
//                        iData.add(newE);
//                    }
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        mFSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                Log.i(TAG, "onFocus: ");
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    // 展示历史搜索项
//                    mFSearchView.swapSuggestions(GraphDataHelper.getHistory(getActivity(), 3));
//                }
            }
            @Override
            public void onFocusCleared() {
                Log.i(TAG, "onFocusCleared: ");
                mFSearchView.setSearchBarTitle(mLastQuery);
                mFSearchView.bringToFront();
                //你也可以将已经打上的搜索字符保存，以致在下一次点击的时候，搜索栏内还保存着之前输入的字符
//                mSearchView.setSearchText(searchSuggestion.getBody());
            }
        });
    }
}
