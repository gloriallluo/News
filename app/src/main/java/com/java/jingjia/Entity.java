package com.java.jingjia;

import com.bin.david.form.annotation.SmartTable;

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
        public String relation;
        public String url;
        public String label;
        public Boolean forward;
        public Relation(String a, String b, String c, Boolean d){
            this.relation = a;
            this.url = b;
            this.label = c;
            this.forward = d;
        }
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAbstractInfo() {
        return abstractInfo;
    }

    public void setAbstractInfo(String abstractInfo) {
        this.abstractInfo = abstractInfo;
    }

    public Map getProperties() {
        return properties;
    }

    public void setProperties(Map properties) {
        this.properties = properties;
    }

    public List<Relation> getRelations() {
        return relations;
    }

    public void setRelations(List<Relation> relations) {
        this.relations = relations;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}