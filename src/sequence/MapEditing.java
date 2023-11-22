package sequence;

import static Save.Constants.Tiles.*;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import Save.LoadSave;
import main.Game;
import object.Material;
import object.PathPoint;
import ui.ToolBar;

public class MapEditing extends GameInter implements ISequence{

    private int[][] lvl;

    private Material selectedMat;
    private int mouseX, mouseY, lastMatX, lastMatY,lastMatID;
    private boolean drawSelect;
    private ToolBar toolBar;
    private PathPoint start,end;


    public MapEditing(Game game) {
        super(game);
        loadDefaultlvl();
        toolBar = new ToolBar(0, 640, 640, 160, this);
    }



    public void update(){
        updateTime();
    }
    
    @Override
    public void render(Graphics g) {
        drawlevel(g);
        toolBar.draw(g);
        drawSelectedMat(g);
        drawPathPoints(g);
    }

    public void drawPathPoints(Graphics g){
        if (start != null){
            g.drawImage(toolBar.getStartPathImg(), start.getxCord() * 32, start.getyCord() * 32, 32,32,null);
        }

        if (end != null){
            g.drawImage(toolBar.getEndPathImg(), end.getxCord() * 32, end.getyCord() * 32, 32,32,null);
        }
    }


    private void loadDefaultlvl() {
        lvl = LoadSave.GetLvlData("new_lvl");
        ArrayList<PathPoint> points = LoadSave.GetLevelPathPoints("new_lvl");
        start = points.get(0);
        end = points.get(1);
    }

    private void changeMat(int x, int y) {
        if(selectedMat != null){
            int tileX = x / 32;
            int tileY = y / 32;

            if (selectedMat.getId() >= 0){
                if(lastMatX == tileX && lastMatY == tileY && lastMatID == selectedMat.getId()){
                    return;
                }

                lastMatX = tileX;
                lastMatY = tileY;
                lastMatID = selectedMat.getId();


                lvl[tileY][tileX] = selectedMat.getId();

            }else{
                int id = lvl[tileY][tileX];
                if (game.getMaterielsControler().getTile(id).getTileType() == ROAD_TILE){
                    if (selectedMat.getId() == -1){
                        start = new PathPoint(tileX, tileY);
                    }else{
                        end = new PathPoint(tileX, tileY);
                    }
                }
            }
        }
    }

    private void drawlevel(Graphics g){
        for (int y = 0; y<lvl.length; y++){
            for (int x = 0; x<lvl[y].length; x++){
                int id = lvl[y][x];
                if(isAnimation(id)){
                    g.drawImage(getSprite(id,animation), x*32, y*32, null);
                }else{
                    g.drawImage(getSprite(id), x*32, y*32, null);
                }
            }
        }
    }

    private void drawSelectedMat(Graphics g) {
        if(selectedMat != null && drawSelect){
            g.drawImage(selectedMat.getSprite(), mouseX, mouseY, 32,32,null);
        }
    }

    public void saveLevel(){
        LoadSave.saveLevel("new_lvl", lvl,start,end);
        game.getPlaying().setLvl(lvl);
    }

    public void setSelectedMat(Material material){
        this.selectedMat = material;
        drawSelect = true;
    }

    

    @Override
    public void mouseClicked(int x, int y) {
        if(y>=640){
            toolBar.mouseClicked(x, y);
        }else{
            changeMat(mouseX, mouseY);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if(y>=640){
            toolBar.mouseMoved(x, y);
            drawSelect = false;
        }else{
            drawSelect = true;
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(y>=640){
            toolBar.mousePressed(x, y);
        }

    }

    @Override
    public void mouseReleased(int x, int y) {
        toolBar.mouseReleased(x, y);
    }

    @Override
    public void mouseDragged(int x, int y) {
        if(y >= 640){

        }else{
            changeMat(x, y);
        }
    }

    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_R){
            toolBar.rotateSprite();
        }
    }

    public void leftClick(MouseEvent e){
        if(e.getButton() == MouseEvent.BUTTON3){
            toolBar.rotateSprite();
        }
    }

    
    
}
