package ui;

import static Save.Constants.Towers.*;
import static main.GameState.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

import Save.Constants.Towers;
import Tower.APokemon;
import Tower.Bulbasaur;
import Tower.Charmender;
import Tower.Squirtle;
import sequence.Playing;

public class ActionBar extends Bar{
    
    private Playing playing;
    private Button buttonM, buttonP;
    private APokemon selectedPokemon, displayedPokemon;
    private Button[] pokButtons;
    private DecimalFormat formatter;
    private Button sellTower, upgradeTower, evolButton;
    private int pokeCoins = 100;
    private boolean showTowerCost;
    private int towerCostType;

    private int lives = 1;


    public ActionBar(int x, int y, int width, int height, Playing playing){
        super(x, y, width, height);
        this.playing = playing;
        formatter = new DecimalFormat("0.0");

        initButton();
    }

    

    private void initButton(){
        buttonM = new Button("Menu", 2, 642, 100, 30);
        buttonP  = new Button("Pause", 2, 682, 100, 30);

        pokButtons = new Button[3];

        int w = 50;
        int h = 50;
        int xStart = 130;
        int yStart = 650;
        int xOffset = (int)(w*1.1f);
    
        for (int i = 0; i<3; i++){
            pokButtons[i] = new Button("", xStart+xOffset*i, yStart, w, h, i);
        }

        sellTower = new Button("Sell", 420, 720, 50, 35);
    
        upgradeTower = new Button("Updrade", 500, 720, 70, 35);
        evolButton = new Button("SHINKA", 500, 720, 70, 35);
        
    }

    public void removeOneLife() {
        lives--;
        if(lives <= 0)
            SetGameState(GAME_OVER);
    }

    public void draw(Graphics g){
        g.setColor(new Color(220,123,15));
        g.fillRect(x, y, width, height);


        drawButton(g);

        drawDisplayedPokemon(g);
        drawWaveInfo(g);
        drawWavesLeftInfo(g);
        drawEnemiesLeftInfo(g);

        drawGoldAmount(g);
        if (showTowerCost){
            drawTowerCost(g);
        }
        
        drawWaveTimerInfo(g);

        g.setColor(Color.black);
        g.drawString("Lives: " + lives, 110, 750);
    }

    private void drawTowerCost(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(290, 650, 120, 50);
        g.setColor(Color.black);
        g.drawRect(290, 650, 120, 50);

        g.drawString("" + getTowerCostName(), 295, 670);
        g.drawString("Cost: " + getTowerCostCost(), 295, 695);

        if (isTowerCostMoreThanCurrentGold()){
            g.setColor(Color.RED);
            g.drawString("Can't Afford", 295, 725);
        }
    
    }

	private boolean isTowerCostMoreThanCurrentGold() {
        return getTowerCostCost() > pokeCoins;
    }

    private int getTowerCostCost() {
        return Save.Constants.Towers.GetTowerCost(towerCostType);
    }

    private String getTowerCostName() {
        return Save.Constants.Towers.GetName(towerCostType);
    }

    private void drawWaveTimerInfo(Graphics g) {
		if (playing.getWaveController().isWaveTimerStart()) {

			float timeLeft = playing.getWaveController().getTimeLeft();
			String formattedText = formatter.format(timeLeft);
			g.drawString("Time Left: " + formattedText, 425, 750);
		}
	}

    private void drawGoldAmount(Graphics g) {
        g.setFont(new Font("LucidaSans",Font.BOLD,11));
        g.drawString("PokeCoins : " + pokeCoins, 110, 725);
    }

    private void drawWaveInfo(Graphics g) {
		if (playing.getWaveController().isWaveTimerStart()){
            g.setFont(new Font("LucidaSans",Font.BOLD,20));
            g.setColor(Color.black);
            float timeLeft = playing.getWaveController().getTimeLeft();
            String formatedText = formatter.format(timeLeft);
            g.drawString("Time Left: " + formatedText, 450, 660);
        }
	}

    private void drawWavesLeftInfo(Graphics g) {
		int current = playing.getWaveController().getWaveIndex();
		int size = playing.getWaveController().getWaves().size();
		g.drawString("Wave " + (current + 1) + " / " + size, 425, 770);

	}

    private void drawEnemiesLeftInfo(Graphics g) {
		int remaining = playing.getMonsterControler().getAmountOfAliveEnemies();
		g.drawString("Enemies Left: " + remaining, 425, 790);
	}

