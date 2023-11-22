package inputs;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.Game;
import main.GameState;



public class MyMouseListener implements MouseListener,MouseMotionListener{
    
    private Game game;

    public MyMouseListener(Game game){
        this.game = game;
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            //System.out.println("Posx= "+ e.getX()+"Posy= "+e.getY());
            switch (GameState.gameState) {
                case MENU:
                    game.getMenu().mouseClicked(e.getX(),e.getY());
                    break;
                case PLAYING:
                    game.getPlaying().mouseClicked(e.getX(),e.getY());
                    break;
                case SETTINGS:
                    game.getSettings().mouseClicked(e.getX(),e.getY());
                    break;
                case EDIT:
                    game.getEditing().mouseClicked(e.getX(), e.getY());
                    break;
                case GAME_OVER:
                    game.getGameOver().mouseClicked(e.getX(), e.getY());
                    break;
                default:
                    break;
            }
        }else if(e.getButton() == MouseEvent.BUTTON3){
                switch (GameState.gameState) {
                case EDIT:
                    game.getEditing().leftClick(e);
                    break;
                default:
                    break;
            }
        }
        
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (GameState.gameState) {
            case MENU:
                game.getMenu().mouseDragged(e.getX(),e.getY());
                break;
            case PLAYING:
                game.getPlaying().mouseDragged(e.getX(),e.getY());
                break;
            case SETTINGS:
                game.getSettings().mouseDragged(e.getX(),e.getY());
                break;
            case EDIT:
                game.getEditing().mouseDragged(e.getX(), e.getY());
                break;
            case GAME_OVER:
                game.getGameOver().mouseDragged(e.getX(), e.getY());
                break;
            default:
                break;
        }
        
        
        
    }

    @Override
    public void mouseMoved(java.awt.event.MouseEvent e) {
        switch (GameState.gameState) {
            case MENU:
                game.getMenu().mouseMoved(e.getX(),e.getY());
                break;
            case PLAYING:
                game.getPlaying().mouseMoved(e.getX(),e.getY());
                break;
            case SETTINGS:
                game.getSettings().mouseMoved(e.getX(),e.getY());
                break;
            case EDIT:
                game.getEditing().mouseMoved(e.getX(), e.getY());
                break;
            case GAME_OVER:
                game.getGameOver().mouseMoved(e.getX(), e.getY());
                break;
            default:
                break;
        }
        
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        switch (GameState.gameState) {
            case MENU:
                game.getMenu().mousePressed(e.getX(),e.getY());
                break;
            case PLAYING:
                game.getPlaying().mousePressed(e.getX(),e.getY());
                break;
            case SETTINGS:
                game.getSettings().mousePressed(e.getX(),e.getY());
                break;
            case EDIT:
                game.getEditing().mousePressed(e.getX(), e.getY());
                break;
            case GAME_OVER:
                game.getGameOver().mousePressed(e.getX(), e.getY());
                break;
            default:
                break;
        }
        
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        switch (GameState.gameState) {
            case MENU:
                game.getMenu().mouseReleased(e.getX(),e.getY());
                break;
            case PLAYING:
                game.getPlaying().mouseReleased(e.getX(),e.getY());
                break;
            case SETTINGS:
                game.getSettings().mouseReleased(e.getX(),e.getY());
                break;
            case EDIT:
                game.getEditing().mouseReleased(e.getX(), e.getY());
                break;
            case GAME_OVER:
                game.getGameOver().mouseReleased(e.getX(), e.getY());
                break;
            default:
                break;
        }
        
    }
}

