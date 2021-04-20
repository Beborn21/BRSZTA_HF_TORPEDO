package brsta;
import java.lang.*;
import java.util.ArrayList;
import java.util.*;
import java.io.*;

public class MakeBoard {
    private int ShipNumber;
    private ArrayList<Ship>  newShipArray;
    private boolean endgame=false;
    private int [][] ShipMatrix= new int[10][10];


    private int getRandomNumberUsingInts(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }
   public MakeBoard(){
       int ShipConfigNumber=getRandomNumberUsingInts(1,7);

       //int ShipConfigNumber=1;

       String filename="src/"+String.valueOf(ShipConfigNumber)+".txt";
       this.ReadShipMatrix(filename);

       int flipHorisontalNumber=getRandomNumberUsingInts(0,2);
       int flipVerticalNumber=getRandomNumberUsingInts(0,2);
       int rotateNumber=getRandomNumberUsingInts(0,4);
       if(flipHorisontalNumber==1){
           this.flipHorisontal();
       }
       if(flipVerticalNumber==1){
           this.flipVertical();
       }
       int i=0;
       while(i<rotateNumber){
           this.rotate90Clockwise();
           i++;
       }

   }
    private  void ReadShipMatrix(String filename)  {
        try {
            Scanner input = new Scanner(new File(filename));
            int m = 10;
            int n = 10;

            while (input.hasNextLine()) {
                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        try{//    System.out.println("number is ");
                            this.ShipMatrix[i][j] = input.nextInt();

                        }
                        catch (java.util.NoSuchElementException e) {
                            // e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int [][] getShipMatrix(){
       return this.ShipMatrix;
    }

    private void rotate90Clockwise()
    {

        for (int i = 0; i < 10 / 2; i++) {
            for (int j = i; j < 10 - i - 1; j++) {

                int temp = this.ShipMatrix[i][j];
                this.ShipMatrix[i][j] = this.ShipMatrix[10 - 1 - j][i];
                this.ShipMatrix[10 - 1 - j][i] = this.ShipMatrix[10 - 1 - i][10 - 1 - j];
                this.ShipMatrix[10 - 1 - i][10 - 1 - j] = this.ShipMatrix[j][10 - 1 - i];
                this.ShipMatrix[j][10 - 1 - i] = temp;
            }
        }
    }

    private  void flipHorisontal() {
        for(int i = 0; i < (5); i++) {
            int [] temp = this.ShipMatrix[i];
            this.ShipMatrix[i] = this.ShipMatrix[10 - i - 1];
            this.ShipMatrix[10 - i - 1] = temp;
        }
    }

    private  void flipVertical() {

       for (int i=0; i<10; i++) {
           //start the second loop from the top
           for (int j = 0; j < 10/2; j++) {
               int temp = this.ShipMatrix[i][j];
               this.ShipMatrix[i][j]=this.ShipMatrix [i][10-j-1];
               this.ShipMatrix[i][10 - j - 1] = temp;
           }
       }
    }


    private Ship [] makefreeShipArray(){
        Ship [] freeShipArray=new Ship[10];
        freeShipArray[0]=new Ship(4,41);
        freeShipArray[1]=new Ship(3,31);
        freeShipArray[2]=new Ship(3,32);
        freeShipArray[3]=new Ship(2,21);
        freeShipArray[4]=new Ship(2,22);
        freeShipArray[5]=new Ship(2,23);
        freeShipArray[6]=new Ship(1,11);
        freeShipArray[7]=new Ship(1,12);
        freeShipArray[8]=new Ship(1,13);
        freeShipArray[9]=new Ship(1,14);
        return freeShipArray;
    }

    public Ship[] ShipMatrixtoShipArray(){
        Ship[] newShipArray=makefreeShipArray();
        for (int i=0; i<10;i++){
            for (int j=0; j<10;j++){
                if (ShipMatrix[i][j]!=0){
                    for(int k=0; k<10;k++){
                        if(newShipArray[k].getShipID()==ShipMatrix[i][j]){
                            ShipSegment newSegment=new ShipSegment(j,i);
                            newShipArray[k].addSegment(newSegment);
                        }
                    }

                }
            }
        }
       return newShipArray;
    }
}