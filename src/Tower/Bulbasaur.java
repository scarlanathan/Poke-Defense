package Tower;

import static Save.Constants.Towers.BULBASAUR;
import static Save.Constants.Towers.IVYSAUR;
import static Save.Constants.Towers.VENUSAUR;


public class Bulbasaur extends APokemon {
    public Bulbasaur(int x, int y, int id, int towerType){
        super(x, y, id, BULBASAUR);
        super.damage = 5;
        super.range = 100;
        super.level = 1;
        super.range = 120f;
        super.cooldown = 25f;
    }

    @Override
    public void lvlUp(){
        this.level++;

        switch (towerType) {
                case BULBASAUR:
                    damage += 2;
                    range += 20;
                    cooldown -= 5;
                    break;
                case IVYSAUR:
                    damage += 2;
                    range += 20;
                    cooldown -= 5;
                    break;
                case VENUSAUR:
                    damage += 2;
                    range += 20;
                    cooldown -= 5;
                    break;
            }
    }

    @Override
    public void evolution() {
        switch (towerType) {
            case BULBASAUR:
                towerType = IVYSAUR;
                damage += 3;
                range += 20;
                cooldown -= 5;
                level = 1;
                break;
            case IVYSAUR:
                towerType = VENUSAUR;
                damage += 4;
                range += 20;
                cooldown -= 5;
                level = 1;
                break;
            }
    }
}


