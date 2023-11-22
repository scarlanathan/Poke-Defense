package main;

public enum GameState {

    PLAYING,MENU,SETTINGS,EDIT,GAME_OVER;

    public static GameState gameState = MENU;

    public static void SetGameState(GameState state){
        gameState = state;
    }

}
