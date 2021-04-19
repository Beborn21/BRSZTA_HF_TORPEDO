package torpedo_gui;

public class game_example {

    game_example(){}

    public void game(){

        //create gamewindow
        GameWindow gameWindow = new GameWindow();
        //open startpanel and wait for submitted info
        /*while(gameWindow.getStartpanel().getSubmitted() == 0){
            System.out.print("startpanel");
        };
        System.out.print("submitted");
        gameWindow.openGamePanel();*/
        boolean gamerunning = true;
        System.out.print("gamerun started");
        while (gamerunning) {
            String gameStatus = gameWindow.getStatus();
            if (gameStatus.equals("STARTPANEL")) {
                //check for submitted data
                int submitted = gameWindow.getStartpanel().getSubmitted();
                if (submitted == 1) {
                    gameWindow.getStartpanel().setSubmitted(0);
                    //If data is submitted, read port and mode
                    int port =  gameWindow.getStartpanel().getPort();
                    String mode = gameWindow.getStartpanel().getMode();
                    //after checking
                    gameWindow.openGamePanel();
                }else{
                    System.out.println("STARTPANEL else block");
                }

            } else if (gameStatus.equals("GAMEPANEL")) {
                if (gameWindow.getGamePanel().getRestart() == 1) {
                    System.out.print("restarted");
                    gameWindow.openGamePanel();
                } else if (gameWindow.getGamePanel().getReconnect() == 1) {
                    //gamerunning=false;
                    System.out.print("reconnected");
                    gameWindow.openStartPanel();
                    System.out.print("opened startpanel");
                } else if (gameWindow.getGamePanel().getClicked() == 1) {
                    gameWindow.getGamePanel().setClicked(0);
                    System.out.println("clicked: ");
                    // Koordináták kiolvasása
                    int x = gameWindow.getGamePanel().getXCoordinate();
                    int y = gameWindow.getGamePanel().getYCoordinate();
                    //Lövések leadása, megfelelő HIT/MISS és koordináta paraméterekkel
                    gameWindow.getGamePanel().shotAtEnemyShipsGUI(gameWindow.getGamePanel().getXCoordinate(), gameWindow.getGamePanel().getYCoordinate(), "HIT");
                    gameWindow.getGamePanel().shotAtMyShipsGUI(gameWindow.getGamePanel().getXCoordinate(), gameWindow.getGamePanel().getYCoordinate(), "HIT");
                }
            }else{
                gamerunning=false;
                System.out.println("gamerun false block");
            }

            System.out.println("gamerun");

        }


    }

}
