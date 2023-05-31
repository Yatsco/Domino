package GameStructure;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Jarek Yatsco
 * 2/9/21
 */


public class DominoGame {
    final int DOMINOS = 27;
    int gameStartnum = 0;
    int topDomino = 0;
    boolean start = true;
    List<DominoPiece> dominoDeck = new LinkedList<DominoPiece>();
    List<DominoPiece> playerHand = new LinkedList<DominoPiece>();
    List<DominoPiece> computerHand = new LinkedList<DominoPiece>();
    List<DominoPiece> gameBoard = new LinkedList<DominoPiece>();
    Scanner scanner = new Scanner(System.in);
    boolean turn = true;
    boolean match;
    boolean flip;
    int myDeleteDomino;
    boolean leftSideCase;
    int gameEndDraw = 0;
    boolean guiMode = false;
    String input = "";

    /**
     * Where we call everything to run the game
     */
    public static void main(String[] args) {
        DominoGame game = new DominoGame();
        game.createDominos();
        game.draw();

        while (!game.gameOver()) {
            game.pickDomino();
            System.out.println("Dominos from 0 to " + game.playerHand.size()
                    + " Player Hand:  " + game.playerHand.toString());
            game.rowOffset();
            System.out.println("Boneyard size:  " + game.dominoDeck.size());

        }


    }

    /**
     * Creates the dominos in the deck
     * and shuffles the deck
     */
    public void createDominos() {

        for (int left = 0; left < 7; left++) {
            for (int right = left; right < 7; right++) {
                DominoPiece dominoPiece = new DominoPiece(left, right);
                dominoDeck.add(dominoPiece);


            }


        }
        Collections.shuffle(dominoDeck);
        topDomino = dominoDeck.size() - 1;
    }

    /**
     * @param myDomino      the callers domino
     * @param gameDominonum the number of the dominos open side
     * @return -2 if the piece matches both sides.
     * the side of the callers domino that matches the number of the open part
     * on the board.
     * -1 if there is no match
     */
    public int sideMatch(DominoPiece myDomino, int gameDominonum) {
        if (gameDominonum == 0) {
            return -2;
        }
        if (((myDomino.leftNum == gameDominonum) || (myDomino.leftNum == 0))
                && ((myDomino.rightNum == gameDominonum) ||
                (myDomino.rightNum == 0))) {
            return -2;

        } else if ((myDomino.leftNum == gameDominonum) ||
                (myDomino.leftNum == 0)) {
            return myDomino.leftNum;

        } else if ((myDomino.rightNum == gameDominonum) ||
                (myDomino.rightNum == 0)) {
            return myDomino.rightNum;
        }
        return -1;

    }

    /**
     * This ended up carrying the entire program
     * picks a piece on the board
     * checks to see if the piece is valid
     *
     * @param myPiece The players Piece
     * @return the players piece to play on the board
     */
    public DominoPiece pickBoardDomino(DominoPiece myPiece) {
        input = "";


        if (start == true) {
            System.out.println("Flip starting piece? (y/n)");
            input = scanner.next();
            if (input.equals("y")) {
                start = false;
                flip = true;
                turn = false;

                return myPiece;

            } else {
                start = false;
                turn = false;

                return myPiece;
            }


        } else if (turn == true) {
            computerPlayer();
            System.out.println("Pick a domino on the board to match to (l/r)");
            input = scanner.next();
            if (input.equals("r")) {
                leftSideCase = false;

                int matchNum = sideMatch(myPiece,
                        gameBoard.get(gameBoard.size() - 1).rightNum);
                if (matchNum == -2) {
                    System.out.println("Flip piece? (y/n)");
                    input = scanner.next();
                    flip = input.equals("y");
                    return myPiece;


                }
                DominoPiece rightSideDomino = gameBoard.get
                        (gameBoard.size() - 1);
                int rightSidenum = rightSideDomino.getRightNum();
                if (rightSidenum == 0) {
                    flip = false;
                    return myPiece;


                } else if (matchNum != -1) {
                    if (matchNum == myPiece.getRightNum()) {
                        flip = true;
                        return myPiece;
                    } else if (matchNum == myPiece.getLeftNum()) {
                        flip = false;
                        return myPiece;

                    }
                }

            } else if (input.equals("l")) {
                leftSideCase = true;
                DominoPiece leftSideDomino = gameBoard.get(0);
                int matchNum = sideMatch(myPiece, gameBoard.get(0).leftNum);
                if (matchNum == -2) {
                    System.out.println("Flip piece? (y/n)");
                    input = scanner.next();
                    flip = input.equals("y");
                    return myPiece;


                }
                int leftidenum = leftSideDomino.getLeftNum();
                if (leftidenum == 0) {
                    return myPiece;

                } else if (matchNum != -1) {
                    if (matchNum == myPiece.getRightNum()) {
                        flip = false;
                        return myPiece;
                    } else if (matchNum == myPiece.getLeftNum()) {
                        flip = true;
                        return myPiece;

                    }

                }


            } else {
                return null;
            }

        }


        return null;

    }

