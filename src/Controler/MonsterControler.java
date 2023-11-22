package Controler;

import static Save.Constants.Direction.*;
import static Save.Constants.Enemies.*;
import static Save.Constants.Tiles.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Monsters.AEnemy;
import Monsters.Giovanni;
import Monsters.James;
import Monsters.Jessie;
import Monsters.TRocketVelo;
import Save.LoadSave;
import object.PathPoint;
import sequence.Playing;


public class MonsterControler {
    private Playing playing;
    private BufferedImage[] monsterArr;
    private ArrayList<AEnemy> enemies = new ArrayList<>();
    private PathPoint start,end;
    private int HPbar = 20;
    private BufferedImage slowEffect;


    public MonsterControler(Playing playing,PathPoint start,PathPoint end) {
        this.playing = playing;
        monsterArr = new BufferedImage[4];
        this.start = start;
        this.end = end;

        loadEffectImg();
        loadMonsters();
    }

    private void loadEffectImg() {
        slowEffect = LoadSave.getSpritesMinions().getSubimage(32*9, 32*2, 32, 32);
    }

    public void loadMonsters(){
        BufferedImage minions = LoadSave.getSpritesMinions();

        for(int i = 0;i < 4;i++){
            monsterArr[i] = minions.getSubimage(i * 32, 32, 32, 32);
        }

        monsterArr[3] = minions.getSubimage(145,225, 50, 55);
    }


    public void addEnemy(int enemyType){
        int x = start.getxCord() * 32;
        int y = start.getyCord() * 32;
        switch (enemyType) {
            case JAMES:
                enemies.add(new James(x, y, 0, this));
                break;
            case JESSIE:
                enemies.add(new Jessie(x, y, 0, this));
                break;
            case GIOVANNI:
                enemies.add(new Giovanni(x, y, 0, this));
                break;
            case TROCKETVELO:
                enemies.add(new TRocketVelo(x, y, 0, this));
                break;
        }
    }

    public void update(){


        updateWaveController();
        
        if (isTimeForNewEnemy()){
            spawnEnemy();
        }

        for(AEnemy e : enemies){
            if(e.isAlive()){
                updateEnemyMove(e);
            }
        }
    }


    public void updateWaveController(){
        playing.getWaveController().update();
    }

    private void spawnEnemy(){
        addEnemy(playing.getWaveController().getNextEnemy());
    }

    private boolean isTimeForNewEnemy(){
        if (playing.getWaveController().isTimeForNewEnemy()){
            if (playing.getWaveController().isThereMoreEnemiesInWave()){
                return true;
            }
        }

        return false;
    }

    public void updateEnemyMove(AEnemy e){
        if (e.getLastDir() == -1){
            setNewDirectionAndMove(e);
        }

        int newX = (int)(e.getX() + getSpeedAndWidth(e.getLastDir(),e.getEnemyType()));
        int newY = (int)(e.getY() + getSpeedAndHeight(e.getLastDir(),e.getEnemyType()));
        
        if (getTileType(newX,newY) == ROAD_TILE){
            // keep moving in same dir
            e.move(GetSpeed(e.getEnemyType()), e.getLastDir());
        }else if (isAtEnd(e)) {
            e.kill();
            playing.removeOneLife();
        }else{
            // find new direction
            setNewDirectionAndMove(e);
        }
    }

    private boolean isAtEnd(AEnemy e){
        if (e.getX() == end.getxCord() * 32){
            if (e.getY() == end.getyCord() * 32){
                return true;
            }
        }
        return false;
    }

    private void setNewDirectionAndMove(AEnemy e){
        int dir = e.getLastDir();

        int xCord = (int)(e.getX() / 32);
        int yCord = (int)(e.getY() / 32);
        fixEnemyOffsetTile(e,dir,xCord,yCord);
        if (isAtEnd(e)){
            return;
        }

        if (dir == LEFT || dir == RIGHT){
            int newY = (int)(e.getY() + getSpeedAndHeight(UP,e.getEnemyType()));
            if (getTileType((int) e.getX(), newY) == ROAD_TILE){
                e.move(GetSpeed(e.getEnemyType()), UP);
            }else{
                e.move(GetSpeed(e.getEnemyType()), DOWN);
            }
        }else{
            int newX = (int)(e.getX() + getSpeedAndWidth(RIGHT,e.getEnemyType()));
            if (getTileType(newX, (int) e.getY()) == ROAD_TILE){
                e.move(GetSpeed(e.getEnemyType()), RIGHT);
            }else{
                e.move(GetSpeed(e.getEnemyType()), LEFT);
            }
        }
    }

    private void fixEnemyOffsetTile(AEnemy e,int dir,int xCord,int yCord){
        switch (dir) {
            case RIGHT:
                if (xCord < 19){
                    xCord++;
                }
                break;
            case DOWN:
                if (yCord < 19){
                    yCord++;
                }
                break;
        }
        e.setPos(xCord * 32, yCord * 32);
    }

    private int getTileType(int x,int y){
        return playing.getTileType(x,y);
    }

    private float getSpeedAndWidth(int dir, int enemyType){
        if (dir == LEFT){
            return -GetSpeed(enemyType);
        }else if (dir == RIGHT) {
            return GetSpeed(enemyType) + 32;
        }
        return 0;
    }

    private float getSpeedAndHeight(int dir, int enemyType){
        if (dir == UP){
            return -GetSpeed(enemyType);
        }else if (dir == DOWN){
            return GetSpeed(enemyType) + 32;
        }

        return 0;
    }

    public void spawnEnemy(int nextEnemy) {
        addEnemy(nextEnemy);
    }

    public void draw(Graphics g){
        for(AEnemy e : enemies){
            if(e.isAlive()){
                drawEnemy(e, g);
                drawHealthBar(e, g);
                drawEffects(e, g);
            }
        }
    }

    private void drawEffects(AEnemy e, Graphics g) {
        if(e.isSlow()){
            g.drawImage(slowEffect, (int) e.getX(), (int) e.getY(), null);
        }
    }

    private void drawHealthBar(AEnemy e, Graphics g){
        if(HPbar > 10){
            g.setColor(Color.GREEN);
            g.fillRect((int)e.getX() + 16  - (getNewHealthBar(e) / 2) ,(int) e.getY()-10, getNewHealthBar(e), 3);
        }else{
            g.setColor(Color.RED);
            g.fillRect((int)e.getX() + 16  - (getNewHealthBar(e)/2) ,(int) e.getY()-10, getNewHealthBar(e), 3);
        }
    }

    private int getNewHealthBar(AEnemy e){
        return (int) (HPbar *e.getHealthBar());
    }

    private void drawEnemy(AEnemy e, Graphics g) {

        
        if (e.getEnemyType() == GIOVANNI){
            g.drawImage(monsterArr[e.getEnemyType()], (int)e.getX(), (int)e.getY() - 25, null);
        }else{
            g.drawImage(monsterArr[e.getEnemyType()], (int)e.getX(), (int)e.getY(), null);
        }

    }

    public Playing getPlaying() {
        return playing;
    }

    public ArrayList<AEnemy> getEnemies() {
        return enemies;
    }

    public int getAmountOfAliveEnemies() {
		int size = 0;
		for (AEnemy e : enemies)
			if (e.isAlive())
				size++;

		return size;
	}

    public void rewardPlayer(int enemyType) {
        playing.rewardPlayer(enemyType);
    }

    public void reset() {
        enemies.clear();
    }

    public PathPoint getEnd() {
        return end;
    }
    public PathPoint getStart() {
        return start;
    }
}
