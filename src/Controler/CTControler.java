package Controler;

import java.util.ArrayList;

import Monsters.AEnemy;

import static Save.Constants.Towers.BLASTOISE;
import static Save.Constants.Towers.BULBASAUR;
import static Save.Constants.Towers.CHARIZARD;
import static Save.Constants.Towers.CHARMELEON;
import static Save.Constants.Towers.CHARMENDER;
import static Save.Constants.Towers.IVYSAUR;
import static Save.Constants.Towers.SQUIRTLE;
import static Save.Constants.Towers.VENUSAUR;
import static Save.Constants.Towers.WARTORTLE;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import Save.LoadSave;

import Tower.APokemon;
import object.CTAttack;
import sequence.Playing;
import static Save.Constants.CT.*;


public class CTControler {
    private Playing playing;

    private ArrayList<CTAttack> ct_arr= new ArrayList<>();
    private ArrayList<Explosion> explo_arr = new ArrayList<>();
    private BufferedImage[] ct_imgs,explo_imgs;
    private int ct_id = 0;
    private boolean drawExplo;
    private int exploTick, exploIndex;
    private Point2D.Float exploPos;
    
    public CTControler(Playing playing){
        this.playing = playing;
        importImgs();
    }

    public void update(){
        for(CTAttack c : ct_arr){
            if(c.isActive()){
                c.move();
                if(isHittingEnemy(c)){
                    c.setActive(false);
                    if(c.getCTtype() == FEU || c.getCTtype() == FEUU || c.getCTtype() == FEUUU){
                        explo_arr.add(new Explosion(c.getPos()));
                        explodeOnEnemies(c);
                    }
                }else if (isCToutsideBounds(c)){
                    c.setActive(false);

                }
            }
        }
        for(Explosion e : explo_arr){
            if(e.getexploIndex() < 7)
                e.update();
        }
    }

    private boolean isCToutsideBounds(CTAttack c) {
        if(c.getPos().x >= 0)
            if(c.getPos().x <= 640)
                if(c.getPos().y >= 0)
                    if(c.getPos().y <= 800)
                        return false;
        return true;
    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        for(CTAttack a : ct_arr){
            if(a.isActive()){
                if(a.getCTtype() == FEU || a.getCTtype() == FEUU || a.getCTtype() == FEUUU){
                    g2d.translate(a.getPos().x , a.getPos().y);
                    g2d.rotate(Math.toRadians(a.getRotation()));
                    g2d.drawImage(ct_imgs[a.getCTtype()], -16, -16, null);
                    g2d.rotate(-Math.toRadians(a.getRotation()));
                    g2d.translate(-a.getPos().x , -a.getPos().y);
                }else{
                    g2d.drawImage(ct_imgs[a.getCTtype()], (int)a.getPos().x - 16,(int)a.getPos().y - 16, null);
                }
            }
        }
        drawExplosion(g2d);
    }
    

    private void explodeOnEnemies(CTAttack c) {
        for (AEnemy e : playing.getMonsterControler().getEnemies()){
            if (e.isAlive()){
                float radius = 0f;
                switch (c.getCTtype()) {
                    case FEU:
                        radius = 40.0f;
                        break;
                    case FEUU:
                        radius = 60.0f;
                        break;
                    case FEUUU:
                        radius = 80.0f;
                        break;
                    default:
                        break;
                }
                
                float xDist = Math.abs(c.getPos().x - e.getX());
                float yDist = Math.abs(c.getPos().y - e.getY());

                float realDist = (float) Math.hypot(xDist, yDist);

                if(realDist <= radius){
                    e.hurt(c.getDamage());
                }
            }
        }
    }

    private boolean isHittingEnemy(CTAttack c) {
        for(AEnemy e : playing.getMonsterControler().getEnemies()){
            if(e.isAlive()){
                if(e.getRectangle().contains(c.getPos())){
                e.hurt(c.getDamage());
                if(c.getCTtype() == EAU || c.getCTtype() == EAUU || c.getCTtype() == EAUUU){
                    e.slow();
                }
                return true;
            }
            }
            
        }
        return false;
    }

