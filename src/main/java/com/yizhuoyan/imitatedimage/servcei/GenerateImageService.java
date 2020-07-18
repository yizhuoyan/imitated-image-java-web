package com.yizhuoyan.imitatedimage.servcei;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface GenerateImageService {

    BufferedImage generateBySize(String size)throws Exception;
    BufferedImage generateBySize(int width,int height)throws Exception;
    BufferedImage generateBySizeAndText(int width,int height,String text)throws Exception;
    BufferedImage generateBySizeAndText(String size,String text)throws Exception;

    BufferedImage generateBySizeAndTextAndColor(int width,int height,String text,Color color)throws Exception;
    BufferedImage generateBySizeAndTextAndColor(int width,int height,String text,String color)throws Exception;
    BufferedImage generateBySizeAndTextAndColor(String size,String text,Color color)throws Exception;
    BufferedImage generateBySizeAndTextAndColor(String size,String text,String color)throws Exception;
    BufferedImage generateBySizeAndColor(String size,Color color)throws Exception;
    BufferedImage generateBySizeAndColor(String size,String color)throws Exception;
    BufferedImage generateBySizeAndColor(int width,int height,Color color)throws Exception;
    BufferedImage generateBySizeAndColor(int width,int height,String color)throws Exception;


}
