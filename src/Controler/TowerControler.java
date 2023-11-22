package Controler;

import static Save.Constants.Towers.BULBASAUR;
import static Save.Constants.Towers.CHARMENDER;
import static Save.Constants.Towers.SQUIRTLE;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Monsters.AEnemy;
import Save.LoadSave;
import Tower.APokemon;
import Tower.Bulbasaur;
import Tower.Charmender;
import Tower.Squirtle;
import sequence.Playing;

public class TowerControler {
    private Playing playing;
    private BufferedImage[] pokemonImgs;
    private ArrayList<APokemon> pokemons = new ArrayList<>();
    private int Amount = 0;

    public TowerControler(Playing playing){
        this.playing = playing;

        loadTower();
    }

    public void update(){
        for (APokemon p : pokemons){
            p.update();
            attack(p);
        }
    }

    private void attack(APokemon p) {
        for(AEnemy e : playing.getMonsterControler().getEnemies()){
            if(e.isAlive())
                if(isEnemyInRange(p,e)){
                    if(p.isCoolDownOver()){
                        playing.shootEnemy(p, e);
                        p.resetCooldown();
                    }

                }else{
                    //nothing
                }
        }
    }

    public void removeTower(APokemon displayedPokemon) {
        for(int i = 0; i < pokemons.size(); i++){
            if(pokemons.get(i).getId() == displayedPokemon.getId()){
                pokemons.remove(i);
            }
        }
    }

    public void lvlUpPokemon(APokemon displayedPokemon) {
        for (APokemon p : pokemons){
            if(p.getId() == displayedPokemon.getId()){
                p.lvlUp();
            }
        }
    }

     public void evolPokemon(APokemon displayedPokemon) {
        for (APokemon p : pokemons){
            if(p.getId() == displayedPokemon.getId()){
                p.evolution();
            }
        }
    }

    private boolean isEnemyInRange(APokemon p , AEnemy e){
        int range = Save.Utility.GetHypotenus(p.getX(), p.getY(), e.getX(), e.getY());

        return range < p.getRange();
    }
    private void loadTower() {
        BufferedImage minions = LoadSave.getSpritesMinions();
        pokemonImgs = new BufferedImage[9];
        for (int i = 0; i < 3; i++){
            pokemonImgs[i] = minions.getSubimage((4 + i) * 32, 32, 32, 32);
        }
        pokemonImgs[3] = minions.getSubimage( 7* 32, 3*32, 32, 32); //reptincel
        pokemonImgs[4] = minions.getSubimage( 9* 32, 5*32, 32, 32); //Dracaufeu
        pokemonImgs[5] = minions.getSubimage( 5* 32, 4*32, 32, 32); //Herbizarre
        pokemonImgs[6] = minions.getSubimage( 7* 32, 4*32, 32, 32); //Florizarre
        pokemonImgs[7] = minions.getSubimage( 7* 32, 5*32, 32, 32); //Carabaffe
        pokemonImgs[8] = minions.getSubimage( 7* 32, 6*32, 32, 32); //Tortank
    }

    public void draw(Graphics g){
        for (APokemon t : pokemons){
            g.drawImage(pokemonImgs[t.getTowerType()], t.getX(), t.getY(), null);
        }
    }

    public APokemon getTowerPosition(int x, int y) {
        for(APokemon t : pokemons){
            if(t.getX() == x){
                if(t.getY() == y){
                    return t;
                }
            }
        }
        return null;
    }

    public BufferedImage[] getPokemonsImgs(){
        return pokemonImgs;
    }

    public void reset() {
        pokemons.clear();
        Amount = 0;
    }

    public void addTower(APokemon selectTower, int Posx, int Posy) {
        if(selectTower.getTowerType() == BULBASAUR){
            pokemons.add(new Bulbasaur(Posx, Posy, Amount++, selectTower.getTowerType()));
        }else if( selectTower.getTowerType() == SQUIRTLE){
            pokemons.add(new Squirtle(Posx, Posy, Amount++, selectTower.getTowerType()));
        }else if(selectTower.getTowerType() == CHARMENDER){
            pokemons.add(new Charmender(Posx, Posy, Amount++, selectTower.getTowerType()));
        }
    }

    
    public Playing getPlaying() {
        return playing;
    }
    
}
