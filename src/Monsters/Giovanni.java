package Monsters;

import static Save.Constants.Enemies.*;

import java.awt.Rectangle;

import Controler.MonsterControler;
import static Save.Constants.Direction.*;


public class Giovanni extends AEnemy{

    public Giovanni(int x, int y, int id,MonsterControler monsterControler) {
        super(x, y, id, GIOVANNI,monsterControler);
        super.health = 2000;
        super.maxHealth = 2000;
    }
    @Override
    public void move(float speed, int dir){
        lastDir = dir;
        if(gelTick < gelTickLimit){
            gelTick ++;
            speed *= 0.5f;
        }
        switch (dir) {
            case LEFT:
                this.x -= speed;
                break;
            case UP:
                this.y -= speed;
                break;
            case RIGHT:
                this.x += speed;
                break;
            case DOWN:
                this.y += speed;
                break;
        }
        updateHitbox();
    }
    @Override
    public void updateHitbox() {
        rectangle.x = (int)x;
        rectangle.y = (int)y;
    }

    
    @Override
    public float getHealthBar(){
        return health/(float)maxHealth;
    }
    @Override
    public int getEnemyType() {
        return enemyType;
    }
    @Override
    public int getHealth() {
        return health;
    }
    @Override
    public float getX() {
        return x;
    }
    @Override
    public float getY() {
        return y;
    }
    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }
    @Override
    public int getId() {
        return id;
    }
    @Override
    public int getAttackDamage() {
        return attackDamage;
    }
    @Override
    public int getLastDir() {
        return lastDir;
    }
    @Override
    public void setLastDir(int NewlastDir) {
        this.lastDir = NewlastDir;
    }
    @Override
    public boolean isAlive(){
        return alive;
    }
    @Override
    public boolean isSlow() {
        return gelTick < gelTickLimit;
    }
    
    
}
