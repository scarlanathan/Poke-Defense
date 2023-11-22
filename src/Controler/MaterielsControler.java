package Controler;

import static Save.Constants.Tiles.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Save.LoadSave;
import Save.RotateImg;
import object.Material;

public class MaterielsControler {
    public Material GRASS,WATER,ROADH,ROADV,
        BOTLEFT_WATER,BOTRIGHT_WATER,TOPLEFT_WATER,TOPRIGHT_WATER,
        VTURNR_ROAD,VTURNL_ROAD, HTURNR_ROAD, HTURNL_ROAD,
        BORDERTOP_WATER, BORDERRIGHT_WATER,BORDERBOT_WATER,BORDERLEFT_WATER,
        ISLANDTL,ISLANDTR,ISLANDBR,ISLANDBL,
        CASTLE;

    public BufferedImage minions;
    
    public ArrayList<Material> materials = new ArrayList<>();
    
    public ArrayList<Material> roads = new ArrayList<>();
    public ArrayList<Material> turn = new ArrayList<>();
    public ArrayList<Material> corner = new ArrayList<>();
    public ArrayList<Material> border = new ArrayList<>();
    public ArrayList<Material> island = new ArrayList<>();



    public MaterielsControler(){
        loadSprites();
        createMaterials();
    }

    private void createMaterials(){
        
        int id = 0;

        materials.add(GRASS = new Material(getSprite(9, 0),id++,GRASS_TILE)); //1 = grass
        materials.add(WATER = new Material(getAnySprites(0, 0),id++,WATER_TILE));//0 = water
        
        roads.add(ROADH = new Material(getSprite(8, 0),id++,ROAD_TILE));//[2] = roadH
        roads.add(ROADV = new Material(RotateImg.getRotationImg(getSprite(8,0),90), id++,ROAD_TILE));//[2] = roadV
        
        turn.add(VTURNR_ROAD = new Material(getSprite(7, 0), id++,ROAD_TILE));
        turn.add(HTURNR_ROAD = new Material(RotateImg.getRotationImg(getSprite(7,0), 180), id++,ROAD_TILE));
        turn.add(HTURNL_ROAD = new Material(RotateImg.getRotationImg(getSprite(7,0),-90), id++,ROAD_TILE));
        turn.add(VTURNL_ROAD = new Material(RotateImg.getRotationImg(getSprite(7,0),90), id++,ROAD_TILE));
        
        corner.add(BOTLEFT_WATER = new Material(RotateImg.getBuildRotImg(getAnySprites(0, 0),getSprite(5,0),0), id++,WATER_TILE));
        corner.add(BOTRIGHT_WATER = new Material(RotateImg.getBuildRotImg(getAnySprites(0, 0),getSprite(5,0),-90), id++,WATER_TILE));
        corner.add(TOPLEFT_WATER = new Material(RotateImg.getBuildRotImg(getAnySprites(0, 0),getSprite(5,0),90), id++,WATER_TILE));
        corner.add(TOPRIGHT_WATER = new Material(RotateImg.getBuildRotImg(getAnySprites(0, 0),getSprite(5,0),180), id++,WATER_TILE));
        
        border.add(BORDERTOP_WATER = new Material(RotateImg.getBuildRotImg(getAnySprites(0, 0),getSprite(6,0),0),id++,WATER_TILE));
        border.add(BORDERRIGHT_WATER = new Material(RotateImg.getBuildRotImg(getAnySprites(0, 0),getSprite(6,0),90),id++,WATER_TILE));
        border.add(BORDERBOT_WATER = new Material(RotateImg.getBuildRotImg(getAnySprites(0, 0),getSprite(6,0),180),id++,WATER_TILE));
        border.add(BORDERLEFT_WATER = new Material(RotateImg.getBuildRotImg(getAnySprites(0, 0),getSprite(6,0),-90),id++,WATER_TILE));
        

        island.add(ISLANDTL = new Material(RotateImg.getBuildRotImg(getAnySprites(0, 0),getSprite(4,0),0), id++,WATER_TILE));
        island.add(ISLANDTR = new Material(RotateImg.getBuildRotImg(getAnySprites(0, 0),getSprite(4,0),90), id++,WATER_TILE));
        island.add(ISLANDBR = new Material(RotateImg.getBuildRotImg(getAnySprites(0, 0),getSprite(4,0),180), id++,WATER_TILE));
        island.add(ISLANDBL = new Material(RotateImg.getBuildRotImg(getAnySprites(0, 0),getSprite(4,0),-90), id++,WATER_TILE));
        


        materials.addAll(roads);
        materials.addAll(turn);
        materials.addAll(corner);
        materials.addAll(border);
        materials.addAll(island);

        materials.add(CASTLE = new Material(RotateImg.getBuildImg(getSprite(8, 0),getSprite(8,3),0),20,ROAD_TILE));
    }

    
    private void loadSprites() {
        minions = LoadSave.getSpritesMinions();
    }


    public boolean isSpriteAnimation(int id) {
        return materials.get(id).isAnimation();
    }


    //getter

    public Material getTile(int id){
        return materials.get(id);
    }
    public BufferedImage getSprite(int i){
        return materials.get(i).getSprite();
    }
    private BufferedImage[] getAnySprites(int xCoord, int yCoord){
        BufferedImage[] tmp = new BufferedImage[4];
        for(int i = 0; i<4; i++){
            tmp[i] = getSprite(xCoord+i,yCoord);
        }
        return tmp;
    }
    private BufferedImage getSprite(int xCoord, int yCoord){
        return minions.getSubimage(xCoord*32, yCoord*32, 32, 32);
    }
    public BufferedImage getAniSprite(int id,int animation){
        return materials.get(id).getSprite(animation);
    }
    public Material getMaterial(int id) {
        return materials.get(id);
    }
    public ArrayList<Material> getRoads() {
        return roads;
    }
    public ArrayList<Material> getTurn() {
        return turn;
    }
    public ArrayList<Material> getBorder() {
        return border;
    }
    public ArrayList<Material> getCorner() {
        return corner;
    }
    public ArrayList<Material> getIsland() {
        return island;
    }

    
}
