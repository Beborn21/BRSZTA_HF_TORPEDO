package brsta;
import java.util.Arrays;
public class Main {

    public static void main(String[] args) {
	// write your code here





        GameBoard board=new GameBoard();
        board.printShipArray();
        ShipSegment s=new ShipSegment(1,1);



        boolean loves1= board.fireGameBoard(new ShipSegment(1,1));
        boolean loves2= board.fireGameBoard(new ShipSegment(2,1));
        boolean loves3= board.fireGameBoard(new ShipSegment(3,1));
        boolean loves4= board.fireGameBoard(new ShipSegment(4,1));

        boolean loves5= board.fireGameBoard(new ShipSegment(9,0));

        boolean loves6= board.fireGameBoard(new ShipSegment(7,1));
        boolean loves7= board.fireGameBoard(new ShipSegment(7,2));
        boolean loves8= board.fireGameBoard(new ShipSegment(7,3));

        boolean loves9= board.fireGameBoard(new ShipSegment(1,3));
        boolean loves10= board.fireGameBoard(new ShipSegment(2,3));
        boolean loves11= board.fireGameBoard(new ShipSegment(3,3));

        boolean loves12= board.fireGameBoard(new ShipSegment(5,4));
        boolean loves13= board.fireGameBoard(new ShipSegment(5,5));

        boolean loves14= board.fireGameBoard(new ShipSegment(2,5));
        boolean loves15= board.fireGameBoard(new ShipSegment(2,6));

        boolean loves16= board.fireGameBoard(new ShipSegment(4,7));
        boolean loves17= board.fireGameBoard(new ShipSegment(5,7));

        boolean loves18= board.fireGameBoard(new ShipSegment(8,5));

        boolean loves19= board.fireGameBoard(new ShipSegment(8,8));

        boolean loves20= board.fireGameBoard(new ShipSegment(1,8));

        boolean vege= board.EndGame();
        System.out.println(loves1);
        System.out.println(loves2);
        System.out.println(loves3);
        System.out.println(loves4);

        System.out.println(vege);











    }
}
