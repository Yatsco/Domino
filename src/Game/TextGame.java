/*
Ran out of time to reimplement the text game in a more modular way









package Game;

import GameStructure.DominoGame;
import GameStructure.DominoPiece;

import java.util.Scanner;

public class TextGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameLogic game = new GameLogic();
        game.createDominos();
        game.draw();
        while(!game.gameOver()){
            if (game.turn==true){
                System.out.println(game.playerHand);
                System.out.println("Select a Domino");
                game.setDominoInHand(scanner.nextInt());
                DominoPiece pieceToPlace =  game.selectPiece();
                //System.out.println(pieceToPlace);
                //System.out.println(game.gameStartnum);

            if (game.gameStartnum==1){
                game.placePiece(pieceToPlace);
                game.setGameStartnum(2);
                game.getPlayerHand().remove(pieceToPlace);
                System.out.println(game.gameBoard);

            }
            else if(game.turn==true){
                //System.out.println("Select a Domino");
                //game.setDominoInHand(scanner.nextInt());
                game.placePiece(pieceToPlace);
                game.getPlayerHand().remove(pieceToPlace);
                System.out.println(game.gameBoard);

            }




            }

        }
    }

}
*/
