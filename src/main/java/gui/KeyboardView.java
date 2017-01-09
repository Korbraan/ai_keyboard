package gui;

import models.Keyboard;
import models.Letter;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Steak on 09/01/2017.
 */
public class KeyboardView extends JPanel implements Observer  {

    private Keyboard keyboard=null;

    public KeyboardView(Keyboard k){
        this.keyboard =  k;
        this.keyboard.addObserver(this);
        this.setPreferredSize(new Dimension(800, 600));
        this.repaint();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Letter[][] keyboard = this.keyboard.getKeyboard();

        for(int i = 0; i<keyboard.length; i++){
            for(int j = 0; j<keyboard[i].length; j++){
                g.setColor(Color.WHITE);
                g.fillRect((j*80)+2,(i*80)+2,60,60);
                g.setColor(Color.BLACK);
                g.drawRect((j*80)+2,(i*80)+2,60,60);
                if(!(keyboard[i][j] == null)){
                    g.drawString(keyboard[i][j].toString(),j*80+27   ,i*80+35);
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        this.validate();
        this.repaint();
    }
}
