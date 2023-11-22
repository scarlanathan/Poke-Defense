package Tower;

import static Save.Constants.Towers.CHARIZARD;
import static Save.Constants.Towers.CHARMELEON;
import static Save.Constants.Towers.CHARMENDER;

public class Charmender extends APokemon {
    public Charmender(int x, int y, int id, int towerType){
        super(x, y, id, towerType);
        super.damage = 25;
        super.range = 100;
        super.level = 1;
        super.range = 150f;
        super.cooldown = 120f;
    }

    @Override
    public void lvlUp(){
        this.level++;

        switch (towerType) {
            case CHARMENDER:
                damage += 5;
                range += 20;
                cooldown -= 15;
                break;
            case CHARMELEON :
                damage += 2;
                range += 20;
                cooldown -= 5;
                break;
            case CHARIZARD : 
                damage += 2;
                range += 20;
                cooldown -= 5;
                break;
            }
    }

    @Override
    public void evolution() {
        switch (towerType) {
            case CHARMENDER:
                towerType = CHARMELEON;
                damage += 6;
                range += 20;
                cooldown -= 25;
                level = 1;
                break;
            case CHARMELEON :
                towerType = CHARIZARD;
                damage += 2;
                range += 20;
                cooldown -= 5;
                level = 1;
                break;
            }
    }
}
