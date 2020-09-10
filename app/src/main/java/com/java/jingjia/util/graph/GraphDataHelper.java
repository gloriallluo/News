package com.java.jingjia.util.graph;

import android.content.Context;
import android.widget.Filter;

import com.java.jingjia.Entity;
import com.java.jingjia.request.KnowledgeGraphManager;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.LogRecord;

public class GraphDataHelper {
    static KnowledgeGraphManager mManager;
    GraphDataHelper(){
    }

    private static final String COLORS_FILE_NAME = "colors.json";

    private static List<Entity> sEntityWrappers = new ArrayList<>();

//    private static List<GraphSearchSuggestion> sEntitySuggestions =
//            new ArrayList<>(Arrays.asList(
//                    new GraphSearchSuggestion("green"),
//                    new GraphSearchSuggestion("blue"),
//                    new GraphSearchSuggestion("pink"),
//                    new GraphSearchSuggestion("purple"),
//                    new GraphSearchSuggestion("brown"),
//                    new GraphSearchSuggestion("gray"),
//                    new GraphSearchSuggestion("Granny Smith Apple")));

    private static void initColorWrapperList(Context context) {
//        if (sEntityWrappers.isEmpty()) {
//            String jsonString = loadJson(context);
//            sEntityWrappers = deserializeColors(jsonString);
//        }
    }

    private static String loadJson(Context context) {
        String jsonString = "";
//        try {
//            InputStream is = context.getAssets().open(COLORS_FILE_NAME);
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            jsonString = new String(buffer, "UTF-8");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
        return jsonString;
    }

//    private static List<Entity> deserializeColors(String jsonString) {
//
//        Gson gson = new Gson();
//        return gson.fromJson(jsonString,  new TypeToken<List<Entity>>() {
//        }.getType());
//    }

    public interface OnFindEntityListener {
        void onResults(List<Entity> results);
    }

    public interface OnFindSuggestionsListener {
        void onResults(List<GraphSearchSuggestion> results);
    }

    public static void findSuggestions(Context context, String query, final int limit, final long simulatedDelay,
                                       final OnFindSuggestionsListener listener) {
        new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                try {
                    Thread.sleep(simulatedDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                GraphDataHelper.resetSuggestionsHistory();
                List<GraphSearchSuggestion> suggestionList = new ArrayList<>();
                if (!(constraint == null || constraint.length() == 0)) {
                    try {
                        List<Entity> someRes = new ArrayList<>();
                        someRes.addAll(new KnowledgeGraphManager().getKnowledgeGraphManager().query(constraint.toString()));
                        for(Entity e : someRes){
                            suggestionList.add(new GraphSearchSuggestion(e.getLabel()));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    for (GraphSearchSuggestion suggestion : sEntitySuggestions) {
//                        if (suggestion.getBody().toUpperCase()
//                                .startsWith(constraint.toString().toUpperCase())) {
//                            suggestionList.add(suggestion);
//                            if (limit != -1 && suggestionList.size() == limit) {
//                                break;
//                            }
//                        }
//                    }
                }
                FilterResults results = new FilterResults();
                Collections.sort(suggestionList, new Comparator<GraphSearchSuggestion>() {
                    @Override
                    public int compare(GraphSearchSuggestion lhs, GraphSearchSuggestion rhs) {
                        return lhs.getIsHistory() ? -1 : 0;
                    }
                });
                results.values = suggestionList;
                results.count = suggestionList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (listener != null) {
                    listener.onResults((List<GraphSearchSuggestion>) results.values);
                }
            }
        }.filter(query);

    }

    public static void findEntitys(Context context, String query, final OnFindEntityListener listener) {
//        initEntityWrapperList(context);

        new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Entity> suggestionList = new ArrayList<>();
                if (!(constraint == null || constraint.length() == 0)) {
                    for (Entity e : sEntityWrappers) {
                        if (e.getLabel().toUpperCase()
                                .startsWith(constraint.toString().toUpperCase())) {
                            suggestionList.add(e);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = suggestionList;
                results.count = suggestionList.size();
                return results;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (listener != null) {
                    listener.onResults((List<Entity>) results.values);
                }
            }
        }.filter(query);

    }

    public static List<GraphSearchSuggestion> getHistory(Context context, int count) {

        List<GraphSearchSuggestion> suggestionList = new ArrayList<>();
//        GraphSearchSuggestion entitySuggestion;
//        for (int i = 0; i < sEntitySuggestions.size(); i++) {
//            entitySuggestion = sEntitySuggestions.get(i);
//            entitySuggestion.setIsHistory(true);
//            suggestionList.add(entitySuggestion);
//            if (suggestionList.size() == count) {
//                break;
//            }
//        }
        return suggestionList;
    }

    public static void resetSuggestionsHistory() {
//        for (GraphSearchSuggestion entitySuggestion : sEntitySuggestions) {
//            entitySuggestion.setIsHistory(false);
//        }
    }

}