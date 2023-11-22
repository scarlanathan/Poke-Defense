package Save;

public class Constants {

    public static class CT {
        public static final int FEUILLE = 2;
        public static final int FEU = 0;
        public static final int EAU = 1;
        public static final int FEUU = 3;
        public static final int FEUUU = 4;
        public static final int EAUU = 5;
        public static final int EAUUU = 6;
        public static final int FEUILLEE = 7;
        public static final int FEUILLEEE = 8;

        public static float GetSpeed(int CT){
            switch (CT) {
                case FEUILLE:
                    return 8f;
                case FEU:
                    return 4f;
                case EAU:
                    return 6f;
                case EAUU:
                    return 7f;
                case EAUUU:
                    return 8f;
                case FEUU:
                    return 5f;
                case FEUUU:
                    return 6f;
                case FEUILLEE:
                    return 8.5f;
                case FEUILLEEE:
                    return 9f;
                default:
                    return 0;
            }
        }
    }

    public static class Towers{
        public static final int BULBASAUR = 0;
        public static final int CHARMENDER = 1;
        public static final int SQUIRTLE = 2;
        public static final int IVYSAUR = 5;
        public static final int VENUSAUR = 6;
        public static final int CHARMELEON = 3;
        public static final int CHARIZARD = 4;
        public static final int WARTORTLE = 7;
        public static final int BLASTOISE = 8;
        
        public static int GetTowerCost(int towerType) {
			switch (towerType) {
                case BULBASAUR:
                    return 50;
                case SQUIRTLE:
                    return 55;
                case CHARMENDER:
                    return 60;
                case IVYSAUR:
                    return 100;
                case VENUSAUR:
                    return 200;
                case CHARMELEON :
                    return 150;
                case CHARIZARD :
                    return 210;
                case WARTORTLE:
                    return 135;
                case BLASTOISE:
                    return 200;
                default:
                    return 0;
            }
		}

        public static String GetName(int towerType){
            switch (towerType) {
                case BULBASAUR:
                    return "Bulbasaur";
                case SQUIRTLE:
                    return "Squirtle";
                case CHARMENDER:
                    return "Charmender";
                case IVYSAUR:
                    return "Ivysaur";
                case VENUSAUR:
                    return "Venusaur";
                case CHARMELEON :
                    return "Charmeleon";
                case CHARIZARD :
                    return "Charizard";
                case WARTORTLE:
                    return "Wartortle";
                case BLASTOISE:
                    return "Blastoise";
                default:
                    return "";
            }
        }
    }

    public static class Enemies {
        public static final int JAMES = 0;
        public static final int JESSIE = 1;
        public static final int TROCKETVELO = 2;
        public static final int GIOVANNI = 3;

        public static int GetReward(int enemyType){
            switch (enemyType) {
                case JAMES:
                    return 5;
                case JESSIE:
                    return 5;
                case TROCKETVELO:
                    return 10;
                case GIOVANNI:
                    return 25;
                default:
                    return 0;
            }
        }

        public static float GetSpeed(int ennemyType){
            switch (ennemyType) {
                case JAMES:
                    return 0.5f;
                case JESSIE:
                    return 1f;
                case TROCKETVELO:
                    return 1.2f;
                case GIOVANNI:
                    return 0.4f;
                default:
                    return 0;
            }
        }
    }
    
    public static class Direction {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class Tiles {
        public static final int WATER_TILE = 0;
        public static final int GRASS_TILE = 1;
        public static final int ROAD_TILE = 2;
    }

}
