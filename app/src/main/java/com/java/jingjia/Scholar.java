package com.java.jingjia;

public class Scholar {
    String name;/**名字*/
    String name_zh;/**中文名*/
    String affiliation;
    String affiliation_zh;
    String bio;/**人物介绍*/
    String homepage;
    String position;
    String work;/**工作*/


    String avatarImg;
    String id;
    Double activity;
    Integer citations;
    Double diversity;
    Integer gindex;
    Integer hindex;
    Double newStar, risingStar;
    Double sociability;
    Boolean is_passedaway;

    public Scholar(String name, String name_zh, String affiliation, String affiliation_zh, String bio, String homepage, String position, String work, String avatarImg, String id, Double activity, Integer citations, Double diversity, Integer gindex, Integer hindex, Double newStar, Double risingStar, Double sociability, Boolean is_passedaway) {
        this.name = name;
        this.name_zh = name_zh;
        this.affiliation = affiliation;
        this.affiliation_zh = affiliation_zh;
        this.bio = bio;
        this.homepage = homepage;
        this.position = position;
        this.work = work;
        this.avatarImg = avatarImg;
        this.id = id;
        this.activity = activity;
        this.citations = citations;
        this.diversity = diversity;
        this.gindex = gindex;
        this.hindex = hindex;
        this.newStar = newStar;
        this.risingStar = risingStar;
        this.sociability = sociability;
        this.is_passedaway = is_passedaway;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_zh() {
        return name_zh;
    }

    public void setName_zh(String name_zh) {
        this.name_zh = name_zh;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getAffiliation_zh() {
        return affiliation_zh;
    }

    public void setAffiliation_zh(String affiliation_zh) {
        this.affiliation_zh = affiliation_zh;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(String avatarImg) {
        this.avatarImg = avatarImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getActivity() {
        return activity;
    }

    public void setActivity(Double activity) {
        this.activity = activity;
    }

    public Integer getCitations() {
        return citations;
    }

    public void setCitations(Integer citations) {
        this.citations = citations;
    }

    public Double getDiversity() {
        return diversity;
    }

    public void setDiversity(Double diversity) {
        this.diversity = diversity;
    }

    public Integer getGindex() {
        return gindex;
    }

    public void setGindex(Integer gindex) {
        this.gindex = gindex;
    }

    public Integer getHindex() {
        return hindex;
    }

    public void setHindex(Integer hindex) {
        this.hindex = hindex;
    }

    public Double getNewStar() {
        return newStar;
    }

    public void setNewStar(Double newStar) {
        this.newStar = newStar;
    }

    public Double getRisingStar() {
        return risingStar;
    }

    public void setRisingStar(Double risingStar) {
        this.risingStar = risingStar;
    }

    public Double getSociability() {
        return sociability;
    }

    public void setSociability(Double sociability) {
        this.sociability = sociability;
    }

    public Boolean getIs_passedaway() {
        return is_passedaway;
    }

    public void setIs_passedaway(Boolean is_passedaway) {
        this.is_passedaway = is_passedaway;
    }
}
