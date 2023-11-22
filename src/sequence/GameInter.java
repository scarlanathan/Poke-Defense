package sequence;

import java.awt.image.BufferedImage;

import main.Game;

public class GameInter { // Game interaction different sequence in the game

    protected Game game;
    protected int ANIMATION_SPEED = 10;
    protected int animation,temps;


    public GameInter(Game game){
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    protected void updateTime() {
        temps++;
        if(temps >= ANIMATION_SPEED){
            temps = 0;
            animation++;
            if(animation >=4){
                animation = 0;
            }
        }
    }

    protected boolean isAnimation(int id){
        return game.getMaterielsControler().isSpriteAnimation(id);
    }

    protected BufferedImage getSprite(int spriteID){
        return game.getMaterielsControler().getSprite(spriteID);
    }

    protected BufferedImage getSprite(int spriteID,int animation){
        return game.getMaterielsControler().getAniSprite(spriteID,animation);
    }

    
}
