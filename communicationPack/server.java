package communicationPack;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class server extends Network {

    final int portNUm;
    ServerSocket serverSocket;


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
        String dataString= Arrays.toString(data);

        outputStreamWriter=new OutputStreamWriter(outputStream);

        outputStreamWriter.write(dataString);
        outputStreamWriter.flush();
    }

     public int[] ReceiveData() throws IOException {
        int x=0;
        inputStreamReader=new InputStreamReader(inputStream);
        stringBuffer=new StringBuffer();

        int []data=new int[3];
        int temp1,temp2,temp3;
        int data1;
        int data2;
        int data3;
        String part1,part2,part3;

        while(true)
        {
            x=inputStreamReader.read();
            stringBuffer.append((char)x);
            if(x==']') break;
        }
        request=stringBuffer.toString();

        temp1=request.indexOf(",");
        part1=request.substring(1,temp1);
        temp2=request.indexOf(",",temp1+1);
        part2=request.substring(temp1+2,temp2);

        temp3=request.indexOf("]");
        part3=request.substring(temp2+2,temp3);

        data1=Integer.parseInt(part1);
        data2=Integer.parseInt(part2);
        data3=Integer.parseInt(part3);
        data[0]=data1;
        data[1]=data2;
        data[2]=data3;

        return data;
    }

    public boolean startGame() throws IOException {
        int[] newGame={-1,-1,-1};
        int [] response=new int[3];
        boolean isStart=false;

        SendData(newGame);
        response=ReceiveData();

        if(Arrays.equals(response ,newGame)){
            isStart=true;
        }
        return isStart;
    }

    public int GetPortNUm(){
        return portNUm;
    }


}


