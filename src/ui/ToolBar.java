package ui;

import static main.GameState.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Save.LoadSave;
import object.Material;
import sequence.MapEditing;

public class ToolBar extends Bar {
    private Button buttonM, buttonSave;
    private MapEditing editing;
    private Material selectedMat;

    private Map<Button,ArrayList<Material>> map = new HashMap<Button,ArrayList<Material>>();
    private Button bGrass, bWater, bRoad, bTurn, bCorner, bBorder, bIsland;
    private Button currentButton,bcastle;
    private Button bPathStart,bpathEnd;
    private BufferedImage pathStart,pathEnd;
    private int currentIndex = 0;


    public ToolBar(int x , int y, int width, int height, MapEditing editing){
        super(x, y, width, height);
        this.editing = editing;
        initPathImgs();
        initButton();
    }

    private void initPathImgs(){
        pathStart = LoadSave.getSpritesMinions().getSubimage(7 * 32, 2 * 32, 32, 32);
        pathEnd = LoadSave.getSpritesMinions().getSubimage(8 * 32, 2 * 32, 32, 32);
    }

    private void initButton(){

        buttonSave = new Button("Save", 2, 672, 100, 30);
        buttonM = new Button("Menu", 2, 642, 100, 30);

        int w = 50;
        int h = 50;
        int xStart = 130;
        int yStart = 650;
        int xOffset = (int)(w*1.1f);
    
        int i = 0;

        bGrass = new Button("Grass", xStart, yStart, w, h, i++);
        bWater = new Button("Water", xStart+xOffset, yStart, w, h, i++);

        initMapButton(bRoad,editing.getGame().getMaterielsControler().getRoads(), xStart, yStart, xOffset,w , h, i++);
        initMapButton(bTurn,editing.getGame().getMaterielsControler().getTurn(), xStart, yStart, xOffset,w , h, i++);
        initMapButton(bCorner,editing.getGame().getMaterielsControler().getCorner(), xStart, yStart, xOffset,w , h, i++);
        initMapButton(bBorder,editing.getGame().getMaterielsControler().getBorder(), xStart, yStart, xOffset,w , h, i++);
        initMapButton(bIsland,editing.getGame().getMaterielsControler().getIsland(), xStart, yStart, xOffset,w , h, i++);

        
        
        bPathStart = new Button("PathStart", xStart, yStart + xOffset,w , h, i++);
        bcastle = new Button("Castle", xStart+xOffset*2, yStart + xOffset, w, h, i++);

        bpathEnd = new Button("PathEnd", xStart + xOffset, yStart + xOffset,w , h, i++);
    }

    private void initMapButton(Button b, ArrayList<Material> l, int x, int y, int xOffset, int w, int h, int id){
        b = new Button("", x + xOffset*id, y, w, h, id);
        map.put(b,l);
    }

    private void drawButton(Graphics g){
        buttonM.draw(g);
        buttonSave.draw(g);
        bcastle.draw(g);
        drawPathButton(g,bPathStart,pathStart);
        drawPathButton(g,bpathEnd,pathEnd);
        
        //drawButtonMaterials(g);
        drawDefaultImage(g,bGrass);
        drawDefaultImage(g,bWater);
        drawCastle(g, bcastle);
        drawSelectedMat(g);
        drawMapButton(g);
    }

    private void drawPathButton(Graphics g,Button b,BufferedImage img){
        g.drawImage(img, b.x, b.y,b.width,b.height,null );
        drawFeedbackButton(g, b);
    }

    public void rotateSprite(){
        currentIndex++;
        if(currentIndex >= map.get(currentButton).size()){
            currentIndex = 0;
        }
        selectedMat = map.get(currentButton).get(currentIndex);
        editing.setSelectedMat(selectedMat);
    }

    private void drawDefaultImage(Graphics g, Button b){
        g.drawImage(getButtonImg(b.getId()), b.x, b.y,b.width,b.height, null);
        drawFeedbackButton(g, b);
    }

    private void drawCastle(Graphics g, Button b){
        g.setColor(new Color(220,123,15));
        g.fillRect(b.x, b.y,b.width,b.height);
        g.drawImage(getButtonImg(20), b.x, b.y,b.width,b.height, null);
        drawFeedbackButton(g, b);
    }

    private BufferedImage getButtonImg(int id) {
        return editing.getGame().getMaterielsControler().getSprite(id);
    }


