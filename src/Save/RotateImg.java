package Save;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class RotateImg {

    public static BufferedImage getRotationImg (BufferedImage img, int rotation){
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage newImg = new BufferedImage(w, h, img.getType());
        Graphics2D graphics2d = newImg.createGraphics();

        graphics2d.rotate(Math.toRadians(rotation),w/2,h/2);
        graphics2d.drawImage(img, 0, 0, null);
        graphics2d.dispose();

        return newImg;
    }
    

    //build the image
    public static BufferedImage buildImg(BufferedImage[] imgs){
        int w = imgs[0].getWidth();
        int h = imgs[0].getHeight();
        BufferedImage newImg = new BufferedImage(w, h, imgs[0].getType());
        Graphics2D graphics2d = newImg.createGraphics();

        for (BufferedImage i : imgs){
            graphics2d.drawImage(i, 0, 0, null);
        }
        graphics2d.dispose();
        return newImg;
    }


    // rotate the second image
    public static BufferedImage getBuildRotImg(BufferedImage[] imgs, int rotation, int rotationIndex){
        int w = imgs[0].getWidth();
        int h = imgs[0].getHeight();
        BufferedImage newImg = new BufferedImage(w, h, imgs[0].getType());
        Graphics2D graphics2d = newImg.createGraphics();

        for(int i = 0; i<imgs.length; i++){
            if(rotationIndex == i){
                graphics2d.rotate(Math.toRadians(rotation), w/2, h/2); //rotate the image
            }
            graphics2d.drawImage(imgs[i], 0, 0, null);
            if(rotationIndex == i){
                graphics2d.rotate(Math.toRadians(-rotation),w/2, h/2); //rotate back the image after
            }
        }
        graphics2d.dispose();
        return newImg;
    }

    // rotate the second image animation
    public static BufferedImage getBuildImg(BufferedImage imgs,BufferedImage scdimgs, int rotation){
        int w = imgs.getWidth();
        int h = imgs.getHeight();
        BufferedImage tmp = new BufferedImage(w, h, imgs.getType());
        

        
        BufferedImage newImg = new BufferedImage(w, h, imgs.getType());
        Graphics2D graphics2d = newImg.createGraphics();
        graphics2d.drawImage(imgs, 0, 0,null);
        graphics2d.drawImage(scdimgs, 0,0, null);   
        graphics2d.dispose();

        tmp = newImg;
        

        return tmp;

    }

    // rotate the second image animation
    public static BufferedImage[] getBuildRotImg(BufferedImage[] imgs,BufferedImage scdimgs, int rotation){
        int w = imgs[0].getWidth();
        int h = imgs[0].getHeight();
        BufferedImage[] tmp = new BufferedImage[imgs.length];
        

        for(int i = 0; i < imgs.length; i++){
            BufferedImage newImg = new BufferedImage(w, h, imgs[0].getType());
            Graphics2D graphics2d = newImg.createGraphics();
            graphics2d.drawImage(imgs[i], 0, 0,null);
            graphics2d.rotate(Math.toRadians(rotation),w/2,h/2);
            graphics2d.drawImage(scdimgs, 0,0, null);   
            graphics2d.dispose();

            tmp[i] = newImg;
        }

        return tmp;

    }
}
