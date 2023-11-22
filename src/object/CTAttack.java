package object;

import java.awt.geom.Point2D;


public class CTAttack {
    private Point2D.Float pos;
    private int id, CTtype, damage;
    private float xSpeed, ySpeed, rotation;
    private boolean active = true;

    public CTAttack(float x, float y, float xSpeed,float ySpeed, int damage,float rotation, int id, int CType){
        pos = new Point2D.Float(x,y);
        this.id = id;
        this.CTtype = CType;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.rotation = rotation;
        this.damage = damage;
    }

    public void move(){
        pos.x += xSpeed;
        pos.y += ySpeed;
    }
    public int getCTtype() {
        return CTtype;
    }
    public int getId() {
        return id;
    }
    public Point2D.Float getPos() {
        return pos;
    }
    public void setPos(Point2D.Float pos) {
        this.pos = pos;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public int getDamage() {
        return damage;
    }
    public float getRotation() {
        return rotation;
    }
}
