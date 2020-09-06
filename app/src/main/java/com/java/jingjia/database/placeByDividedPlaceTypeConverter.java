package com.java.jingjia.database;

import androidx.room.TypeConverter;

public class placeByDividedPlaceTypeConverter {
    @TypeConverter
    public String[] stringToManyString(String data) {
        if (data == null) {
            return null;
        }
        return data.split("|");
    }

    @TypeConverter
    public String ManyStringToString(String[] manyString) {
        StringBuilder sb = null;
        for(String str : manyString){
            sb.append(str+"|");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