    private void drawButton(Graphics g){
        buttonM.draw(g);
        buttonP.draw(g);
        
        for(Button b : pokButtons){
            g.setColor(Color.white);
            g.fillRect(b.x, b.y, b.width, b.height);
            g.setColor(Color.red);
            g.fillRect(b.x, b.y, b.width, b.height/2);
            g.drawImage(playing.getTowerControler().getPokemonsImgs()[b.getId()], b.x, b.y, b.width, b.height,null);
            drawFeedbackButton(g, b);
        }
    }

    public void displayPokmon(APokemon t) {
        displayedPokemon = t;
    }

    private void drawDisplayedPokemon(Graphics g) {
        if (displayedPokemon != null){
            g.setColor(Color.gray);
            g.fillRect(410, 645, 220, 85);
            g.setColor(Color.black);
            g.drawRect(410, 645, 220, 85);
            g.drawImage(playing.getTowerControler().getPokemonsImgs()[displayedPokemon.getTowerType()], 420, 650, 50, 50, null);
            g.setFont(new Font("LucidaSans",Font.BOLD,15));
            g.drawString(""+Towers.GetName(displayedPokemon.getTowerType()), 480, 660);
            g.drawString("Level: "+displayedPokemon.getLevel(), 550, 675);
            g.drawString("ID: "+displayedPokemon.getId(), 480, 675);

            drawSelectedPokemonBorder(g);
            drawPokemonRange(g);

            sellTower.draw(g);
            drawFeedbackButton(g, sellTower);

            if(sellTower.isMouseOver()){
                g.setColor(Color.red);
                g.drawString("Sell: "+getSellAmount(displayedPokemon) + "Pcoins", 490, 710);
            }else if (upgradeTower.isMouseOver()){
                g.setColor(Color.blue);
                g.drawString("Upgrade: " + getlevelUpAmount(displayedPokemon) + "Pcoins", 490, 710);
            }else if (evolButton.isMouseOver() ){
                g.setColor(Color.blue);
                g.drawString("Evolution: " + getEvolutionAmounts(displayedPokemon) + "Pcoins", 490, 710);
            }

            
            if(displayedPokemon.getLevel() <3){
                upgradeTower.draw(g);
                drawFeedbackButton(g, upgradeTower);
            }else{
                if(displayedPokemon.getTowerType() == CHARIZARD || displayedPokemon.getTowerType() == VENUSAUR || displayedPokemon.getTowerType() == BLASTOISE){
                    
                }else{
                    evolButton.draw(g);
                    drawFeedbackButton(g, evolButton);
                    
                }
            }
        }

    }

    private int getEvolutionAmounts(APokemon displayedPokemon2) {
        return (int) (Save.Constants.Towers.GetTowerCost(displayedPokemon.getTowerType())* 1.2f);
    }



    private int getlevelUpAmount(APokemon displayedPokemon2) {
        return (int) (Save.Constants.Towers.GetTowerCost(displayedPokemon.getTowerType()) * 0.3f);
    }



    private int getSellAmount(APokemon displayedPokemon2) {
        int upgradeCost = (displayedPokemon.getLevel() - 1) * getlevelUpAmount(displayedPokemon2);
        upgradeCost *= 0.5f;

        return (int) (Save.Constants.Towers.GetTowerCost(displayedPokemon2.getTowerType()) + upgradeCost);
    }



    private void drawPokemonRange(Graphics g){
        g.setColor(Color.white);
        g.drawOval(displayedPokemon.getX()+ 16-(int)(displayedPokemon.getRange() *2) /2,
                    displayedPokemon.getY()+16-(int)(displayedPokemon.getRange() * 2) / 2, (int)displayedPokemon.getRange()*2, (int)displayedPokemon.getRange()*2);
    }

    private void drawSelectedPokemonBorder(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect(displayedPokemon.getX(), displayedPokemon.getY(), 32,32);
    }

    private void levelUpPokemonClicked() {
        if(pokeCoins >= getlevelUpAmount(displayedPokemon)){
            playing.lvlUpPokemon(displayedPokemon);
            pokeCoins -= getlevelUpAmount(displayedPokemon);
        }
        
    }

    private void evolPokemonClicked() {
        if (pokeCoins >= getEvolutionAmounts(displayedPokemon)) {
            
            pokeCoins = pokeCoins - getEvolutionAmounts(displayedPokemon);
            playing.evolPokemon(displayedPokemon);
        }
        
    }

    private void togglePause() {
        playing.setGamePaused(!playing.isGamePaused());

        if(playing.isGamePaused()) {
            buttonP.setText("Unpause");
        }
        else {
            buttonP.setText("Pause");
        }
    }

    private void sellTowerClicked() {
        playing.removeTower(displayedPokemon);
        pokeCoins += getSellAmount(displayedPokemon);
        
        
        int upgradeCost = (displayedPokemon.getLevel() - 1) * getlevelUpAmount(displayedPokemon);
        upgradeCost *= 0.5f;
        pokeCoins += upgradeCost;

        
        displayedPokemon = null;
    }