    private void drawMapButton(Graphics g) {
        for(Map.Entry<Button,ArrayList<Material>> e : map.entrySet()){
            Button b = e.getKey();
            BufferedImage img = e.getValue().get(0).getSprite();

            g.drawImage(img, b.x, b.y, b.width, b.height, null);
            //mouseOver
            if(b.isMouseOver()){
                g.setColor(Color.white);
            }else{
                g.setColor(Color.BLACK);
            }
            //border
            g.drawRect(b.x, b.y, b.width, b.height);

            //mousePressed
            if(b.isMousePressed()){
                g.drawRect(b.x+1, b.y+1, b.width-2, b.height-2);
                g.drawRect(b.x+2, b.y+2, b.width-4, b.height-4);
            }
        }

    }

    private void saveLevel(){
        editing.saveLevel();
    }


    private void drawSelectedMat(Graphics g) {
        if(selectedMat != null){
            g.drawImage(selectedMat.getSprite(), 550, 650, 50, 50, null);
            g.setColor(Color.black);
            g.drawRect(550, 650, 50, 50);
        }
    }

    public void draw(Graphics g){
        g.setColor(new Color(220,123,15));
        g.fillRect(x, y, width, height);

        drawButton(g);

    }


    public void mouseClicked(int x, int y) {
        if(buttonM.getRectangle().contains(x,y)){
            SetGameState(MENU);
        }else if(buttonSave.getRectangle().contains(x, y)){
            saveLevel();
        }else if(bGrass.getRectangle().contains(x, y)){
            selectedMat = editing.getGame().getMaterielsControler().getMaterial(bGrass.getId());
            editing.setSelectedMat(selectedMat);
        }else if(bWater.getRectangle().contains(x, y)){
            selectedMat = editing.getGame().getMaterielsControler().getMaterial(bWater.getId());
            editing.setSelectedMat(selectedMat);
        }else if(bPathStart.getRectangle().contains(x, y)){
            selectedMat = new Material(pathStart, -1, -1);
            editing.setSelectedMat(selectedMat);
        }else if(bpathEnd.getRectangle().contains(x, y)){
            selectedMat = new Material(pathEnd, -2, -2);
            editing.setSelectedMat(selectedMat);
        }else if(bcastle.getRectangle().contains(x, y)){
            selectedMat = editing.getGame().getMaterielsControler().getMaterial(20);
            editing.setSelectedMat(selectedMat);
        }else{
            for(Button b : map.keySet()){
                if(b.getRectangle().contains(x, y)){
                    selectedMat = map.get(b).get(0);
                    editing.setSelectedMat(selectedMat);
                    currentButton = b;
                    currentIndex = 0;
                    return;
                }
            }
        }
    }


    public void mouseMoved(int x, int y) {
        buttonM.setMouseOver(false);
        buttonSave.setMouseOver(false);
        bGrass.setMouseOver(false);
        bWater.setMouseOver(false);

        bPathStart.setMouseOver(false);
        bpathEnd.setMouseOver(false);

        for(Button b : map.keySet()){
                b.setMouseOver(false);
            }
        if(buttonM.getRectangle().contains(x,y)){
            buttonM.setMouseOver(true);
        }else if(buttonSave.getRectangle().contains(x, y)){
            buttonSave.setMouseOver(true);
        }else if(bGrass.getRectangle().contains(x, y)){
            bGrass.setMouseOver(true);
        }else if(bWater.getRectangle().contains(x, y)){
            bWater.setMouseOver(true);
        }else if(bPathStart.getRectangle().contains(x, y)){
            bPathStart.setMouseOver(true);
        }else if(bpathEnd.getRectangle().contains(x, y)){
            bpathEnd.setMouseOver(true);
        }else{
            for(Button b : map.keySet()){
                if(b.getRectangle().contains(x, y)){
                    b.setMouseOver(true);
                }
            }
        }
    }


    public void mousePressed(int x, int y) {
        if(buttonM.getRectangle().contains(x,y)){
            buttonM.setMousePressed(true);
        }else if(buttonSave.getRectangle().contains(x, y)){
            buttonSave.setMousePressed(true);
        }else if(bGrass.getRectangle().contains(x, y)){
            bGrass.setMousePressed(true);
        }else if(bWater.getRectangle().contains(x, y)){
            bWater.setMousePressed(true);
        }else{
            for(Button b : map.keySet()){
                if(b.getRectangle().contains(x, y)){
                    b.setMousePressed(true);
                    return;
                }
            }
        }
    }


    public void mouseReleased(int x, int y) {
        buttonM.resetBooleans();
        buttonSave.resetBooleans();
        bGrass.resetBooleans();
        bWater.resetBooleans();
        for(Button b : map.keySet()){
            b.resetBooleans();
        }
    }

    public BufferedImage getStartPathImg(){
        return pathStart;
    }

    public BufferedImage getEndPathImg(){
        return pathEnd;
    }


    
}
