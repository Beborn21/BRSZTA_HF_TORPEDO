package communicationPack;

import java.io.IOException;
import java.net.Socket;

public class Network {
    public Socket socket;

    public void sendData(int data[]) throws IOException {};

    public int[] receiveData() throws IOException {
        int []  a=new int[2];
        a[1]=0;
        a[2]=0;
         return a;
    };
    public int getPortNUm(){ return 0;};

    public void CloseConnection() throws IOException { socket.close();}
}
