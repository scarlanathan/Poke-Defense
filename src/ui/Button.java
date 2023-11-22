package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Button {
    public int x,y,width,height,id;
    private String text;

    private Rectangle rectangle;
    private boolean mouseOver,mousePressed;

    public Button(String text,int x , int y, int width, int height){
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = -1;
        initRectangle();
    }

    public Button(String text,int x , int y, int width, int height, int id){
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id  = id;
        initRectangle();
    }

    public void initRectangle(){
        this.rectangle = new Rectangle(x,y,width,height);
    }

    public void draw(Graphics g){

        //body
        drawbody(g);

        //border
        drawborder(g);

        //text
        drawtext(g);

    }

    public boolean isMouseOver(){
        return mouseOver;
    }

    public boolean isMousePressed(){
        return mousePressed;
    }

    private void drawborder(Graphics g) {
        g.setColor(Color.black);
        g.drawRect(x, y, width, height);
        if (isMousePressed()){
            g.drawRect(x+1, y+1, width-2, height-2);
            g.drawRect(x+2, y+2, width-4, height-4);
        }
    }

    public void resetBooleans(){
        this.mouseOver = false;
        this.mousePressed = false;

    }

    private void drawbody(Graphics g) {
        if(mouseOver){
            g.setColor(Color.gray);
        }else{
            g.setColor(Color.WHITE);
        }
        g.fillRect(x, y, width, height);
    }

    private void drawtext(Graphics g) {
        int w= g.getFontMetrics().stringWidth(text);
        int h = g.getFontMetrics().getHeight();
        g.drawString(text, x - w/2 + width/2, y + h/2 + height/2);
    }


    public void setMouseOver(boolean mouseOver){
        this.mouseOver = mouseOver;
    }

    public void setMousePressed(boolean mousePressed){
        this.mousePressed = mousePressed;
    }

    





    // getter
    public Rectangle getRectangle(){
        return rectangle;
    }

    public int getId() {
        return id;
    }

    public void setText(String text) {
        this.text=text;
    }


}
