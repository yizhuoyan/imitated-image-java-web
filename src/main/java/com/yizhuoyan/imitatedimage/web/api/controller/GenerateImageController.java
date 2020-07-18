package com.yizhuoyan.imitatedimage.web.api.controller;

import com.yizhuoyan.imitatedimage.common.StringHelpler;
import com.yizhuoyan.imitatedimage.servcei.GenerateImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@RestController
@Slf4j
@AllArgsConstructor
public class GenerateImageController implements StringHelpler {

    private final GenerateImageService generateImageService;

    private String getImageText(HttpServletRequest req) throws UnsupportedEncodingException {
        String text=trim2Null(req.getQueryString());
        if(text!=null){
            text= URLDecoder.decode(text,"utf-8");
        }
        return text;
    }

    @RequestMapping({"/{size}", "/{size}.png"})
    public void generateBySize(@PathVariable String size,
                               HttpServletRequest req,
                               HttpServletResponse resp) throws Exception {
        String text=getImageText(req);
        log.debug("size={},text={}", size,text);
        BufferedImage    bufferedImage = generateImageService.generateBySizeAndText(size,text);

        resp.setContentType("image/png");
        ImageIO.write(bufferedImage, "png", resp.getOutputStream());
    }

    @RequestMapping({"/{size}.gif"})
    public void generateBySizeWithGif(@PathVariable String size,
                                      HttpServletRequest req,
                                      HttpServletResponse resp) throws Exception {
        String text=getImageText(req);
        log.debug("size={},text={}", size,text);
        BufferedImage bufferedImage = generateImageService.generateBySizeAndText(size,text);
        resp.setContentType("image/gif");
        ImageIO.write(bufferedImage, "gif", resp.getOutputStream());
    }

    @RequestMapping({"/{size}/{color}", "/{size}/{color}.png"})
    public void generateBySizeAndColor(@PathVariable String size,
                                       @PathVariable String color,
                                       HttpServletRequest req,
                                       HttpServletResponse resp) throws Exception {
        String text=getImageText(req);
        log.debug("size={},color={},text={}", size, color,text);
        BufferedImage bufferedImage = generateImageService.generateBySizeAndTextAndColor(size,text,color);
        resp.setContentType("image/png");
        ImageIO.write(bufferedImage, "png", resp.getOutputStream());
        System.out.println("png-ok");
    }

    @RequestMapping("/{size}/{color}.gif")
    public void generateBySizeAndColorAndSuffix(@PathVariable String size,
                                                @PathVariable String color,
                                                HttpServletRequest req,
                                                HttpServletResponse resp) throws Exception {
        String text=getImageText(req);
        log.debug("size={},color={}", size, color);
        BufferedImage bufferedImage = generateImageService.generateBySizeAndTextAndColor(size,text,color);
        resp.setContentType("image/gif");
        ImageIO.write(bufferedImage, "gif", resp.getOutputStream());
    }


}
