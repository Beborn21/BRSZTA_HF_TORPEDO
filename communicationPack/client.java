package communicationPack;

import java.net.*;
import java.io.*;
import java.util.Arrays;

public class client extends Network {

     final int portNUm;

    public client(int portNum) throws Exception {
        this.portNUm=portNum;
        mode="CLIENT";
        socket  = new Socket("localhost",this.portNUm);

        outputStream=socket.getOutputStream();
        inputStream=socket.getInputStream();
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
        System.out.println("Sent"+dataString);

    }

    public int[] ReceiveData() throws IOException {
        int x=0;
        inputStreamReader=new InputStreamReader(inputStream);
        stringBuffer=new StringBuffer();

        int []data=new int[2];
        int temp1,temp2;
        int data1;
        int data2;
        String part1,part2;

        while(true)
        {
            x=inputStreamReader.read();
            stringBuffer.append((char)x);
            if(x==']') break;
        }
        request=stringBuffer.toString();

        temp1=request.indexOf(",");
        part1=request.substring(1,temp1);
        temp2=request.indexOf("]");
        part2=request.substring(temp1+2,temp2);

        data1=Integer.parseInt(part1);
        data2=Integer.parseInt(part2);
        data[0]=data1;
        data[1]=data2;

        return data;
    }

     public boolean startGame() throws IOException {
        int[] newGame={-1,-1};
        int [] response=new int[2];
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



