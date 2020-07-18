package com.yizhuoyan.imitatedimage.servcei.support.impl;

import com.yizhuoyan.imitatedimage.servcei.support.ColorParserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@AllArgsConstructor
public class ColorParserServiceImpl implements ColorParserService {
    private final static Pattern COLOR_RGB_PATTERN = Pattern.compile("[0-9a-fA-F]{3}");
    private final static Pattern COLOR_ARGB_PATTERN = Pattern.compile("[0-9a-fA-F]{4}");
    private final static Pattern COLOR_RRGGBB_PATTERN = Pattern.compile("[0-9a-fA-F]{6}");
    private final static Pattern COLOR_AARRGGBB_PATTERN = Pattern.compile("[0-9a-fA-F]{8}");


    @Override
    public Color parseColorByName(@NonNull String color)  {
        String colorRGB=NameColorDictionary.getColorRGB(color.toLowerCase(),null);
        if(colorRGB==null){
            return null;
        }
        return parseColorByRGB(colorRGB);
    }

    @Override
    public Color parseColorByRGB(@NonNull String rgb)  {
        if (rgb.length() == 3) {
            Matcher matcher = COLOR_RGB_PATTERN.matcher(rgb);
            if (!matcher.matches()) {
                return null;
            }
            String r = String.valueOf(rgb.charAt(0));
            String g = String.valueOf(rgb.charAt(1));
            String b = String.valueOf(rgb.charAt(2));
            return new Color(Integer.parseInt(r + r, 16), Integer.parseInt(g + g, 16), Integer.parseInt(b + b, 16));
        } else if (rgb.length() == 6) {
            Matcher matcher = COLOR_RRGGBB_PATTERN.matcher(rgb);
            if (!matcher.matches()) {
                return null;
            }
            String r = rgb.substring(0, 2);
            String g = rgb.substring(2, 4);
            String b = rgb.substring(4, 6);
            return new Color(Integer.parseInt(r, 16), Integer.parseInt(g, 16), Integer.parseInt(b, 16));
        }
        return null;
    }

    @Override
    public Color parseColorByARGB(@NonNull String argb)  {
        if (argb.length() == 4) {
            Matcher matcher = COLOR_ARGB_PATTERN.matcher(argb);
            if (!matcher.matches()) {
                return null;
            }
            String a = String.valueOf(argb.charAt(0));
            String r = String.valueOf(argb.charAt(1));
            String g = String.valueOf(argb.charAt(2));
            String b = String.valueOf(argb.charAt(3));
            return new Color(Integer.parseInt(r + r, 16), Integer.parseInt(g + g, 16), Integer.parseInt(b + b, 16), Integer.parseInt(a + a, 16));
        } else if (argb.length() == 8) {
            Matcher matcher = COLOR_AARRGGBB_PATTERN.matcher(argb);
            if (!matcher.matches()) {
                return null;
            }
            String a = argb.substring(0, 2);
            String r = argb.substring(2, 4);
            String g = argb.substring(4, 6);
            String b = argb.substring(6, 8);
            return new Color(Integer.parseInt(r, 16), Integer.parseInt(g, 16),
                    Integer.parseInt(b, 16), Integer.parseInt(a, 16));
        }
        return null;
    }

    public Color parseColor(String color) {
        //try rgb
        Color result=parseColorByRGB(color);
        if(result!=null){
            return result;
        }
        //try argb
        result=parseColorByARGB(color);
        if(result!=null){
            return result;
        }
        result=parseColorByName(color);
        //try name
        if(result!=null){
            return result;
        }

        return null;
    }
}
