package torpedo_gui;

import javax.swing.*;

import static javax.swing.BorderFactory.createLineBorder;

public class MySquares extends JPanel {

    MySquares(int i, int j) {
        int size = GamePanel.targetsize;
        this.setSize(size, size);
        this.setLocation(GamePanel.sidepadding+size *i, GamePanel.toppadding+size *j);

        if(i==1){               //TODO add if ship is present
            this.setBackground(GamePanel.navylabelcolor);
            int bordersize = 6;
            this.setBorder(BorderFactory.createMatteBorder(
                    bordersize, bordersize, bordersize, bordersize,   GamePanel.seacolor));
        }else{
            this.setBackground(GamePanel.seacolor);
            this.setBorder(null);
        }
    }
}
