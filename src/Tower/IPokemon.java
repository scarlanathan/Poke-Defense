package Tower;
/**
 * ITower
 */
public interface IPokemon {
    public int getLevel();
    public int getTargetNumber();
    public void setDamage(int damage);
    public int getDamage();
    public void setRange(float range);
    public float getRange();
    public int getX();
    public int getY();
    public int getTowerType();
    public int getId();
}
