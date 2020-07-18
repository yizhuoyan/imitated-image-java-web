package com.yizhuoyan.imitatedimage.servcei.impl;

import com.yizhuoyan.imitatedimage.common.StringHelpler;
import com.yizhuoyan.imitatedimage.servcei.GenerateImageService;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;

@Slf4j
public abstract class AbstractCommonGenerateImageService implements GenerateImageService, StringHelpler {

    @Override
    public BufferedImage generateBySize(String size) throws Exception {
        int[] wh=parseWidthAndHeight(size);
        return generateBySizeAndTextAndColor(size,(String)null,(Color) null);
    }

    @Override
    public BufferedImage generateBySize(int width, int height) throws Exception {
        return generateBySizeAndTextAndColor(width,height,(String)null,(Color) null);
    }

    @Override
    public BufferedImage generateBySizeAndText(int width, int height, String text) throws Exception {
        return generateBySizeAndTextAndColor(width,height,text,(Color) null);
    }

    @Override
    public BufferedImage generateBySizeAndText(String size, String text) throws Exception {
        return generateBySizeAndTextAndColor(size,text,(Color) null);
    }

    @Override
    public BufferedImage generateBySizeAndTextAndColor(String size, String text, String color) throws Exception {
        return generateBySizeAndTextAndColor(size,text,parseColor(color));
    }

    @Override
    public BufferedImage generateBySizeAndColor(String size, Color color) throws Exception {
        return generateBySizeAndTextAndColor(size,null,color);
    }
    @Override
    public BufferedImage generateBySizeAndColor(int width, int height, Color color) throws Exception {
        return generateBySizeAndTextAndColor(width,height,null,color);
    }

    @Override
    public BufferedImage generateBySizeAndColor(String size, String color) throws Exception {
        return generateBySizeAndTextAndColor(size,null,color);
    }

    @Override
    public BufferedImage generateBySizeAndColor(int width, int height, String color) throws Exception {
        return generateBySizeAndTextAndColor(width,height,null,color);
    }

    @Override
    public BufferedImage generateBySizeAndTextAndColor(String size, String text, Color color) throws Exception {
        int[] wh=parseWidthAndHeight(size);
        return generateBySizeAndTextAndColor(wh[0],wh[1],text,color);
    }
    @Override
    public BufferedImage generateBySizeAndTextAndColor(int width, int height, String text, String color) throws Exception {
        return generateBySizeAndTextAndColor(width,height,text,parseColor(color));
    }
    protected abstract Color parseColor(String color) ;
    protected abstract int[] parseWidthAndHeight(String size) ;

}
