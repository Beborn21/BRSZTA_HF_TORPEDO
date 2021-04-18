package communicationPack;

import java.io.IOException;
import java.util.Arrays;

public class Control {

    public static void main(String[] args) throws Exception {
        /*
        1. GUI példányosítása- játék indulása
        konfigurációs beállítások-> kliens/szerver mód + port Number (kb:0-6000 tartományon bármi)
             */
           // Hálózati rész indulása

        Network comInterface;
        int portNum=2000;       // törlendő-------

        String mode="SERVER";   //törlendő-------
            if(mode=="SERVER"){
              comInterface=new server(portNum);
            }
            else{
              comInterface=new client(portNum);
            }

            //Board példányosítása

           // játék indítása----

        //megbizonyosodunk hogy a másik oldal is eljutott-e már idáig --> "szinkronizálunk"
        boolean isStart=comInterface.startGame();
        while(isStart==false) {
            isStart = comInterface.startGame();
        }

        String state="IDLE";

        //mi kezdünk ha mi vagyunk a szerver
        if (comInterface.getMode() == "SERVER") {
            state="shot";
        }

        int[] coordinates=new int[2];
        int[] response=new int[2];
        int[] nullArray={-1,-1};

        while(true) {
            switch (state) {
                case "waitForShot":
                    coordinates = nullArray;
                    coordinates = comInterface.ReceiveData();

                    /*if(minden hajónk kilőve ==true){
                    state="eoGame";
                    }
                     */
                    break;
                case "shot":
                   // comInterface.SendData(coordinates); // ide kellenek az általunk kiadott lövés koordinátái egy kételemű tömbbe
                    state="waitForResponse";
                    break;

                case "waitForResponse":
                        response = nullArray;
                        response = comInterface.ReceiveData();

                        if (response[0]==1){  //a tömb első eleme jelzi ha talált, azaz az értéke =1

                            state="shot";     // juhu újra mi jövünk
                        }
                        else if (response[0]==0){
                            state="waitForShot"; // már nem mi jövünk
                        }else{
                            //hibás érték
                             }
                        break;

                case "eoGame":
                    break;

                case "newGame":
                    break;

                case "exitGame":
                    comInterface.CloseConnection();
                    break;

                default: //kaki a levesben
            }


        }



    }

}
