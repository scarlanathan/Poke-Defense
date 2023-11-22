package main;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import inputs.KeyboardListener;
import inputs.MyMouseListener;

public class GameScreen extends JPanel{

    private Dimension size;
    private Game game;
    
    private MyMouseListener myMouseListener;
    private KeyboardListener keyboardListener;

    GameScreen(Game game){
        this.game = game;
        setSize();
    }

    public void inputs(){
        myMouseListener = new MyMouseListener(game);
        keyboardListener = new KeyboardListener(game);

        addMouseListener(myMouseListener);
        addMouseMotionListener(myMouseListener);
        addKeyListener(keyboardListener);

        requestFocus();
    }

    private void setSize(){
        size = new Dimension(640,800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }
    
    public void paintComponent(Graphics G){
        super.paintComponent(G);
        game.getRender().render(G);
    }

    
}