    /**
     *  sideMatch(piece,gameBoard.get(i).leftNum);
     *         sideMatch(piece,gameBoard.get(i).rightNum);
     *         if (sideMatch(piece,gameBoard.get(i).leftNum)!=-1||
     *                 sideMatch(piece,gameBoard.get(i).rightNum)!=-1){
     *             return true;
     *         }
     *         **/

    /**
     * places a domino on the board if all checks are met
     *
     * @param piece the players piece
     * @return null if the player gave invalid piece
     */
    public boolean placePiece(DominoPiece piece) {
        String userInput = "";

        if (turn == true) {

            if (guiMode == true) {
                userInput = "y";
                if (userInput.equals("y")) {
                    piece = pickBoardDomino(piece);

                    if (piece == null) {
                        return false;
                    }
                    if (flip == false) {

                        playerHand.remove(myDeleteDomino);
                        if (leftSideCase == true) {
                            gameEndDraw = 0;
                            gameBoard.add(0, piece);

                        } else {
                            gameEndDraw = 0;
                            gameBoard.add(piece);

                        }
                        return true;
                    } else if (flip = true) {

                        piece.flip(piece);
                        playerHand.remove(myDeleteDomino);
                        if (leftSideCase == true) {
                            gameEndDraw = 0;
                            gameBoard.add(0, piece);
                        } else {
                            gameEndDraw = 0;
                            gameBoard.add(piece);

                        }
                        return true;
                    }
                }

            }



            System.out.println("Place this piece? (y/n)");
            userInput = scanner.next();
            if (userInput.equals("y")) {
                piece = pickBoardDomino(piece);

                if (piece == null) {
                    return false;
                }
                if (flip == false) {

                    playerHand.remove(myDeleteDomino);
                    if (leftSideCase == true) {
                        gameEndDraw = 0;
                        gameBoard.add(0, piece);

                    } else {
                        gameEndDraw = 0;
                        gameBoard.add(piece);

                    }
                    return true;
                } else if (flip = true) {

                    piece.flip(piece);
                    playerHand.remove(myDeleteDomino);
                    if (leftSideCase == true) {
                        gameEndDraw = 0;
                        gameBoard.add(0, piece);
                    } else {
                        gameEndDraw = 0;
                        gameBoard.add(piece);

                    }
                    return true;
                }
            } else {
                return false;
            }


        } else {
            computerPlayer();
            return false;
        }

        return false;

    }

    /**
     * checks if in the hand there is a valid move to be played
     *
     * @param hand the hand of the caller
     * @return true if there is a valid move
     */
    public boolean anyValidPiece(List<DominoPiece> hand) {

        if (gameStartnum == 0) {
            return true;
        }

        for (int i = 0; i < hand.size(); i++) {
            DominoPiece piece = hand.get(i);


            if (start == true) {
                return true;
            }

            if (gameBoard.get(gameBoard.size() - 1) == gameBoard.get(0)) {
                if (piece.match(piece,
                        gameBoard.get(gameBoard.size() - 1)) == true) {
                    return true;

                }


            }
            if (sideMatch(piece,
                    gameBoard.get(gameBoard.size() - 1).rightNum) != -1) {
                return true;
            }
            if (sideMatch(piece, gameBoard.get(0).leftNum) != -1) {
                return true;
            }


        }
        return false;

    }

    /**
     * checks if we have a valid move if we do then the player gets to play
     * their domino
     *
     * @return null to break out of do while
     */
    public DominoPiece pickDomino() {


        if (turn == true) {
            do {


                if (anyValidPiece(playerHand) == false && gameOver() == false) {
                    draw();
                    gameEndDraw++;
                    turn = false;
                    return null;
                }

                do {

                    if (guiMode == true) {
                        if (myDeleteDomino > playerHand.size() - 1 ||
                                myDeleteDomino < 0) {
                            System.out.println("Domino not found Try again");
                        }


                    }

                    else {


                        System.out.println("Pick a domino");
                        myDeleteDomino = scanner.nextInt();
                        if (myDeleteDomino > playerHand.size() - 1 ||
                                myDeleteDomino < 0) {
                            System.out.println("Domino not found Try again");
                        }

                    }


                }
                while (myDeleteDomino > playerHand.size() - 1 ||
                        myDeleteDomino < 0);


                System.out.println(playerHand.get(+myDeleteDomino).toString());
                placePiece(playerHand.get(myDeleteDomino));


                return null;

            }

            while (placePiece(playerHand.get(myDeleteDomino)) != false);
        } else {
            turn = false;
            computerPlayer();
            turn = true;

        }
        return null;


    }

