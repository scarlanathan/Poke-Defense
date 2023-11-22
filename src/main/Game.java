package main;

import javax.swing.JFrame;

import Controler.MaterielsControler;
import Save.Music;
import sequence.MapEditing;
import sequence.Menu;
import sequence.Playing;
import sequence.Settings;
import sequence.GameOver;

public class Game extends JFrame{
    private GameScreen gs;

    private Render render;
    private Menu menu;
    private Playing playing;
    private Settings settings;
    private MapEditing editing;
    private MaterielsControler materielsControler;
    private GameOver gameOver;
    private Music son;
    
    public Game(){

        materielsControler = new MaterielsControler();
        render = new Render(this);
        gs = new GameScreen(this);
        menu = new Menu(this);
        playing = new Playing(this);
        settings = new Settings(this);
        editing = new MapEditing(this);
        gameOver = new GameOver(this);
        son = new Music("src/Image/Surf.wav");
        
        son.start();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        add(gs);
        pack(); //to have the right dimension because there was some sprint hiding
        
        setLocationRelativeTo(null);
        setVisible(true);//be visible

    }

    private void GameUpdate(){
        switch (GameState.gameState) {
            case EDIT:
                editing.update();
                break;
            case MENU:
                break;
            case PLAYING:
                playing.update();
                break;
            case SETTINGS:
                break;
            default:
                break;
        }
    }
    

    
    //make a gameloop to run the game consistently and with the same speed
    public void runGame(){
        int frame = 0;
        double timePerFrame = 1000000000.0 / 120.0; //Pour avoir 120 FPS pour chaque utilisateur
        long lastFrame = System.nanoTime();
        long lastTimeFrame = System.currentTimeMillis();

        int updates = 0;
        double timePerUpdate = 1000000000.0 / 60.0;  //UPS moves objects checks for events check for collisions must be stable constant. The player must not change this one 
        long lastUPS = System.nanoTime() ;

        long NOW;
        while(true){

            NOW = System.nanoTime();
            
            //Update
            if(NOW - lastUPS >= timePerUpdate){
                GameUpdate();
                updates++ ;
                lastUPS = NOW;
            }
            if(NOW - lastFrame >= timePerFrame){
                lastFrame = NOW;
                repaint();        //repaint() call paintComponenet all the time
                frame++;
            }
             //FPS
            if(System.currentTimeMillis() - lastTimeFrame >=1000){
                System.out.println("FPS: "+frame +" | UPS: "+ updates);
                frame = 0;
                updates = 0;
                lastTimeFrame = System.currentTimeMillis();
            }
            
        }

    }

    //Getter and setter
    public Render getRender() {
        return render;
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public Settings getSettings() {
        return settings;
    }

    public MapEditing getEditing() {
        return editing;
    }

    public GameOver getGameOver() {
        return gameOver;
    }

    public MaterielsControler getMaterielsControler() {
        return materielsControler;
    }



    public static void main(String[] args) {
        Game game = new Game();
        game.gs.inputs();
        game.runGame();
        
    }

}
