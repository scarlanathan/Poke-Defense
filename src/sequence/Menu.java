package sequence;

import java.awt.Graphics;

import main.Game;
import ui.Button;

import static main.GameState.*;

public class Menu extends GameInter implements ISequence{
    
    protected Button buttonP, buttonS, buttonQuit, buttonE;
    


    public Menu(Game game){

        super(game);
        initButtons();

    }




    private void initButtons(){
        int w = 150;
        int h = w/3;
        int x = 640/2 - w/2;
        int y = 150;
        int yOffset = 100;
        buttonP = new Button("Play", x, y, w, h);
        buttonE = new Button("Map Edit", x, y + yOffset, w, h);
        buttonS = new Button("Settings", x, y + yOffset*2, w, h);
        buttonQuit = new Button("Exit", x, y+ yOffset*3 , w, h);

    }
    
    @Override
    public void render(Graphics g){
        drawButtons(g);
    }

    private void drawButtons(Graphics g) {
        buttonP.draw(g);
        buttonS.draw(g);
        buttonQuit.draw(g);
        buttonE.draw(g);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if(buttonP.getRectangle().contains(x, y)){
            SetGameState(PLAYING);
        }else if(buttonE.getRectangle().contains(x,y)){
            SetGameState(EDIT);
        }else if(buttonS.getRectangle().contains(x, y)){
            SetGameState(SETTINGS);
        }else if (buttonQuit.getRectangle().contains(x, y)){
            System.exit(0);
        }
    }




    @Override
    public void mouseMoved(int x, int y) {
        buttonP.setMouseOver(false);
        buttonS.setMouseOver(false);
        buttonQuit.setMouseOver(false);
        buttonE.setMouseOver(false);
        if(buttonP.getRectangle().contains(x, y)){
            buttonP.setMouseOver(true);
        }else if(buttonS.getRectangle().contains(x, y)){
            buttonS.setMouseOver(true);
        }else if (buttonE.getRectangle().contains(x, y)){
            buttonE.setMouseOver(true);
        }else if(buttonQuit.getRectangle().contains(x, y)){
            buttonQuit.setMouseOver(true);
        }
    }




    @Override
    public void mousePressed(int x, int y) {
        if(buttonP.getRectangle().contains(x, y)){
            buttonP.setMousePressed(true);
        }else if (buttonS.getRectangle().contains(x, y)){
            buttonS.setMousePressed(true);
        }else if (buttonE.getRectangle().contains(x, y)){
            buttonE.setMousePressed(true);
        }else if (buttonQuit.getRectangle().contains(x, y)){
            buttonQuit.setMousePressed(true);
        }
    }




    @Override
    public void mouseReleased(int x, int y) {
        resetButtons();
    }




    private void resetButtons() {
        buttonP.resetBooleans();
        buttonE.resetBooleans();
        
    }




    @Override
    public void mouseDragged(int x, int y) {
        
    }
}
