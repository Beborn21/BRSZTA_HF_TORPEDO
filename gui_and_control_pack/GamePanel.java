package gui_and_control_pack;

import brsta.*;
import brsta.GameBoard;
import communicationPack.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;


public class GamePanel extends JPanel implements ActionListener, MouseListener {

    //TODO add ship and network variables
    GameBoard board=new  GameBoard();
    Network comInterface;
    int [][] shipmatrix;
    int []lastHitCoordinate=new int[2];


    String mode;

    String status = "COMPUTING";
    JPanel [][] myshippanels = new JPanel[10][10];
    JPanel [][] enemyshippanels = new JPanel[10][10];
    JButton restartbutton;
    private boolean restart = false;
    JButton reconnectbutton;
    private boolean reconnect = false;
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


    GamePanel(int port ,String mode){
        System.out.println("game constructor opened");
        this.mode=mode;

        //panel look setup
        this.setBounds(0,0,width,height);
        this.setBackground(backgroundcolor);
        this.setLayout(null);



        // add panels to game
        shipmatrix = board.getShipMatrix();
        this.addMyNavy(shipmatrix);              //TODO give ship placement as parameter
        this.addEnemyNavy();
        this.addSea();
        this.addReconnectButton();
        this.addRestartButton();
        this.addTurnLabel();
        this.repaint();

        //clicks disabled until start of game
        computingGUI();
        System.out.println("computing gui implemented");

        try {
            if (mode == "SERVER") {
                comInterface = new server(port, this);
                System.out.println("new server initialised");
            } else {
                comInterface = new client(port,this);
                System.out.println("new client initialised");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("new server/client fail");

        }


        if(mode.equals("SERVER")){  //mi kezd??nk
            myTurnGUI();
        }else if(mode.equals("CLIENT")){
            enemysTurnGUI();    //az ellenf??l kezd, kattint??s letiltva
        }


        if(status.equals("ENEMYSTURN")){
            this.enemysTurnGUI();   //disables clicks and sets text to enemys turn
        }

    }




    public void waitForShot(int [] coordinates){
        System.out.println("\n Wait for Shot method");
        int [] response = new int[3];
        response[2]=1;
/************************************************************************************************/
/********************        TODO           WAIT FOR SHOT
 *
 * ha az ellens??g j??n, ez a method fut, amig a status meg nem v??ltozik.
 *
  */
/************************************************************************************************/
            // recieve coordinates

            //board t??bl??n megmondja hogy tal??lt e a megadott koordin??ta
            boolean talalt= board.fireGameBoard(new ShipSegment(coordinates[0],coordinates[1]));
            boolean vege= board.EndGame();

            if (true==talalt) {
                response[0] = 1;
                if(true==vege){
                    response[1] = 1;
                }
            }else {
                response[0] = 0;
                response[1] = 0;
            }

            try {
                comInterface.SendData(response); // v??lasz k??ld??se
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (true==talalt) {
                shootAtMyShipsGUI(coordinates[0], coordinates[1], "HIT");
                if(true==vege){
                    
                    endoOfGameGUI("LOST");  //vesztett??nk, kil??p a methodbol
                }
            }else {
                
                shootAtMyShipsGUI(coordinates[0], coordinates[1], "MISS");
                myTurnGUI();        //??jra mi j??v??nk, kil??p a methodbol
            }

            
/**************************************************************************************************/

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
        restartbutton.setFont(GameWindow.PixelFont20);
        restartbutton.addActionListener(this);
        this.add(restartbutton);
    }
    private void addMyNavy(int[][]shippresent){
        int rows = 10;
        int cloumns = 10;
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cloumns;j++){
                myshippanels[i][j] = new JPanel();
                myshippanels[i][j].setSize(targetsize, targetsize);
                myshippanels[i][j].setLocation(GamePanel.sidepadding+targetsize *i, GamePanel.toppadding+targetsize *j);

                if(shippresent[i][j]==1){
                    myshippanels[i][j].setBackground(GamePanel.navylabelcolor);
                    int bordersize = 6;
                    myshippanels[i][j].setBorder(BorderFactory.createMatteBorder(
                            bordersize, bordersize, bordersize, bordersize,   GamePanel.seacolor));
                }else{
                    myshippanels[i][j].setBackground(GamePanel.seacolor);
                    myshippanels[i][j].setBorder(null);
                }
            this.add(myshippanels[i][j]);
            }
        }
    }
    private void addEnemyNavy(){
        int rows = 10;
        int cloumns = 10;
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cloumns;j++){
                enemyshippanels[i][j] = new JPanel();
                int bordersize = 3;
                enemyshippanels[i][j].setBackground(GamePanel.navylabelcolor);
                enemyshippanels[i][j].setBorder(BorderFactory.createMatteBorder(
                        bordersize, bordersize, bordersize, bordersize,   GamePanel.seacolor));
                enemyshippanels[i][j].setSize(targetsize,targetsize);
                enemyshippanels[i][j].setLocation(GamePanel.width-(GamePanel.sidepadding+10*GamePanel.targetsize)+ targetsize*i,
                        GamePanel.toppadding+targetsize*j);
                enemyshippanels[i][j].addMouseListener(this);
                this.add(enemyshippanels[i][j]);
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
        reconnectbutton.setFont(GameWindow.PixelFont20);
        reconnectbutton.addActionListener(this);
        this.add(reconnectbutton);


    }
    private void addTurnLabel(){
        turnlabel = new JLabel("TURN",SwingConstants.CENTER );
        turnlabel.setSize(width, 50);
        turnlabel.setLocation(0, toppadding+ targetsize*10+ seabordersize+10);
        turnlabel.setForeground(navylabelcolor);
        turnlabel.setFont(GameWindow.PixelFont50);
        turnlabel.setVisible(true);
        this.add(turnlabel);
    }

