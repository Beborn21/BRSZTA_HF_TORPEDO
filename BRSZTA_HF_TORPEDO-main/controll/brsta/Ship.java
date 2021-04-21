package brsta;

import java.util.ArrayList;
import java.util.Arrays;

public class Ship {
    private int ShipSegmentNumber;
    private ArrayList<ShipSegment> newSegmentArray;
    private boolean sinks=false;
    private int ShipID;
/*
    public Ship(int ShipSegmentNumber,ArrayList<ShipSegment> newSegmentArray,int ShipID){
    this.ShipSegmentNumber=ShipSegmentNumber;
    this.newSegmentArray  = newSegmentArray;
    this.ShipID=ShipID;
    }

 */

    public Ship(int ShipSegmentNumber,int ShipID){
        this.ShipSegmentNumber=ShipSegmentNumber;
        ArrayList<ShipSegment> newSegmentArray=new ArrayList<ShipSegment>() ;
        this.newSegmentArray  = newSegmentArray;
        this.ShipID=ShipID;
    }
    /*
    public Ship(int ShipSegmentNumber){
        this.ShipSegmentNumber=ShipSegmentNumber;
        ArrayList<ShipSegment> newSegmentArray=new ArrayList<ShipSegment>() ;
        this.newSegmentArray  = newSegmentArray;
    }

     */
    public int getShipID(){
        return this.ShipID;
    }

    public void addSegment(ShipSegment newShipSegment){

        this.newSegmentArray.add(newShipSegment);
    }

    public boolean fireShip(ShipSegment firedPlace ){
        for(ShipSegment actSegment:this.newSegmentArray){
            if (actSegment.isequalSegment(firedPlace)) {
        actSegment.HitSegment();
        return true;
            }
        }
        return false;
    }

    public boolean SinkShip() {
        for(ShipSegment actSegment:newSegmentArray) {

            if(actSegment.isHit()==false){
                return false;
            }

        }
        this.sinks = true;
        return true;
    }

    public boolean inShipSegment(ShipSegment ActShipSegment){
        for(ShipSegment actSegment:this.newSegmentArray) {

            if(actSegment.isequalSegment(ActShipSegment)){
                return true;
            }

        }
        return false;
    }


    public void printShip(){
        for(int i=0;i<this.newSegmentArray.size();i++){
            System.out.println(this.ShipID);
            System.out.println(this.newSegmentArray.get(i).getxKoord());
            System.out.println(this.newSegmentArray.get(i).getyKoord());

        }

    }

}
