package torpedo_gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static torpedo_gui.GamePanel.setClicked;

public class EnemySquares extends JPanel implements MouseListener {

    private int size = GamePanel.targetsize;

    private String turn = "MYTURN";
    private int hit = 0;
    private int i = 0;
    private int j = 0;

    EnemySquares(int i, int j){

        int bordersize = 3;
        this.setBackground(GamePanel.navylabelcolor);
        this.setBorder(BorderFactory.createMatteBorder(
                bordersize, bordersize, bordersize, bordersize,   GamePanel.seacolor));
        this.setSize(size,size);
        this.setLocation(GamePanel.width-(GamePanel.sidepadding+10*GamePanel.targetsize)+size*i, GamePanel.toppadding+size*j);
        this.addMouseListener(this);
        this.i = i;
        this.j = j;
    }

    public void disableClicks(){
        turn = "ENEMYSTURN";
    }

    public void enableClicks(){
        turn = "MYTURN";
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(turn=="MYTURN"){
            if(this.hit==0){
                this.setHit();
                GamePanel.setCoordinates(this.i, this.j);
                setClicked(1);
            }
        }
    }



    @Override
    public void mouseEntered(MouseEvent e) {

        if(turn=="MYTURN"){
            if(hit==0){
                this.setBackground(GamePanel.targetaquiredcolor);
                this.setBorder(null);
                this.repaint();
            }
        }

    }
    @Override
    public void mouseExited(MouseEvent e) {
        if(turn=="MYTURN"){
            if(hit==0){
                int bordersize = 3;
                this.setBackground(GamePanel.navylabelcolor);
                this.setBorder(BorderFactory.createMatteBorder(
                        bordersize, bordersize, bordersize, bordersize,   GamePanel.seacolor));
                this.repaint();
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}

    public int getHit(){
        return hit;
    }

    public void setHit(){
        this.hit = 1;
    }
}
