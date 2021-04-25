package communicationPack;

import gui_and_control_pack.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.Arrays;

public class client extends Network {

     final int portNUm;

    public client(int portNum, GamePanel gamePanel) throws Exception {
        this.portNUm=portNum;
        this.gamePanel=gamePanel;
        socket  = new Socket("localhost",this.portNUm);

        outputStream=socket.getOutputStream();
        inputStream=socket.getInputStream();

        inputStreamReader = new InputStreamReader(inputStream);
        outputStreamWriter=new OutputStreamWriter(outputStream);

        outputStreamWriter.flush();
        Thread receive = new Thread(new ReceiverThreadClient());
        receive.start();
    }

    /**
     * With this func. we can send a shot or a response for a shot.
     * @param data it contains a couple of coordinate (X,Y) or the fire result(hit,sunk). The hit and sunk positive-> value=1
     * @throws IOException smth
     */
    public void SendData(int data[]) throws IOException {
        String dataString= Arrays.toString(data);

        outputStreamWriter.write(dataString);
        outputStreamWriter.flush();
        System.out.println("Sent C->S"+dataString);

    }

    public int GetPortNUm(){
        return portNUm;
    }


    private class ReceiverThreadClient implements Runnable {

        public void run() {
            System.out.println("ClientSide Listener Will Run");
            inputStreamReader = new InputStreamReader(inputStream);
            try {
                while (true) {
                    int x = 0;

                    stringBuffer = new StringBuffer();

                    int[] data = new int[2];
                    int temp1, temp2, temp3;
                    int data1, data2, data3;


                    String part1, part2, part3;

                    while (true) {
                        x = inputStreamReader.read();
                        stringBuffer.append((char) x);
                        if (x == ']') break;
                    }
                    request = stringBuffer.toString();
                    System.out.println("\n ClientSide Listener get data");
                   temp1 = request.indexOf(",");
                    part1 = request.substring(1, temp1);
                    temp2 = request.indexOf(",", temp1 + 1);
                    part2 = request.substring(temp1 + 2, temp2);
                    temp3 = request.indexOf("]");
                    part3 = request.substring(temp2 + 2, temp3);




                    data1 = Integer.parseInt(part1);
                    data2 = Integer.parseInt(part2);
                    data3 = Integer.parseInt(part3);

                    data[0] = data1;
                    data[1] = data2;

                    switch (data3) {
                        case 0:
                            System.out.println("Call Wait F shot");
                            gamePanel.waitForShot(data);    // receive shot
                            break;
                        case 1:
                            System.out.println("Call Repo F shot");
                            gamePanel.responseForShot(data);   // receive response
                            break;
                        case 2:
                            if (Arrays.equals(data, resetSign)) {
                                gamePanel.restartFunc();
                            }else if(Arrays.equals(data,reConnectSign)){gamePanel.reconnectFunc();} //receive restart
                            break;
                        default:
                            System.err.println("Invalid data in ReceiverThread ");
                    }

                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                System.err.println("Client disconnected!");
            } finally {
                try {
                    CloseConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}



