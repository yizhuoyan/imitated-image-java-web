package com.yizhuoyan.imitatedimage.servcei.impl;

import com.yizhuoyan.imitatedimage.servcei.GenerateImageService;
import com.yizhuoyan.imitatedimage.servcei.support.ColorParserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class SimpleGenerateImageService extends AbstractCommonGenerateImageService  implements GenerateImageService {
    private static Pattern SIZE_WIDTH$HEIGHT_PATTERN = Pattern.compile("(\\d+)(?:\\D(\\d+))?");
    private static Pattern SIZE_RANDOM_PATTERN = Pattern.compile("random\\((.*)\\)");


    private static Pattern COLOR_PATTERN = Pattern.compile("[0-9a-fA-F]{3,8}");
    private static final String DEFAULT_IMAGE_SIZE ="200x200";
    private static String DEFAULT_IMAGE_COLOR ="000";
    private final Environment env;
    private final ColorParserService colorParser;
    private int[] defaultImageSize;
    private Color defaultImageColor;
    private int maxImageWidthAndHeight;
    /**
     * parse the width and height from the size part int request uri
     *
     * @param size the image size.eg 200x200 or only with 400
     * @return the width and height
     * if the size format error then return {200,200}
     */
    protected int[] parseWidthAndHeight(String size) {
        log.debug("parseWidthAndHeight:size={}", size);
        if ("random".equalsIgnoreCase(size)) {
            return getRandomImageSize();
        }
        int width = 0, height = 0;
        Matcher matcher = SIZE_RANDOM_PATTERN.matcher(size);
        if (matcher.matches()) {
            for(int i=0;i<=matcher.groupCount();i++){
                System.out.println(i+"="+matcher.group(i));
            }
            //random by scope
            String parameterString=trim2Null(matcher.group(1));
            log.debug("random parameterString={}",parameterString);
            if(parameterString==null){
                return parseWidthAndHeight("random");
            }
            String[] parameters=parameterString.split("\\D+");
            int[] arguments=new int[]{0,1,-1,1};
            for(int i=0;i<arguments.length;i++){
                arguments[i]=parseInt(parameters[i],0);
            }
            log.debug("random arguments:{}",arguments);
            int widthMin=Math.min(arguments[0],arguments[1]);
            int widthMax=Math.max(arguments[0],arguments[1]);
            if(widthMin==widthMax) {
                width=height=widthMin;
            }else {
                width=height=ThreadLocalRandom.current().nextInt(widthMin,widthMax);
            }
            //there is no scope of height size,use width
            if(arguments[2]==-1){
                log.debug("parseWidthAndHeight:w={},h={}", width, height);
                return new int[]{width, height};
            }

            int heightMin=Math.min(arguments[2],arguments[3]);
            int heightMax=Math.max(arguments[2],arguments[3]);
            if(heightMin==heightMax) {
                height=heightMin;
            }else{
                height=ThreadLocalRandom.current().nextInt(heightMin,heightMax);
            }
            log.debug("parseWidthAndHeight:w={},h={}", width, height);
            return new int[]{width, height};
        }

        matcher = SIZE_WIDTH$HEIGHT_PATTERN.matcher(size);
        if (!matcher.matches()) {
            return defaultImageSize;
        }

        width = height = parseInt(matcher.group(1),0);
        String heightStr = matcher.group(2);
        if(heightStr!=null) {
            height = parseInt(heightStr,0);
        }
        log.debug("parseWidthAndHeight:w={},h={}", width, height);
        return new int[]{width, height};
    }

    protected int[] getRandomImageSize() {
        int width = ThreadLocalRandom.current().nextInt(10, 1000);
        int height = ThreadLocalRandom.current().nextInt(10, 1000);
        return new int[]{width, height};
    }
    protected int[] getRandomSquareImageSize() {
        int width = ThreadLocalRandom.current().nextInt(10, 1000);
        return new int[]{width, width};
    }


    /**
     * parse the color,
     *
     * @param color accept formats:random,fff,RRGGBB,AARRGGBB
     * @return the image color
     */
    protected Color parseColor(String color) {
       return  colorParser.parseColor(color);
    }
    @PostConstruct
    private  void getConfigMaxImageSize(){
        String configSize= env.getProperty("generate.max-size");

        this.maxImageWidthAndHeight=parseInt(configSize,10000);

    }
    @PostConstruct
   private void getDefaultGenerateSize() {
       String configSize= env.getProperty("generate.default-size",DEFAULT_IMAGE_SIZE);
        Matcher matcher = SIZE_WIDTH$HEIGHT_PATTERN.matcher(configSize);
        if(!matcher.matches()){
            log.warn("the property {generate.default-size}'s value is error,use the default value:{}",DEFAULT_IMAGE_SIZE);
            configSize=DEFAULT_IMAGE_SIZE;
        }
        this.defaultImageSize=parseWidthAndHeight(configSize);
    }
    @PostConstruct
    private void getDefaultGenerateColor() {
        String configColor= env.getProperty("generate.default-color",DEFAULT_IMAGE_COLOR);
        Matcher matcher = COLOR_PATTERN.matcher(configColor);
        if(!matcher.matches()){
            log.warn("the property {generate.default-color}'s value is error,use the default value:{}",DEFAULT_IMAGE_COLOR);
            configColor=DEFAULT_IMAGE_COLOR;
        }
        this.defaultImageColor=parseColor(configColor);
    }

    private String getDefaultText(int width,int height,Color color){
        return width+"x"+height;
    }

    @Override
    public BufferedImage generateBySizeAndTextAndColor(int width, int height, String text, Color color) throws Exception {
        if(width<=0||width>maxImageWidthAndHeight){
            width=this.defaultImageSize[0];
        }
        if (height<=0||height>maxImageWidthAndHeight){
            height=this.defaultImageSize[1];
        }

        if(color==null){
            color=getRandomColor();
        }

        text=trim2Null(text);
        if(text==null){
            text=getDefaultText(width,height,color);
        }

        BufferedImage img=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        g.setColor(color);
        g.fillRect(0,0,width,height);

        g.setColor(getInverseColor(color));

        g.setFont(new Font(g.getFont().getFontName(),Font.BOLD,Math.min(width,height)/5));
        FontMetrics fontMetrics = g.getFontMetrics();
        Rectangle2D textBounds = fontMetrics.getStringBounds(text, g);
        LineMetrics lineMetrics = fontMetrics.getLineMetrics(text, g);
        int y=(int)((height-textBounds.getHeight())/2+lineMetrics.getAscent());
        int x=(int)((width-textBounds.getWidth())/2);
        g.drawString(text,x,y);
        return img;
    }

    private static Color getInverseColor(Color color){
        return new Color(255-color.getRed(),255-color.getGreen(),255-color.getBlue());
    }

    protected final Color getRandomColor() {
        return getRandomColor(true);
    }
    protected final Color getRandomColor(boolean hasAlpha) {
        return new Color(ThreadLocalRandom.current().nextInt(), hasAlpha);
    }

}
