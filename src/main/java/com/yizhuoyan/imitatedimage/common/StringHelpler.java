package com.yizhuoyan.imitatedimage.common;

public interface StringHelpler {

    default String trim2Null(String s){
        if(s==null){
            return  null;
        }
        return (s=s.trim()).length()==0?null:s;
    }

    default int parseInt(String s,int defaultValue){
        if(s==null)return defaultValue;
        try {
            return Integer.parseInt(s);
        }catch (Exception e){
            return defaultValue;
        }
    }
}
