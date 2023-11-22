package object;

import java.awt.image.BufferedImage;

public class Material {
    private BufferedImage[] sprite;
    private int id;
    private int tileType;

    public Material(BufferedImage s,int id,int tileType){
        this.sprite = new BufferedImage[1];
        this.sprite[0] = s;
        this.id = id;
        this.tileType = tileType;
    }

    public Material(BufferedImage[] s, int id,int tileType){
        this.sprite = s;
        this.id = id;
        this.tileType = tileType;
    }

    public int getTileType() {
        return tileType;
    }

    public BufferedImage getSprite(int animation){
        return sprite[animation];
    }
    public BufferedImage getSprite(){
        return sprite[0];
    }

    public boolean isAnimation(){
        return sprite.length>1;
    }

    public int getId() {
        return id;
    }

}
