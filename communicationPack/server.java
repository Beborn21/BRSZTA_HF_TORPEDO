package communicationPack;

import java.io.*;
import java.net.ServerSocket;

public class server extends Network {

    final int portNUm;
    ServerSocket serverSocket;
    int x;

    public server(int portNum) throws Exception {
        this.portNUm=portNum;
        mode="SERVER";
        serverSocket = new ServerSocket(this.portNUm);
        socket=serverSocket.accept();

        outputStream= socket.getOutputStream();
        inputStream= socket.getInputStream();

    }



    /**
     * With this func. we can send a shot or a response for a shot.
     * @param data it contains a couple of coordinate (X,Y) or the fire result(hit,sunk). The hit and sunk positive-> value=1
     * @throws IOException smth
     */
    public void SendData(int data[]) throws IOException {
        String dataString= data.toString();

        outputStreamWriter=new OutputStreamWriter(outputStream);

        outputStreamWriter.write(dataString);
        outputStreamWriter.flush();
    }

    public int[] ReceiveData() throws IOException {

        inputStreamReader=new InputStreamReader(inputStream);
        stringBuffer=new StringBuffer();

        int data[]=new int[2];
        int temp1;
        int data1;
        int data2;
        String part1,part2;

        while(true)
        {
            x=inputStreamReader.read();
            if(x=='#' || x==-1) break;
            stringBuffer.append((char)x);
        }
        request=stringBuffer.toString();

        temp1=request.indexOf(",");
        part1=request.substring(0,temp1);
        part2=request.substring(temp1+1);

        data1=Integer.parseInt(part1);
        data2=Integer.parseInt(part2);
        data[1]=data1;
        data[2]=data2;

        return data;
    }


    @Override
    protected void finalize() throws Throwable {
        socket.close();
        super.finalize();
    }

    public int GetPortNUm(){
        return portNUm;
    }

// végső játékból törlendő
    public static void main(String[] args) throws Exception {

        Network serverSide=new server(2000);


        int [] b=new int[2];
        b[0]=1;
        b[1]=2;

        serverSide.SendData(b);
        System.out.println("send:"+b);

        b=serverSide.ReceiveData();
        System.out.println("receive:"+b);


    }

}


