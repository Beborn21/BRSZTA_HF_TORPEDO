package gui_and_control_pack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPanel extends JPanel implements ActionListener {

    JButton clientbutton;
    JButton serverbutton;
    String mode = "SERVER";
    JTextField portInputTextField;
    int port;
    JButton submitbutton;

    int buttonpadding = 350;
    int buttonsize = 140;
    int bordersizechosen = 7;
    int bordersizenotchosen = 2;

    StartPanel(){

        this.setBounds(0, 0, 1300 , 750 );
        this.setBackground(GamePanel.seacolor);
        this.setLayout(null);
        this.addModeQuieryLabel();
        this.addClientButton();
        this.addServerButton();
        this.addPortQuieryLabel();
        this.addPortInputTextField();
        this.addSubmitButton();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== submitbutton){
            submitbutton.setBorder(BorderFactory.createMatteBorder(
                    bordersizechosen, bordersizechosen, bordersizechosen, bordersizechosen,   GamePanel.targetaquiredcolor));
            port = Integer.parseInt(portInputTextField.getText());
            this.setVisible(false);
        }else if(e.getSource()== clientbutton){
            clientbutton.setBorder(BorderFactory.createMatteBorder(
                    bordersizechosen, bordersizechosen, bordersizechosen, bordersizechosen,   GamePanel.targetaquiredcolor));
            serverbutton.setBorder(BorderFactory.createMatteBorder(
                    bordersizenotchosen, bordersizenotchosen, bordersizenotchosen, bordersizenotchosen,   GamePanel.targetaquiredcolor));
            mode = "CLIENT";
            System.out.println("client");
        }else if(e.getSource()== serverbutton){
            serverbutton.setBorder(BorderFactory.createMatteBorder(
                    bordersizechosen, bordersizechosen, bordersizechosen, bordersizechosen,   GamePanel.targetaquiredcolor));
            clientbutton.setBorder(BorderFactory.createMatteBorder(
                    bordersizenotchosen, bordersizenotchosen, bordersizenotchosen, bordersizenotchosen,   GamePanel.targetaquiredcolor));
            mode = "SERVER";
            System.out.println("server");
        }
    }

    private void addClientButton(){
        clientbutton = new JButton();
        clientbutton.setBounds( 550, 220, 100,35);
        clientbutton.setVisible(true);
        clientbutton.setText("CLIENT");
        clientbutton.setFont(GameWindow.PixelFont20);
        clientbutton.setBackground(GamePanel.backgroundcolor);
        clientbutton.setBorder(BorderFactory.createMatteBorder(
                bordersizenotchosen, bordersizenotchosen, bordersizenotchosen, bordersizenotchosen,   GamePanel.targetaquiredcolor));
        clientbutton.setForeground(GamePanel.navylabelcolor);
        clientbutton.addActionListener(this);
        this.add(clientbutton);
    }

    private void addServerButton(){
        serverbutton = new JButton();
        serverbutton.setBounds( 350, 220, 100,35);
        serverbutton.setVisible(true);
        serverbutton.setText("SERVER");
        serverbutton.setFont(GameWindow.PixelFont20);
        serverbutton.setBackground(GamePanel.backgroundcolor);
        serverbutton.setBorder(BorderFactory.createMatteBorder(
                bordersizechosen, bordersizechosen, bordersizechosen, bordersizechosen,   GamePanel.targetaquiredcolor));
        serverbutton.setForeground(GamePanel.navylabelcolor);
        serverbutton.addActionListener(this);
        this.add(serverbutton);
    }

    private void addSubmitButton(){
        submitbutton = new JButton();
        submitbutton.setBounds( 450, 500, 100,35);
        submitbutton.setVisible(true);
        submitbutton.setText("SUBMIT");
        submitbutton.setFont(GameWindow.PixelFont20);
        submitbutton.setBackground(GamePanel.backgroundcolor);
        submitbutton.setBorder(BorderFactory.createMatteBorder(
                bordersizenotchosen, bordersizenotchosen, bordersizenotchosen, bordersizenotchosen,   GamePanel.targetaquiredcolor));
        submitbutton.setForeground(GamePanel.navylabelcolor);
        submitbutton.addActionListener(this);
        this.add(submitbutton);
    }

    private void addPortInputTextField(){
        portInputTextField = new JTextField();
        portInputTextField.setBounds(250, 400, 500, 25);
        portInputTextField.setVisible(true);
        portInputTextField.setBackground(GamePanel.navylabelcolor);
        portInputTextField.setFont(new Font("Monospaced", Font.BOLD, 20));
        portInputTextField.setBorder(null);
        portInputTextField.getCaret().setVisible(true);
        portInputTextField.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(portInputTextField);
    }

    private void addPortQuieryLabel(){
        JLabel label = new JLabel("ENTER PORT NUMBER:", SwingConstants.CENTER);
        label.setSize(1000, 100);
        label.setLocation(0, 300);
        label.setForeground(GamePanel.navylabelcolor);
        label.setFont(GameWindow.PixelFont50);
        label.setVisible(true);
        this.add(label);
    }

    private void addModeQuieryLabel(){
        JLabel label = new JLabel("CHOOSE ONE:", SwingConstants.CENTER);
        label.setSize(1000, 100);
        label.setLocation(0, 100);
        label.setForeground(GamePanel.navylabelcolor);
        label.setFont(GameWindow.PixelFont50);
        label.setVisible(true);
        this.add(label);
    }

    public int getPort(){return port;}
    public String getMode(){return mode;}

}
