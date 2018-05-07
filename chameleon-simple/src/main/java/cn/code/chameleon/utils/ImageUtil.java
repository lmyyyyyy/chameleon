package cn.code.chameleon.utils;


import cn.code.chameleon.entity.ImageRegion;
import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;

/**
 * @author liumingyu
 * @create 2018-04-25 下午5:08
 */
public class ImageUtil {

    public static void crop(String srcfile,String destfile,ImageRegion region){
        //指定坐标
        try {
            Thumbnails.of(srcfile)
                    .sourceRegion(region.x, region.y, region.width, region.height)
                    .size(region.width, region.height).outputQuality(1.0)
                    //.keepAspectRatio(false)  //不保持比例
                    .toFile(destfile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        crop("D:\\data\\111.png","D:\\data\\1112.png",new ImageRegion(66, 264, 422, 426));
    }
}