    public void shootAtMyShipsGUI(int i, int j, String hitmiss){

        if(hitmiss.equals("HIT")){
            myshippanels[i][j].setBorder(null);
            myshippanels[i][j].setBackground(shipfoundcolor);
            myshippanels[i][j].repaint();
        }else if(hitmiss.equals("MISS")) {
            myshippanels[i][j].setBackground(blackgray);
            int bordersize = 10;
            myshippanels[i][j].setBorder(BorderFactory.createMatteBorder(
                    bordersize, bordersize, bordersize, bordersize, seacolor));
            myshippanels[i][j].repaint();
        }
    }   //TODO call this when the enemy takes a shot at our ships
    void shootAtEnemyShipsGUI(int i, int j, String hitmiss){          //TODO call this when we take a shot at enemy ships
        enemyshippanels[i][j].setBorder(null);
        if(hitmiss.equals("HIT")){
            enemyshippanels[i][j].setBackground(shipfoundcolor);
            enemyshippanels[i][j].repaint();
        }else if(hitmiss.equals("MISS")){
            enemyshippanels[i][j].setBackground(shipfoundcolor);
            enemyshippanels[i][j].setOpaque(false);
            enemyshippanels[i][j].repaint();
        }
    }

/** GUI methods to call when turn is changed or computing or endofgame */
    public void enemysTurnGUI(){
            status="ENEMYSTURN";

            turnlabel.setText("ENEMYS TURN");
        this.repaint();
    }
    public void myTurnGUI(){
        status="MYTURN";
        turnlabel.setText("MYTURN");
        this.repaint();
    }
    public void computingGUI(){
        status="COMPUTING";
        turnlabel.setText("COMPUTING");
        this.repaint();
    }
    public void endoOfGameGUI(String s){
        if(s.equals("WON")){
            turnlabel.setText("YOU WON!");
        }else if(s.equals("LOST")){
            turnlabel.setText("YOU LOST.");
        }
        this.repaint();
    }
/***********************************************************************/
    public boolean getRestart(){ return restart; }
    public boolean getReconnect(){ return reconnect; }


    private int [] getCoordinates(Point point){
        int [] coord = new int[2];
        coord[0] =-(width-sidepadding-10*targetsize-point.x)/targetsize;
        coord[1] =(point.y-toppadding)/targetsize;
        return coord;
    }

