package Monsters;

import static Save.Constants.Direction.*;

import java.awt.Rectangle;

import Controler.MonsterControler;

public abstract class AEnemy {
    protected MonsterControler monsterControler;
    protected int id,health,attackDamage;
    protected int maxHealth;
    protected float x,y; //SPEED float because we have 60 updates per sec so if its int for 1sec it will move 60 times
    protected Rectangle rectangle;
    protected int enemyType;
    protected int lastDir;
    protected boolean alive = true;
    protected int gelTickLimit = 120;
    protected int gelTick = gelTickLimit;

    public AEnemy(int x,int y,int id,int enemyType,MonsterControler monsterControler) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.enemyType = enemyType;
        rectangle = new Rectangle((int)x,(int)y,32,32);
        this.monsterControler = monsterControler;
        lastDir = UP;
    }

    public void slow() {
        gelTick = 0;
    }


    public abstract void move(float speed, int dir);

    public abstract void updateHitbox();



    public void setPos(int x,int y){
        this.x = x;
        this.y = y;
    }

    public void hurt(float dmg){
        this.health -= dmg;
        if(health <= 0){
            alive = false;
            monsterControler.rewardPlayer(enemyType);
        }
    }

    public void kill(){
        alive = false;
        health = 0;
    }



    //getter
    public abstract float getHealthBar();
    public abstract int getEnemyType();
    public abstract int getHealth();
    public abstract float getX();
    public abstract float getY();
    public abstract Rectangle getRectangle();
    public abstract int getId();
    public abstract int getAttackDamage();
    public abstract int getLastDir();
    public abstract void setLastDir(int NewlastDir);
    public abstract boolean isAlive();
    public abstract boolean isSlow();
    
}
