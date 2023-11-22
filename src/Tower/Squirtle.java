package Tower;

import static Save.Constants.Towers.SQUIRTLE;
import static Save.Constants.Towers.WARTORTLE;
import static Save.Constants.Towers.BLASTOISE;


public class Squirtle extends APokemon {
    public Squirtle(int x, int y, int id, int towerType){
        super(x, y, id, SQUIRTLE);
        super.damage = 1;
        super.range = 100;
        super.level = 1;
        super.range = 100f;
        super.cooldown = 40f;
    }


    @Override
    public void lvlUp(){
        this.level++;

        switch (towerType) {
                case SQUIRTLE:
                    damage += 3;
                    range += 20;
                    cooldown -= 5;
                    break;
                case WARTORTLE:
                    damage += 2;
                    range += 20;
                    cooldown -= 5;
                    break;
                case BLASTOISE:
                    damage += 2;
                    range += 20;
                    cooldown -= 5;
                    break;
            }
    }

    @Override
    public void evolution() {
        switch (towerType) {
            case SQUIRTLE:
                towerType = WARTORTLE;
                damage += 3;
                range += 20;
                cooldown -= 5;
                level = 1;
                break;
            case WARTORTLE:
                towerType = BLASTOISE;
                damage += 2;
                range += 20;
                cooldown -= 5;
                level = 1;
                break;
            }
    }
}

