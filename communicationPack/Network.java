package communicationPack;

import gui_and_control_pack.GamePanel;

import java.io.*;
import java.net.Socket;

public class Network {
    protected Socket socket;
    protected OutputStream outputStream;
    protected OutputStreamWriter outputStreamWriter;
    protected  InputStream inputStream;
    protected  InputStreamReader inputStreamReader;
    protected  StringBuffer stringBuffer;
    protected  String request;
    protected int[] resetSign={-100,-100};
    protected int[]  reConnectSign={-200,-200};
    protected GamePanel gamePanel;

    public void SendData(int data[]) throws IOException {};

    public int GetPortNUm(){ return 0;};

    public void CloseConnection() throws IOException { socket.close();}

    public void doRestart(){
        int [] reset={-100,-100,2};
        try {
            SendData(reset);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doReconnect() {

        int [] reset={-200,-200,2};
        try {
            SendData(reset);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
