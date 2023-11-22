package Tower;

//Merci TELEMET pour les sprites

public abstract class APokemon implements IPokemon{
    protected int x, y, id, towerType;
    protected int damage;
    protected float range,cooldown;
    private int cdTick;
    protected int level;

    public APokemon(int x, int y, int id, int towerType){
        this.x = x;
        this.y = y;
        this.id = id;
        this.towerType = towerType;
    }

    


    public void update(){
        cdTick++;
    }


    public abstract void lvlUp();

    public abstract void evolution();

    @Override
    public int getX() {
        return x;
    }
    @Override
    public int getY() {
        return y;
    }
    @Override
    public int getTowerType() {
        return towerType;
    }
    @Override
    public int getId() {
        return id;
    }
    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getTargetNumber() {
        return 1;
    }

    @Override
    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public void setRange(float range) {
        this.range = range;
    }

    @Override
    public float getRange() {
        return range;
    }






    public void resetCooldown() {
        cdTick = 0;
    }






    public boolean isCoolDownOver() {
        return cdTick >= cooldown;
    }




    
}
