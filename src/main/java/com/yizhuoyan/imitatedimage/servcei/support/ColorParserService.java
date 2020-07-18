package com.yizhuoyan.imitatedimage.servcei.support;

import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public interface ColorParserService {

    public Color parseColorByName(String color);

    public  Color parseColorByRGB(String rgb);

    public  Color parseColorByARGB(String argb);

    public  Color parseColor(String color);
}
