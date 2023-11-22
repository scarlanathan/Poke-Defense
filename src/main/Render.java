package main;

import java.awt.Graphics;

public class Render {
    private Game game;
    public Render(Game g){
        this.game = g;
        
    }

    public void render(Graphics g){
        switch (GameState.gameState) {
            case MENU:
                game.getMenu().render(g);
                break;
            case PLAYING:
                game.getPlaying().render(g);
                break;
            case SETTINGS:
                game.getSettings().render(g);
                break;
            case EDIT:
                game.getEditing().render(g);
                break;
            case GAME_OVER:
                game.getGameOver().render(g);
                break;
            default:
                break;
        }
    }
}
