package communicationPack;

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
    protected String mode;

public  String getMode(){return  mode;}

    public void SendData(int data[]) throws IOException {};

    public int[] ReceiveData() throws IOException {
        int []  a=new int[2];
        a[1]=-1;
        a[2]=-1;
         return a;
    };
    public int GetPortNUm(){ return 0;};

    public void CloseConnection() throws IOException { socket.close();}

    public boolean startGame() throws IOException {return false;}

    public void doRestart(){
        int [] reset={-100,-100};
        try {
            SendData(reset);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
