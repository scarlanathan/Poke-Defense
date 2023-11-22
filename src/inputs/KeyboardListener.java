package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static main.GameState.*;

import main.Game;
import main.GameState;

public class KeyboardListener implements KeyListener{
    private Game game;
    public KeyboardListener(Game game){
        this.game = game;
    }
    @Override
    public void keyTyped(KeyEvent e){

    }

    @Override
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_M){
            GameState.gameState = MENU;
        }else if (e.getKeyCode() == KeyEvent.VK_P){
            GameState.gameState = PLAYING;
        }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            GameState.gameState = SETTINGS;
        }else if (GameState.gameState == EDIT){
            game.getEditing().keyPressed(e);
        }else if (e.getKeyCode() == KeyEvent.VK_S){
            game.getPlaying().keyPressed(e);
        }

    }

    @Override
    public void keyReleased(KeyEvent e){

    }

}
