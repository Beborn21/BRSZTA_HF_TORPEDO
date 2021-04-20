package torpedo_gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel implements ActionListener {

    static protected int clicked = 0;
    static protected int xcoordinate = -1;
    static protected int ycoordinate = -1;

    MySquares[][] mynavy  = new MySquares[10][10];
    EnemySquares[][] enemynavy  = new EnemySquares[10][10];

    JButton restartbutton;
    private int restart = 0;

    JButton reconnectbutton;
    private int reconnect = 0;

    JLabel turnlabel;

    static int targetsize = 35;
    static int seabordersize = 50;
    static int sidepadding = 90;
    static int toppadding = 130;
    static int labelpadding = 80;
    static int width = 1000;
    static int height = 700;

    static final Color navylabelcolor = new Color(0xF6E4F6);
    static final Color blackgray = new Color(0x4C5B5C);
    static final Color backgroundcolor = new Color(0xFFA753);
    static final Color seacolor = new Color(0x76BED0);
    static final Color shipfoundcolor = new Color(0xFF715B);
    static final Color targetaquiredcolor = new Color(0xFFDD4A);
    static Font PixelFont50;
    static Font PixelFont20;

    GamePanel(){                //TODO give ship placement as parameter
        fontSetup();
        this.setBounds(0,0,width,height);
        this.setBackground(backgroundcolor);
        this.setLayout(null);

        this.addMyNavy();       //TODO give ship placement as parameter
        this.addEnemyNavy();
        this.addSea();
        this.addReconnectButton();
        this.addRestartButton();
        this.addTurnLabel();

        this.repaint();
    }

    private void fontSetup(){
        try{
            PixelFont50 = Font.createFont(Font.TRUETYPE_FONT, new File("src/torpedo_gui/PixelMplus12-Bold.ttf")).deriveFont(50f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/torpedo_gui/PixelMplus12-Bold.ttf")));
        }
        catch(IOException | FontFormatException e){

        }
        try{
            PixelFont20 = Font.createFont(Font.TRUETYPE_FONT, new File("src/torpedo_gui/PixelMplus12-Bold.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/torpedo_gui/PixelMplus12-Bold.ttf")));
        }
        catch(IOException | FontFormatException e){

        }
    }

    private void addSea(){

        int seaborder = 12;
        JPanel sea = new JPanel();
        sea.setBackground(seacolor);
        sea.setBounds(sidepadding-seabordersize,toppadding-seabordersize,
                width-(2*(sidepadding-seabordersize)), 10*targetsize+2*seabordersize);
        sea.setBorder(BorderFactory.createMatteBorder(
                seaborder, seaborder, seaborder, seaborder,  targetaquiredcolor ));
        sea.setOpaque(true);
        sea.setVisible(true);
        this.add(sea);

    }

    private void addRestartButton(){
        int buttonpadding = 350;
        int buttonsize = 140;
        int bordersize = 5;

        restartbutton = new JButton();
        restartbutton.setSize(buttonsize, 50);
        restartbutton.setLocation(buttonpadding, toppadding+ targetsize*10+ seabordersize+90);
        restartbutton.setVisible(true);
        restartbutton.setBackground(navylabelcolor);
        restartbutton.setBorder(BorderFactory.createMatteBorder(
                bordersize, bordersize, bordersize, bordersize,  targetaquiredcolor));
        restartbutton.setText("RESTART");
        restartbutton.setForeground(seacolor);
        restartbutton.setFont(PixelFont20);
        restartbutton.addActionListener(this);
        this.add(restartbutton);
    }

    private void addMyNavy(){

        int rows = 10;
        int cloumns = 10;
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cloumns;j++){
                mynavy[i][j] = new MySquares(i, j);     //TODO give ship present as parameter
                this.add(mynavy[i][j]);
            }
        }

    }

    private void addEnemyNavy(){
        int rows = 10;
        int cloumns = 10;
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cloumns;j++){
                enemynavy[i][j] = new EnemySquares(i, j);
                this.add(enemynavy[i][j]);
            }
        }
    }

    private void addReconnectButton(){
        int buttonpadding = 350;
        int buttonsize = 140;
        int bordersize = 5;

        reconnectbutton = new JButton();
        reconnectbutton.setSize(buttonsize, 50);
        reconnectbutton.setLocation(width-buttonpadding-buttonsize, toppadding+ targetsize*10+ seabordersize+90);
        reconnectbutton.setVisible(true);
        reconnectbutton.setBackground(navylabelcolor);
        reconnectbutton.setBorder(BorderFactory.createMatteBorder(
                bordersize, bordersize, bordersize, bordersize,   targetaquiredcolor));
        reconnectbutton.setText("RECONNECT");
        reconnectbutton.setForeground(seacolor);
        reconnectbutton.setFont(PixelFont20);
        reconnectbutton.addActionListener(this);
        this.add(reconnectbutton);


    }

    private void addTurnLabel(){
        turnlabel = new JLabel("TURN",SwingConstants.CENTER );
        turnlabel.setSize(width, 50);
        turnlabel.setLocation(0, toppadding+ targetsize*10+ seabordersize+10);
        turnlabel.setForeground(navylabelcolor);
        turnlabel.setFont(PixelFont50);
        turnlabel.setVisible(true);
        this.add(turnlabel);
    }

    public void shotAtMyShipsGUI(int i, int j, String hitmiss){

        if(hitmiss.equals("HIT")){
            mynavy[i][j].setBorder(null);
            mynavy[i][j].setBackground(shipfoundcolor);
            mynavy[i][j].repaint();
        }else if(hitmiss.equals("MISS")) {
            mynavy[i][j].setBackground(blackgray);
            int bordersize = 10;
            mynavy[i][j].setBorder(BorderFactory.createMatteBorder(
                    bordersize, bordersize, bordersize, bordersize, seacolor));
            mynavy[i][j].repaint();
        }
    }   //TODO call this when the enemy takes a shot at our ships

    void shotAtEnemyShipsGUI(int i, int j, String hitmiss){          //TODO call this when we take a shot at enemy ships
        if(hitmiss.equals("HIT")){
            enemynavy[i][j].setBackground(shipfoundcolor);
            enemynavy[i][j].repaint();
        }else if(hitmiss.equals("MISS")){
            enemynavy[i][j].setBorder(null);
            enemynavy[i][j].setOpaque(false);
            enemynavy[i][j].repaint();
        }
    }

    public void enableShooting(){
        setClicked(0);
        setCoordinates(-1,-1);
        int rows = 10;
        int cloumns = 10;
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cloumns;j++){
                enemynavy[i][j].enableClicks();
            }
        }
    }

    public void disableShooting(){
        int rows = 10;
        int cloumns = 10;
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cloumns;j++){
                enemynavy[i][j].disableClicks();
            }
        }
    }

    static public void setClicked(int isclicked){
        clicked=isclicked;
    }

    public int getClicked(){
        return this.clicked;
    }

    static protected void setCoordinates(int x, int y){
        xcoordinate=x;
        ycoordinate=y;
    }

    public int  getXCoordinate(){
        return this.xcoordinate;
    }
    public int  getYCoordinate(){
        return this.ycoordinate;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==restartbutton){
            restart=1;
        }else if(e.getSource()==reconnectbutton){
            reconnect=1;
        }
    }

    public int getRestart(){return restart;}
    public int getReconnect(){return reconnect;}

    public void enemysTurnGUI(){
        this.disableShooting();
        turnlabel.setText("ENEMYS TURN");
    }
    public void myTurnGUI(){
        this.enableShooting();
        turnlabel.setText("ENEMYS TURN");
    }
    public void endofgame(String s){
        this.disableShooting();
        if(s.equals("WON")){
            turnlabel.setText("YOU WON!");
        }else if(s.equals("LOST")){
            turnlabel.setText("YOU LOST.");
        }



    }

}