    /**
     * Computer AI
     *
     * @param piece the piece the computer wants to play
     * @return the piece if it passes all checks
     */
    public boolean computerDominoChecker(DominoPiece piece) {

        if (true) {
            int left = gameBoard.get(0).leftNum;
            int right = gameBoard.get(gameBoard.size() - 1).rightNum;
            if (piece.match(piece, gameBoard.get(0)) == true) {
                int matchNumLeft = sideMatch(piece, left);
                if (matchNumLeft != -1) {
                    if (matchNumLeft == piece.rightNum) {
                        gameBoard.add(0, piece);
                        turn = true;
                        return true;
                    } else {
                        piece.flip(piece);
                        gameBoard.add(0, piece);
                        turn = true;
                        return true;

                    }

                }


            } else if (piece.match(piece,
                    gameBoard.get(gameBoard.size() - 1)) == true) {
                int matchNumRight = sideMatch(piece, right);
                if (matchNumRight != -1) {
                    if (matchNumRight == piece.rightNum) {
                        piece.flip(piece);
                        gameBoard.add(piece);
                        turn = true;
                        return true;
                    } else {
                        gameBoard.add(piece);
                        turn = true;
                        return true;

                    }

                }

            }


        }
        return false;


    }

    /**
     * calls computer ai function with every domino on its hand
     * if it finds a valid play it plays then ends turn
     */
    public void computerPlayer() {
        turn = false;
        boolean cpuChecker = false;

        if (turn == false) {
            for (int i = 0; i < computerHand.size(); i++) {

                cpuChecker = computerDominoChecker(computerHand.get(i));
                if (cpuChecker == true) {
                    gameEndDraw = 0;
                    computerHand.remove(i);
                    turn = true;
                    break;
                }


            }
            if (cpuChecker == false) {

                draw();
                System.out.println("COMPUTERR HAND" + computerHand.toString());
            }

        }

    }

    /**
     * the offset of the rows to print the board
     */
    public void rowOffset() {
        String rowOne = "";
        String rowTwo = "    ";
        for (int i = 0; i < gameBoard.size(); i++) {
            if (i % 2 == 0) {
                rowOne += gameBoard.get(i).toString();

            } else {
                rowTwo += gameBoard.get(i).toString();
            }

        }
        System.out.println(rowOne);
        System.out.println(rowTwo);
    }

    /**
     * checks whether all game over measures are met to end the game
     *
     * @return true if game is over
     * false if game is still being played
     */
    public boolean gameOver() {


        if (dominoDeck.size() == 0 || gameEndDraw == 2) {
            if (anyValidPiece(computerHand) == false ||
                    anyValidPiece(playerHand) == false) {
                int playerHandNum = 0;
                int computerHandNum = 0;
                for (int i = 0; i < playerHand.size(); i++) {
                    playerHandNum = playerHand.get(i).rightNum +
                            playerHand.get(i).leftNum;

                }
                for (int i = 0; i < computerHand.size(); i++) {
                    computerHandNum = computerHand.get(i).rightNum +
                            computerHand.get(i).leftNum;

                }
                if (playerHandNum > computerHandNum) {
                    System.out.printf("Computer Wins");
                    return true;


                } else if (playerHandNum < computerHandNum) {
                    System.out.printf("You Win!");
                    return true;

                } else {
                    boolean winner = !turn;
                    if (winner == true) {
                        System.out.println("You Win!");
                        return true;
                    } else {
                        System.out.printf("Computer Wins");
                        return true;
                    }

                }

            }

            return false;
        }
        return false;


    }

    /**
     * Draws a Domino from the domino deck
     */
    public void draw() {
        if (gameStartnum == 0) {
            for (int draws = 0; draws < 7; draws++) {
                playerHand.add(dominoDeck.get(topDomino));
                dominoDeck.remove(topDomino);
                topDomino--;
                computerHand.add(dominoDeck.get(topDomino));
                dominoDeck.remove(topDomino);
                topDomino--;


            }
            gameStartnum = 1;

        } else {
            if (topDomino != -1) {
                if (turn == true) {
                    turn = false;
                    playerHand.add(dominoDeck.get(topDomino));
                    dominoDeck.remove(dominoDeck.get(topDomino));
                    topDomino--;
                } else {
                    turn = true;
                    computerHand.add(dominoDeck.get(topDomino));
                    dominoDeck.remove(dominoDeck.get(topDomino));
                    topDomino--;
                }

            }

        }
        System.out.println("Dominos from 0 to " + playerHand.size()
                + " Player Hand:  " + playerHand.toString());
        System.out.println("Boneyard size:  " + dominoDeck.size());


    }
}
