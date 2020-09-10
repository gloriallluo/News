package com.java.jingjia.ui;

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
import android.widget.ImageView;
import android.widget.TextView;

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
import com.java.jingjia.request.KnowledgeGraphManager;
import com.java.jingjia.util.graph.EntityViewHolder;
import com.java.jingjia.util.graph.GraphDataHelper;
import com.java.jingjia.util.graph.GraphSearchSuggestion;
import com.poliveira.parallaxrecyclerview.ParallaxRecyclerAdapter;

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
    RecyclerView entityRecyclerView;
    RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;

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
        mManager = new KnowledgeGraphManager().getKnowledgeGraphManager();
        /**
         * 绑定组件
         */
        mFSearchView = view.findViewById(R.id.floating_search_view);
        entityRecyclerView = view.findViewById(R.id.entity_recycler);
        entityRecyclerView.setLayoutManager(manager);
        entityRecyclerView.setHasFixedSize(true);

        //有关List
        iniEntityItems();

        mAdapter = new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new EntityViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entity, parent, false), mActivity);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                ((EntityViewHolder) viewHolder).mLabel = viewHolder.itemView.findViewById(R.id. entity_item_label);
                ((EntityViewHolder) viewHolder).mLabel.setText(myEntityList.get(i).getLabel()); // your bind holder routine.
                ((EntityViewHolder) viewHolder).mAbstractInfo = viewHolder.itemView.findViewById(R.id. entity_item_info);
                ((EntityViewHolder) viewHolder).mAbstractInfo.setText(myEntityList.get(i).getAbstractInfo());
                ((EntityViewHolder) viewHolder).mImg = viewHolder.itemView.findViewById(R.id. entity_item_image);
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            URL picUrl = new URL(myEntityList.get(i).getImageUrl());
                            Bitmap pngBM = BitmapFactory.decodeStream(picUrl.openStream());
                            ((EntityViewHolder) viewHolder).mImg.setImageBitmap(pngBM); // your bind holder routine.
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                ((EntityViewHolder) viewHolder).properties = viewHolder.itemView.findViewById(R.id.entity_item_properties);
                StringBuilder sb = new StringBuilder();
                for(Object e : myEntityList.get(i).getProperties().entrySet()){
                    Map.Entry<String,String> entry = (Map.Entry<String,String>)e;
                    sb.append(entry.getValue() + " : " + entry.getValue() + "\n");
                }
                ((EntityViewHolder) viewHolder).properties.setText(sb.toString());
            }

            @Override
            public int getItemCount() {
                return myEntityList.size();
            }

//            @Override
//            public void onBindViewHolderImpl(RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapter<Entity> parallaxRecyclerAdapter, int i) {
//                // If you're using your custom handler (as you should of course)
//                // you need to cast viewHolder to it.
//                ((EntityViewHolder) viewHolder).mLabel = viewHolder.itemView.findViewById(R.id. entity_item_label);
//                ((EntityViewHolder) viewHolder).mLabel.setText(myEntityList.get(i).getLabel()); // your bind holder routine.
//                ((EntityViewHolder) viewHolder).mAbstractInfo = viewHolder.itemView.findViewById(R.id. entity_item_info);
//                ((EntityViewHolder) viewHolder).mAbstractInfo.setText(myEntityList.get(i).getAbstractInfo());
//                ((EntityViewHolder) viewHolder).mImg = viewHolder.itemView.findViewById(R.id. entity_item_image);
//                new Thread(new Runnable(){
//                    @Override
//                    public void run() {
//                        try {
//                            URL picUrl = new URL(myEntityList.get(i).getImageUrl());
//                            Bitmap pngBM = BitmapFactory.decodeStream(picUrl.openStream());
//                            ((EntityViewHolder) viewHolder).mImg.setImageBitmap(pngBM); // your bind holder routine.
//                        } catch (MalformedURLException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//                ((EntityViewHolder) viewHolder).properties = viewHolder.itemView.findViewById(R.id.entity_item_properties);
//                StringBuilder sb = new StringBuilder();
//                for(Object e : myEntityList.get(i).getProperties().entrySet()){
//                    Map.Entry<String,String> entry = (Map.Entry<String,String>)e;
//                    sb.append(entry.getValue() + " : " + entry.getValue() + "\n");
//                }
//                ((EntityViewHolder) viewHolder).properties.setText(sb.toString());
//            }
//
//            @Override
//            public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, ParallaxRecyclerAdapter<Entity> parallaxRecyclerAdapter, int i) {
//                // Here is where you inflate your row and pass it to the constructor of your ViewHolder
//                return new EntityViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_entity, viewGroup, false), mActivity);
//            }
//
//            @Override
//            public int getItemCountImpl(ParallaxRecyclerAdapter<Entity> parallaxRecyclerAdapter) {
//                // return the content of your array
//                return myEntityList.size();
//            }
        };
//        mAdapter.setParallaxHeader(getLayoutInflater().inflate(R.layout.my_header_img, entityRecyclerView, false), entityRecyclerView);
//        mParallaxRecyclerAdapter.setOnParallaxScroll(new ParallaxRecyclerAdapter.OnParallaxScroll() {
//            // Event triggered when the parallax is being scrolled.
//            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//            @Override
//            public void onParallaxScroll(float percentage, float offset, View parallax) {
////                Log.i(TAG, "onParallaxScroll: ");
////                Drawable c = mFSearchView.getBackground();
////                c.setAlpha(Math.round(percentage * 255));
////                mFSearchView.setBackground(c);
//            }
//        });
//        mParallaxRecyclerAdapter.setOnClickEvent(new ParallaxRecyclerAdapter.OnClickEvent() {
//            @Override
//            // Event triggered when you click on a item of the adapter.
//            public void onClick(View view, int i) {
////                Toast.makeText(mActivity, "你点击了：" + myEntityList.get(i).getLabel(), Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(mActivity, NewsActivity.class);
////                intent.putExtra;
////                mActivity.startActivity(intent);
//            }
//        });
        entityRecyclerView.setAdapter(mAdapter);

        setListeners();
        return view;
    }

    private void iniEntityItems() {
    }



    private void setListeners() {
        mFSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView, SearchSuggestion item, int itemPosition) {
                //here you can set some attributes for the suggestion's left icon and text. For example,
                //you can choose your favorite image-loading library for setting the left icon's image.
            }

        });
        mFSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                Log.i(TAG, "onActionMenuItemSelected: ");
            }
        });
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
        mFSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                Log.i(TAG, "onActionMenuItemSelected: ");
            }

        });
        mFSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {
                Log.i(TAG, "onSuggestionClicked: ");
                mLastQuery = searchSuggestion.getBody();
            }

            /**按下搜索*/
            @Override
            public void onSearchAction(String query) {
                Log.d(TAG, "onSearchAction()");
                mLastQuery = query;
                try {
                    myEntityList.clear();
                    myEntityList.addAll(mManager.query(query));
                    Log.e(TAG, "onSearchAction: myEntityList size" + myEntityList.size());
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
//
//                }
            }
            @Override
            public void onFocusCleared() {
                Log.i(TAG, "onFocusCleared: ");
                mFSearchView.setSearchBarTitle(mLastQuery);
                //你也可以将已经打上的搜索字符保存，以致在下一次点击的时候，搜索栏内还保存着之前输入的字符
//                mSearchView.setSearchText(searchSuggestion.getBody());

            }
        });

    }


}
