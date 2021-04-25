package gui_and_control_pack;
import communicationPack.Network;
import communicationPack.client;
import communicationPack.server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;

public class GameWindow extends JFrame implements ComponentListener {

    GamePanel gamePanel;
    StartPanel startpanel;
    String mode = "SERVER";
    int port = -1;
    static Font PixelFont50;
    static Font PixelFont20;
    String status = "START";

    GameWindow(){

        try{
            // load a custom font in your project folder
            PixelFont50 = Font.createFont(Font.TRUETYPE_FONT, new File("gui_and_control_pack/PixelMplus12-Bold.ttf")).deriveFont(50f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("gui_and_control_pack/PixelMplus12-Bold.ttf")));
        }
        catch(IOException | FontFormatException e){ }

        try{
            // load a custom font in your project folder
            PixelFont20 = Font.createFont(Font.TRUETYPE_FONT, new File("gui_and_control_pack/PixelMplus12-Bold.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("gui_and_control_pack/PixelMplus12-Bold.ttf")));
        }
        catch(IOException | FontFormatException e){ }


        this.setBounds(0,0,1014,737);
        this.setTitle("Battleship");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.openStartPanel();
        this.repaint();

    }

    public void openStartPanel(){
        this.status = "STARTPANEL";
        this.getContentPane().removeAll();
        startpanel=new StartPanel();
        this.getContentPane().add(startpanel);
        startpanel.addComponentListener(this);
        this.repaint();
    }
    public void openGamePanel(int port, String mode){
        this.status = "GAMEPANEL";
        this.getContentPane().removeAll();

        System.out.print("removed");
        gamePanel = new GamePanel(port,mode);
        this.getContentPane().add(gamePanel, BorderLayout.CENTER);
        System.out.print("added");
        gamePanel.addComponentListener(this);
        this.repaint();
        System.out.print("repainted");
    }


    @Override
    public void componentResized(ComponentEvent e) { }

    @Override
    public void componentMoved(ComponentEvent e) { }

    @Override
    public void componentShown(ComponentEvent e) { }

    @Override
    public void componentHidden(ComponentEvent e) {

        if(e.getSource()== startpanel){
            mode = startpanel.getMode();
            port = startpanel.getPort();
            openGamePanel(port, mode);
        }else  if(e.getSource()== gamePanel){
            if(gamePanel.getRestart()){
                openGamePanel(port, mode);
            }else if(gamePanel.getReconnect()){
                openStartPanel();
            }


        }

    }
}
