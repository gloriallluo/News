package com.java.jingjia;

import java.util.List;
import java.util.Map;

public class Entity {
    String label;
    String url;
    String abstractInfo;
    Map properties;
    List<Relation> relations;
    String imageUrl;

    public Entity(String label, String url, String abstractInfo,
                  Map properties, List<Relation> relations, String imageUrl){
        this.label = label;
        this.url = url;
        this.abstractInfo = abstractInfo;
        this.properties = properties;
        this.relations = relations;
        this.imageUrl = imageUrl;
    }

    public static class Relation {
        String relation;
        String url;
        String label;
        Boolean forward;
        public Relation(String a, String b, String c, Boolean d){
            this.relation = a;
            this.url = b;
            this.label = c;
            this.forward = d;
        }
    }
}