    private void importImgs(){
        BufferedImage poke = LoadSave.getSpritesMinions();
        ct_imgs = new BufferedImage[9];
        for(int i = 0; i < 3; i++){
            ct_imgs[i] = poke.getSubimage((7+i) * 32 , 32, 32,32 );
        }
        ct_imgs[3] = poke.getSubimage( 8* 32 , 5*32, 32,32 );
        ct_imgs[4] = poke.getSubimage( 3* 32 , 8*32, 32,32 );
        ct_imgs[5] = poke.getSubimage( 3* 32 , 6*32, 32,32 );
        ct_imgs[6] = poke.getSubimage( 3* 32 , 7*32, 32,32 );
        ct_imgs[7] = poke.getSubimage( 8* 32 , 6*32, 32,32 );
        ct_imgs[8] = poke.getSubimage( 9* 32 , 6*32, 32,32 );

        importExploImgs(poke);
    }

    private void importExploImgs(BufferedImage poke){
        explo_imgs = new BufferedImage[7];

        for(int i=0; i < 7; i++){
            explo_imgs[i] = poke.getSubimage(i*32,32*2,32,32);
        }
    }

    public void newCT(APokemon p, AEnemy e){
        int type = getCtType(p);

        int xDist = (int) (p.getX() - e.getX());
        int yDist = (int) (p.getY() - e.getY());
        int totDis = Math.abs(xDist) + Math.abs(yDist);

        float xPer = (float) Math.abs(xDist) / totDis;

        float xSpeed = xPer * Save.Constants.CT.GetSpeed(type);
        float ySpeed = Save.Constants.CT.GetSpeed(type) - xSpeed;

        if(p.getX() > e.getX()){
            xSpeed *= -1;
        }
        if(p.getY() > e.getY()){
            ySpeed *= -1;
        }

        float rotate = 0;

        if(type == FEU){
            float arcValue = (float)Math.atan(yDist/(float)xDist);
            rotate = (float)Math.toDegrees(arcValue);
            if(xDist < 0){
                rotate += 180;
            }
        }
                
        ct_arr.add(new CTAttack(p.getX() + 16, p.getY() + 16, xSpeed, ySpeed,p.getDamage(),rotate, ct_id++, type));
    }

    
    private int getCtType(APokemon p) {
        switch (p.getTowerType()) {
            case CHARMENDER:
                return FEU;
            case BULBASAUR: 
                return FEUILLE;
            case SQUIRTLE:
                return EAU;
            case IVYSAUR:
                return FEUILLEE;
            case VENUSAUR:
                return FEUILLEEE;
            case CHARMELEON :
                return FEUU;
            case CHARIZARD : 
                return FEUUU;
            case WARTORTLE:
                return EAUU;
            case BLASTOISE:
                return EAUUU;
            default:
                return 0;
        }
    }
    public int getExploIndex() {
        return exploIndex;
    }
    public Point2D.Float getExploPos() {
        return exploPos;
    }
    public ArrayList<CTAttack> getCt_arr() {
        return ct_arr;
    }
    public int getCt_id() {
        return ct_id;
    }
    public BufferedImage[] getCt_imgs() {
        return ct_imgs;
    }
    public int getExploTick() {
        return exploTick;
    }
    public ArrayList<Explosion> getExplo_arr() {
        return explo_arr;
    }
    public BufferedImage[] getExplo_imgs() {
        return explo_imgs;
    }
    public boolean isDrawExplo() {
        return drawExplo;
    }


    


    private void drawExplosion(Graphics2D g2d) {
        for(Explosion e : explo_arr){
            if (e.getexploIndex() < 7 ){
                g2d.drawImage(explo_imgs[e.getexploIndex()],(int) e.getPos().x - 16, (int)e.getPos().y - 16,null);
            }
        }
    }

    public Playing getPlaying() {
        return playing;
    }
    

    public class Explosion{
        private Point2D.Float pos;
        private int exploTick = 0, exploIndex = 0;

        public Explosion(Point2D.Float pos){
            this.pos = pos;
        }

        public void update(){
            exploTick++;
            if(exploTick >= 12){
                exploTick = 0;
                exploIndex++;
            }
        }

        public int getexploIndex(){
            return exploIndex;
        }
        public Point2D.Float getPos(){
            return pos;
        }
    }

    public void reset() {
		ct_arr.clear();
		explo_arr.clear();

		ct_id = 0;
	}

}
