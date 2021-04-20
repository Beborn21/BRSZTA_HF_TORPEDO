package brsta;


public class GameBoard {
    private int ShipNumber = 10;
    private Ship[] newShipArray;
    private boolean endgame = false;

    public GameBoard() {
        MakeBoard board=new MakeBoard();
        Ship[] newShipArray= board.ShipMatrixtoShipArray();
        this.newShipArray = newShipArray;
    }

    public boolean EndGame() {
        for (Ship actShip : newShipArray) {

            if (actShip.SinkShip() == false) {
                return false;
            }

        }
        this.endgame = true;
        return true;
    }

    public boolean fireGameBoard(ShipSegment firedPlace) {
        for (Ship actShip : this.newShipArray) {
            if(actShip.inShipSegment(firedPlace)) {
            actShip.fireShip(firedPlace);
            return true;
            }

        }
        return false;
    }

    public Ship [] getShip(){

        return this.newShipArray;
    }

    public void printShipArray(){
        System.out.println(this.newShipArray.length);
        for(int i=0; i<10; i++){
            this.newShipArray[i].printShip();

        }
    }

}