    //MouseEvent
    public void mouseClicked(int x, int y) {
        if(buttonM.getRectangle().contains(x,y)){
            SetGameState(MENU);
        }else if(buttonP.getRectangle().contains(x,y)){
            togglePause();
        }else{

            if (displayedPokemon != null){
                if(sellTower.getRectangle().contains(x, y)){
                    sellTowerClicked();
                    return;
                }else if (upgradeTower.getRectangle().contains(x, y) && displayedPokemon.getLevel() < 3){
                    levelUpPokemonClicked();
                    return;
                }else if(evolButton.getRectangle().contains(x,y)){
                    evolPokemonClicked();
                    return;
                }
                    
                return;
                
            }
            for (Button b : pokButtons){
                if(b.getRectangle().contains(x, y)){

                    if (!isGoldEnoughForTower(b.getId())){
                        return;
                    }

                    if(b == pokButtons [0]){
                        selectedPokemon = new Bulbasaur(0, 0, -1, BULBASAUR);
                        playing.setSelectedPokemon(selectedPokemon);
                        return;
                    }else if(b == pokButtons[1]){
                        selectedPokemon = new Charmender(0, 0, -1, CHARMENDER);
                        playing.setSelectedPokemon(selectedPokemon);
                        return;
                    }else if(b == pokButtons[2]){
                        selectedPokemon = new Squirtle(0, 0, -1, SQUIRTLE);
                        playing.setSelectedPokemon(selectedPokemon);
                        return;
                    }
                }
            }
        }
    }
    





    private boolean isGoldEnoughForTower(int id) {
        return pokeCoins >= Save.Constants.Towers.GetTowerCost(id);
    }



    



    public void mouseMoved(int x, int y) {
        buttonM.setMouseOver(false);
        showTowerCost = false;
        sellTower.setMouseOver(false);
        upgradeTower.setMouseOver(false);
        evolButton.setMouseOver(false);
        buttonP.setMouseOver(false);
        for (Button b : pokButtons){
            b.setMouseOver(false);
        }
        if(buttonM.getRectangle().contains(x,y)){
            buttonM.setMouseOver(true);
        }else if(buttonP.getRectangle().contains(x,y)){
            buttonP.setMouseOver(true);
        }else{
            if(displayedPokemon != null){
                if(sellTower.getRectangle().contains(x,y)){
                    sellTower.setMouseOver(true);
                    return;
                }else if(upgradeTower.getRectangle().contains(x, y) && displayedPokemon.getLevel() < 3){
                    upgradeTower.setMouseOver(true);
                    return;
                }else if(evolButton.getRectangle().contains(x, y)){
                    evolButton.setMouseOver(true);
                    return;
                }
            }
            for (Button b : pokButtons){
                if(b.getRectangle().contains(x, y)){
                    b.setMouseOver(true);
                    towerCostType = b.getId();
                    showTowerCost = true;
                    return;
                }
            }
        }
    }
    public void mousePressed(int x, int y) {
        if(buttonM.getRectangle().contains(x,y)){
            buttonM.setMousePressed(true);
        }else if(buttonP.getRectangle().contains(x,y)){
            buttonP.setMousePressed(true);
        }else{
            if(displayedPokemon != null){
                if(sellTower.getRectangle().contains(x, y)){
                    sellTower.setMousePressed(true);
                    return;
                }else if (upgradeTower.getRectangle().contains(x, y) && displayedPokemon.getLevel() < 3){
                    upgradeTower.setMousePressed(true);
                    return;
                }else if (evolButton.getRectangle().contains(x, y)){
                    evolButton.setMousePressed(true);
                    return;
                }
            }
            for (Button b : pokButtons){
                if(b.getRectangle().contains(x,y)){
                    b.setMousePressed(true);
                }

        }
        
        }
    }
    public void mouseReleased(int x, int y) {
        buttonM.resetBooleans();
        buttonP.resetBooleans();
        for (Button b : pokButtons){
                b.resetBooleans();
            }
        sellTower.resetBooleans();
        upgradeTower.resetBooleans();
        evolButton.resetBooleans();
    }

    //getter
    public Playing getPlaying() {
        return playing;
    }

    public void payForTower(int towerType) {
        this.pokeCoins -= Save.Constants.Towers.GetTowerCost(towerType);
    }



    public void addCoin(int getReward) {
        this.pokeCoins += getReward;
    }

    public int getLives() {
        return lives;
    }



    public void resetEverything() {
        lives = 25;
        towerCostType = 0;
        showTowerCost = false;
        pokeCoins = 100;
    }

}
