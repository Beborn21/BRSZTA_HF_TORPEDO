package gui_and_control_pack;

import brsta.*;
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

    int [][] shipmatrix = board.getShipMatrix();
    Network comInterface=null;

    String status = "MYTURN";
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
    static Font PixelFont50;
    static Font PixelFont20;

    GamePanel(int port, String mode){

        //panel look setup
        fontSetup();
        this.setBounds(0,0,width,height);
        this.setBackground(backgroundcolor);
        this.setLayout(null);

/**** ships dummie version just to fill matrix   *******/
        // TODO implement board (@Sári)
        shipmatrix = new int[10][10];
        for(int rows = 0; rows<10;rows++){
            for(int columns = 0; columns<10;columns++){
                shipmatrix[rows][columns] = (rows*columns)%2;
            }
        }
/*******************************************************/
        // add panels to game
        this.addMyNavy(shipmatrix);              //TODO give ship placement as parameter
        this.addEnemyNavy();
        this.addSea();
        this.addReconnectButton();
        this.addRestartButton();
        this.addTurnLabel();
        this.repaint();

        //clicks disabled until start of game
        computingGUI();

        // update network variable based on port and mode (@Levi)

        try {
            if(mode=="SERVER"){
                comInterface=new server(port);
            }
            else{
                comInterface=new client(port);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(mode.equals("SERVER")){  //mi kezdünk
            myTurnGUI();
        }else if(mode.equals("CLIENT")){
            enemysTurnGUI();    //az ellenfél kezd, kattintás letiltva
        }


        if(status.equals("ENEMYSTURN")){
            this.enemysTurnGUI();   //disables clicks and sets text to enemys turn
            //TODO add wait for shot method
            try {
                comInterface.ReceiveData(); // itt megkapja a koordináták tömbjét
            } catch (IOException e) {
                e.printStackTrace();
            }
            //waitForShot();
        }

    }

    private void waitForShot(){
        int [] coordinates=new int[2];
        int [] response={-1,-1};
        while(status.equals("ENEMYSTURN")){ //itt maradunk amig mi nem jovunk
/************************************************************************************************/
/********************        TODO           WAIT FOR SHOT
 *
 * ha az ellenség jön, ez a method fut, amig a status meg nem változik.
 *
  */
/************************************************************************************************/
            // recieve coordinates

            try {

                coordinates=comInterface.ReceiveData();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //chekkoljuk, hogy talált-e
            //board táblán megmondja hogy talált e a megadott koordináta
            boolean talalt= board.fireGameBoard(new ShipSegment(coordinates[0],coordinates[1]));
            boolean vege= board.EndGame();
            try {
                comInterface.SendData();
            } catch (IOException e) {
                e.printStackTrace();
            }


            //kommunikáció kezelése



            /*if (talált) {
                if(minden hajónk kilőve ==true){
                    //TODO
               }
                //TODO
            }else if(nem talált){
                //TODO
            }*/

            //GUI kezelése

            if (talalt) {
                shootAtMyShipsGUI(coordinates[0], coordinates[1], "HIT");
                comInterface.SendData(response);
                if(vege ==true){
                    endoOfGameGUI("LOST");  //vesztettünk, kilép a methodbol
                }
            }else {
                shootAtMyShipsGUI(coordinates[0], coordinates[1], "MISS");
                myTurnGUI();        //újra mi jövünk, kilép a methodbol
            }
/**************************************************************************************************/
        }
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
 * itt kell meghívni mindent ami a restarthoz kell kommunikációs oldalról,
 * utána újra implementáljuk a classt
 *
 */
/**********************************************************************************************/
            this.setVisible(false);
            restart=true;
        }else if(e.getSource()==reconnectbutton){
/**********************************************************************************************/
/********************* TODO RECONNECT
 *
 * itt kell meghívni mindent ami a reconnecthez kell kommunikációs oldalról,
 * utána újra megnyitjuk a start panelt
 *
 */
/**********************************************************************************************/
            this.setVisible(false);
            reconnect=true;
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(status.equals("MYTURN")){
            // CHECK COORDINATES OF CLICK
            int [] coord = getCoordinates(e.getComponent().getLocation());
            int x = coord[0];
            int y = coord[1];
            int [] response={-1,-1};
            if(!hasBeenShotAt(x, y)){
/**********************************************************************************************/
/********************* TODO SHOT
 *
 * itt leadtunk egy lövést oda, ahova még eddig soha, le kell kommunikálni az ellenféllel
 * ha talált,akkor mi jövünk,
 * ha nem talált, akkor visszalépünk a waitForShot methodbe *
 */
/**********************************************************************************************/
                //computingGUI();   //TODO include this to disable clicks
                //SEND COORDINATES
                //comInterface.SendData(coord);
                //WAIT FOR ANSWER
                //response = nullArray;
                //response = comInterface.ReceiveData();
                //HIT OR MISS
                try {
                    comInterface.SendData(coord);
                    response=comInterface.ReceiveData(); // visszakapjuk a két elemű tömböt ami tartalmazza a találatot és a süllyedést
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                if(x<y){    //comment out!
                if (response[0]==1){  //a tömb első eleme jelzi ha talált, azaz az értéke =1
                    shootAtEnemyShipsGUI(x, y, "HIT"); //jelezzük a táblán a találatot
                       /* if(nyertünk){
                            endOfGameGUI("WON");
                        }*/

                    myTurnGUI();     // juhu újra mi jövünk, újra szabad kattintani
                }
                else if (response[0]==0) {

                }
                }
                else{ //comment out!!
                    shootAtEnemyShipsGUI(x, y, "MISS"); //jelezzük a táblán a hibát
                    enemysTurnGUI(); // már nem mi jövünk, kattintások továbbra is letiltva
                    //waitForShot();
                }
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
