package torpedo_gui;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameWindow extends JFrame {

    GamePanel gamePanel;
    StartPanel startpanel;
    static Font PixelFont50;
    static Font PixelFont20;
    String status = "START";


    GameWindow(){

        try{
            // load a custom font in your project folder
            PixelFont50 = Font.createFont(Font.TRUETYPE_FONT, new File("src/torpedo_gui/PixelMplus12-Bold.ttf")).deriveFont(50f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/torpedo_gui/PixelMplus12-Bold.ttf")));
        }
        catch(IOException | FontFormatException e){ }

        try{
            // load a custom font in your project folder
            PixelFont20 = Font.createFont(Font.TRUETYPE_FONT, new File("src/torpedo_gui/PixelMplus12-Bold.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/torpedo_gui/PixelMplus12-Bold.ttf")));
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
        this.repaint();
    }
    public void openGamePanel(){
        this.status = "GAMEPANEL";
        this.getContentPane().removeAll();
        System.out.print("removed");
        gamePanel = new GamePanel();
        this.getContentPane().add(gamePanel, BorderLayout.CENTER);
        System.out.print("added");
        this.repaint();
        System.out.print("repainted");
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
    public StartPanel getStartpanel() {
        return startpanel;
    }
    public String getStatus() {
        return status;
    }

    public void enableShooting(){
        if(status=="GAMEPANEL"){
            gamePanel.enableShooting();
        }
    }
    public void disableShooting(){
        if(status=="GAMEPANEL"){
            gamePanel.disableShooting();
        }
    }

    static Font getPixelFont50(){ return PixelFont50; }
    static Font getPixelFont20(){ return PixelFont20; }

}