    private boolean hasBeenShotAt(int i, int j){
        return shipfoundcolor.equals(enemyshippanels[i][j].getBackground());
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==restartbutton){
/**********************************************************************************************/
/********************* TODO RESTART
 *
 * itt kell megh??vni mindent ami a restarthoz kell kommunik??ci??s oldalr??l,
 * ut??na ??jra implement??ljuk a classt
 *
 */
/**********************************************************************************************/
            comInterface.doRestart();

            restartFunc();

        }else if(e.getSource()==reconnectbutton){
            comInterface.doReconnect();
/**********************************************************************************************/
/********************* TODO RECONNECT
 *
 * itt kell megh??vni mindent ami a reconnecthez kell kommunik??ci??s oldalr??l,
 * ut??na ??jra megnyitjuk a start panelt
 *
 */
/**********************************************************************************************/
            reconnectFunc();
        }
    }

    public void restartFunc(){
        try {
            comInterface.CloseConnection();
        } catch (IOException ioException) {
            ioException.printStackTrace();        }

        this.setVisible(false);
        restart=true;

    }

    public void reconnectFunc(){

        try {
            comInterface.CloseConnection();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        this.setVisible(false);
        reconnect=true;
    }




    @Override
    public void mouseClicked(MouseEvent e) {
        if(status.equals("MYTURN")){
            // CHECK COORDINATES OF CLICK
            int [] coord = getCoordinates(e.getComponent().getLocation());
            int x = coord[0];
            int y = coord[1];
            if(!hasBeenShotAt(x, y)){
/**********************************************************************************************/
/********************* TODO SHOT
 *
 * itt leadtunk egy l??v??st oda, ahova m??g eddig soha, le kell kommunik??lni az ellenf??llel
 * ha tal??lt,akkor mi j??v??nk,
 * ha nem tal??lt, akkor visszal??p??nk a waitForShot methodbe *
 */
/**********************************************************************************************/
                //computingGUI();   //TODO include this to disable clicks
                //SEND COORDINATES
                //comInterface.SendData(coord);
                //WAIT FOR ANSWER
                //response = nullArray;
                //response = comInterface.ReceiveData();
                //HIT OR MISS

                //TO DOO :MI k??ld??nk restartot?
                int [] sendFormat=new int[3];
                sendFormat[0]=coord[0];
                sendFormat[1]=coord[1];
                sendFormat[2]=0;

                try {
                    comInterface.SendData(sendFormat);

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                lastHitCoordinate[0]=x;
                lastHitCoordinate[1]=y;

            }
        }

    }


    public void  responseForShot(int[] response) {
        if (!hasBeenShotAt(lastHitCoordinate[0], lastHitCoordinate[1])) {
            if (response[0] == 1) {  //a t??mb els?? eleme jelzi ha tal??lt, azaz az ??rt??ke =1
                shootAtEnemyShipsGUI(lastHitCoordinate[0], lastHitCoordinate[1], "HIT"); //jelezz??k a t??bl??n a tal??latot
                if (response[1] == 1) {
                    //jatek v??ge j??tt a masik oldalrol -> nyert??nk
                    endoOfGameGUI("WON");

                }else{
                    myTurnGUI();     // juhu ??jra mi j??v??nk, ??jra szabad kattintani
                }

            } else if (response[0] == 0) {
                shootAtEnemyShipsGUI(lastHitCoordinate[0], lastHitCoordinate[1], "MISS"); //jelezz??k a t??bl??n a hib??t
                enemysTurnGUI(); // m??r nem mi j??v??nk, kattint??sok tov??bbra is letiltva
            }
        }
    }



    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {
        if(status.equals("MYTURN")){
            int [] coord = getCoordinates(e.getComponent().getLocation());
            int x = coord[0];
            int y = coord[1];
            if(!hasBeenShotAt(x, y)){//if ship hasnt been hit before
                e.getComponent().setBackground(GamePanel.targetaquiredcolor);
                e.getComponent().repaint();
            }
        }
    }
    @Override
    public void mouseExited(MouseEvent e) {
        if(status.equals("MYTURN")){
            int [] coord = getCoordinates(e.getComponent().getLocation());
            int x = coord[0];
            int y = coord[1];
            if(!hasBeenShotAt(x, y)){   //if ship hasnt been hit before
                e.getComponent().setBackground(GamePanel.navylabelcolor);
                e.getComponent().repaint();
            }
        }

    }
}
