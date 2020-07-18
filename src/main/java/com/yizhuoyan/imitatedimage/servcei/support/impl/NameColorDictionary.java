package com.yizhuoyan.imitatedimage.servcei.support.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Slf4j
public class NameColorDictionary {
    private static final Map<String,String> NAME_COLOR_MAP=new HashMap<>(1024);
    private static final  String NAME_COLOR_PROPERTIES_FILE_PATH="name-colors.properties";


    static {
        try {
            loadNameColors();
            log.debug("loading  name-colors.properties is down");
        } catch (Exception e) {
            log.error("loading the file of name-colors.properties throw exception",e);
        }
    }

    public static void main(String[] args) {

    }

    private static final  void loadNameColors()throws Exception{

        InputStream in = NameColorDictionary.class.getResourceAsStream(NAME_COLOR_PROPERTIES_FILE_PATH);
        Properties properties=new Properties();
        properties.load(in);
        Set<String> strings = properties.stringPropertyNames();
        for(String name:properties.stringPropertyNames()){
            NAME_COLOR_MAP.put(name.toLowerCase(),properties.getProperty(name).substring(1));
        }
        log.debug("load name-colors:{}",NAME_COLOR_MAP);
    }

    public static String getColorRGB(@NonNull String name, String defaultColor){
        return NAME_COLOR_MAP.getOrDefault(name.toLowerCase(),defaultColor);
    }
}
