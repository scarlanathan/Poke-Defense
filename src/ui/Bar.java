package ui;

import java.awt.Color;
import java.awt.Graphics;

public class Bar {

    protected int x, y, width, height;

    public Bar(int x, int y, int witdh, int height){
        this.x = x;
        this.y = y;
        this.width = witdh;
        this.height = height;
        
    }
    
    

    protected void drawFeedbackButton(Graphics g, Button b) {
        //mouseOver
        if(b.isMouseOver()){
            g.setColor(Color.white);
        }else{
            g.setColor(Color.BLACK);
        }
        //border 
        g.drawRect(b.x, b.y, b.width, b.height);

        //mousePressed
        if(b.isMousePressed()){
            g.drawRect(b.x+1, b.y+1, b.width-2, b.height-2);
            g.drawRect(b.x+2, b.y+2, b.width-4, b.height-4);
        }

    }
}
