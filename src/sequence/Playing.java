package sequence;

import static Save.Constants.Tiles.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Controler.CTControler;
import Controler.MonsterControler;
import Controler.TowerControler;
import Controler.WaveController;
import Monsters.AEnemy;
import Save.LoadSave;
import Tower.APokemon;
import main.Game;
import object.PathPoint;
import ui.ActionBar;

public class Playing extends GameInter implements ISequence{

    private int[][] lvl;

    private int mouseX, mouseY;
    private ActionBar bottomBar;
    private MonsterControler monsterControler;
    private TowerControler towerControler;
    private CTControler ctControler;
    private Boolean gamePaused = false;

    private PathPoint start,end;
    private APokemon selectedPokemon;
    private WaveController waveController;
    private int goldTick;


    
    public Playing(Game game){
        super(game);
        loadDefaultlvl();
        bottomBar = new ActionBar(0, 640, 640, 160, this);
        monsterControler = new MonsterControler(this,start,end);
        towerControler = new TowerControler(this);
        ctControler = new CTControler(this);
        
        waveController = new WaveController(this);
    }


    public void update(){
        if(!gamePaused){
            updateTime();
            waveController.update();
            goldTick++;
			if (goldTick % (60 * 3) == 0){
                bottomBar.addCoin(1);
            }

            if (isAllEnemiesDead()){
                if (isThereMoreWaves()){
                    waveController.startWaveTimer();
                    if (isWaveTimerOver()){
                        waveController.increaseWaveIndex();
                        monsterControler.getEnemies().clear();
                        waveController.resetEnemyIndex();
                    }
                }
            }
            if (isTimeForNewEnemy()){
                if (!waveController.isWaveTimerOver()){
                    spawnEnemy();
                }
            }

            monsterControler.update();
            towerControler.update();
            ctControler.update();
        }


        
    }

    private boolean isWaveTimerOver() {
        return waveController.isWaveTimerOver();
    }


    private boolean isThereMoreWaves() {
        return waveController.isThereMoreWaves();
    }


    private boolean isAllEnemiesDead() {

        if (waveController.isThereMoreEnemiesInWave()){
            return false;
        }

        for (AEnemy e : monsterControler.getEnemies()){
            if (e.isAlive()){
                return false;
            }
        }
        return true;
    }


    private void spawnEnemy(){
        monsterControler.spawnEnemy(waveController.getNextEnemy());
        // addEnemy(getWaveController().getNextEnemy());
    }

    private boolean isTimeForNewEnemy(){
        if (getWaveController().isTimeForNewEnemy()){
            if (getWaveController().isThereMoreEnemiesInWave()){
                return true;
            }
        }

        return false;
    }

    public boolean isGamePaused() {
        return gamePaused;
    }


    @Override
    public void render(Graphics g){
        drawlevel(g);
        bottomBar.draw(g);
        towerControler.draw(g);
        monsterControler.draw(g);
        ctControler.draw(g);
        drawselectedPokemon(g);
        drawHighlighted(g);
        
        drawWaveInfos(g);
    }


    private void drawWaveInfos(Graphics g) {
        
    }


    private void drawHighlighted(Graphics g) {
        g.setColor(Color.white);
        g.drawRect(mouseX, mouseY, 32,32);
    }


    public void setSelectedPokemon(APokemon selectedPokemon) {
        this.selectedPokemon = selectedPokemon;
    }

    public void saveLevel(){
        LoadSave.saveLevel("new_lvl", lvl,null,null); // change null to start and end
    }

    private void loadDefaultlvl() {
        lvl = LoadSave.GetLvlData("new_lvl");
        ArrayList<PathPoint> points = LoadSave.GetLevelPathPoints("new_lvl");
        start = points.get(0);
        end = points.get(1);
    }

    private void drawselectedPokemon(Graphics g) {
        if (selectedPokemon != null){
            g.drawImage(towerControler.getPokemonsImgs()[selectedPokemon.getTowerType()], mouseX, mouseY, null);
        }
    
    }
        

    private void drawlevel(Graphics g){
        for (int y = 0; y<lvl.length; y++){
            for (int x = 0; x<lvl[y].length; x++){
                int id = lvl[y][x];
                if(isAnimation(id)){
                    g.drawImage(getSprite(id,animation), x*32, y*32, null);
                }else{
                    g.drawImage(getSprite(id), x*32, y*32, null);
                }
            }
        }
    }

    public void lvlUpPokemon(APokemon displayedPokemon) {
        towerControler.lvlUpPokemon(displayedPokemon);
    }

    public void evolPokemon(APokemon displayedPokemon) {
        towerControler.evolPokemon(displayedPokemon);
    }

    public void setGamePaused(boolean gamePaused) {
        this.gamePaused=gamePaused;
    }

    public void removeTower(APokemon displayedPokemon) {
        towerControler.removeTower(displayedPokemon);
    }


    private boolean isTileGrass(int x, int y) {
        int id = lvl[y / 32][x / 32];
        int tileType = game.getMaterielsControler().getMaterial(id).getTileType();
        return tileType == GRASS_TILE;
    }

    public void shootEnemy(APokemon p, AEnemy e) {
        ctControler.newCT(p,e);
    }

    public int getTileType(int x,int y){
        int xCord = x / 32;
        int yCord = y / 32;
        if (xCord < 0 || xCord > 19){
            return 0;
        }

        if (yCord < 0 || yCord > 19){
            return 0;
        }

        int id = lvl[y / 32][x / 32];
        return game.getMaterielsControler().getTile(id).getTileType();
    }

    public void setLvl(int[][] lvl){
        this.lvl = lvl;
    }

    public void mouseClicked(int x, int y) {
        if (y>=640){
            bottomBar.mouseClicked(x, y);
        }else{
            if (selectedPokemon != null){
                if(isTileGrass(mouseX,mouseY)){
                    if(getTowerPosition(mouseX,mouseY) == null){
                        towerControler.addTower(selectedPokemon,mouseX,mouseY);

                        removeCoins(selectedPokemon.getTowerType());

                        selectedPokemon = null;
                    }
                }
            }else{//
                APokemon t = getTowerPosition(mouseX,mouseY);
                bottomBar.displayPokmon(t);
            }
        }
    }

    public void removeCoins(int towerType) {
        bottomBar.payForTower(towerType);
    }


    private APokemon getTowerPosition(int x, int y) {
        return towerControler.getTowerPosition(x,y);
    }


    @Override
    public void mouseMoved(int x, int y) {
        if(y>=640){
            bottomBar.mouseMoved(x, y);
        }else{
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if(y>=640){
            bottomBar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        bottomBar.mouseReleased(x, y);
    }

    @Override
    public void mouseDragged(int x, int y) {
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_S){
            selectedPokemon = null;
        }
    }


    public TowerControler getTowerControler() {
        return towerControler;
    }
    public MonsterControler getMonsterControler() {
        return monsterControler;
    }
    public int getMouseX() {
        return mouseX;
    }
    public int getMouseY() {
        return mouseY;
    }

    public WaveController getWaveController(){
        return waveController;
    }

    public void rewardPlayer(int enemyType){
        bottomBar.addCoin(Save.Constants.Enemies.GetReward(enemyType));
    }


    
    
    public void removeOneLife() {
        bottomBar.removeOneLife();
    }

    public void resetEverything() {

        bottomBar.resetEverything();

        //Controlers
        monsterControler.reset();
        towerControler.reset();
        ctControler.reset();
        waveController.reset();

        mouseX = 0;
        mouseY = 0;

        selectedPokemon=null;
        goldTick = 0;
        gamePaused = false;
    }
}
