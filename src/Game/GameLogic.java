package Game;

import GameStructure.DominoPiece;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GameLogic {

    List<DominoPiece> dominoDeck = new LinkedList<DominoPiece>();
    List<DominoPiece> playerHand = new LinkedList<DominoPiece>();
    List<DominoPiece> computerHand = new LinkedList<DominoPiece>();
    List<DominoPiece> gameBoard = new LinkedList<DominoPiece>();
    int topDomino = 0;
    int gameStartnum = 0;
    boolean turn = true;
    boolean side = false;
    int dominoInHand = -1;
    boolean error = false;
    boolean inputFlip = false;
    boolean start = true;
    int gameEndDraw = 0;



    public List<DominoPiece> getGameBoard() {
        return gameBoard;
    }


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
    }

    public DominoPiece placePiece(DominoPiece piece) {

        if (gameBoard.size() == 0) {
            //sideMatch();
            gameBoard.add(piece);
            gameEndDraw = 0;
            return piece;

        } else if (side == true) {
            //sideMatch();
            gameBoard.add(piece);
            gameEndDraw = 0;
            return piece;
        } else {
            //sideMatch();
            gameBoard.add(0, piece);
            gameEndDraw = 0;
            return piece;
        }


    }


    public int sideMatchHelper(DominoPiece myDomino, int gameDominonum) {
        if (gameDominonum == 0) {
            return -2;
        }
        if (((myDomino.getLeftNum() == gameDominonum) ||
                (myDomino.getLeftNum() == 0))
                && ((myDomino.getRightNum() == gameDominonum) ||
                (myDomino.getRightNum() == 0))) {
            return -2;

        } else if ((myDomino.getLeftNum() == gameDominonum) ||
                (myDomino.getRightNum() == 0)) {
            return myDomino.getRightNum();

        } else if ((myDomino.getRightNum() == gameDominonum) ||
                (myDomino.getRightNum() == 0)) {
            return myDomino.getRightNum();
        }
        gameEndDraw++;
        return -1;

    }

    public void betterSideMatch() {

    }

    public boolean sideMatch() {
        if (gameBoard.size() == 0) {
            if (inputFlip == true) {
                selectPiece().flip(selectPiece());
                return true;
                //placePiece(selectPiece());
            }
        }
        if (side == true) {
            int sideMatchHelperRight = sideMatchHelper(selectPiece(),
                    gameBoard.get(gameBoard.size() - 1).getRightNum());
            int sideMatchHelperLeft = sideMatchHelper(selectPiece(),
                    gameBoard.get(gameBoard.size() - 1).getLeftNum());
            if (sideMatchHelperRight != -1) {
                if (sideMatchHelperRight == -2) {
                    if (inputFlip == true) {
                        selectPiece().flip(selectPiece());
                        return true;
                        //placePiece(selectPiece());
                    } else {
                        return true;
                        //placePiece(selectPiece());
                    }

                }

            }
            if (sideMatchHelperLeft != -1) {
                if (sideMatchHelperLeft == -2) {
                    if (inputFlip == true) {
                        selectPiece().flip(selectPiece());
                        return true;
                        //placePiece(selectPiece());
                    } else {
                        return true;
                        //placePiece(selectPiece());
                    }

                }

            } else error = true;


        }
        return false;
    }

    public DominoPiece selectPiece() {
        int playerHandSize = playerHand.size();
        if (dominoInHand >= 0 && dominoInHand <= playerHandSize - 1) {
            return playerHand.get(dominoInHand);

        }
        error = true;
        return null;

    }

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
            if (sideMatchHelper(piece,
                    gameBoard.get(gameBoard.size() - 1).getRightNum()) != -1) {
                return true;
            }
            if (sideMatchHelper(piece, gameBoard.get(0).getLeftNum()) != -1) {
                return true;
            }


        }
        return false;

    }

    public boolean computerDominoChecker(DominoPiece piece) {

        if (true) {
            int left = gameBoard.get(0).getLeftNum();
            int right = gameBoard.get(gameBoard.size() - 1).getRightNum();
            if (piece.match(piece, gameBoard.get(0)) == true) {
                int matchNumLeft = sideMatchHelper(piece, left);
                if (matchNumLeft != -1) {
                    if (matchNumLeft == piece.getRightNum()) {
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
                int matchNumRight = sideMatchHelper(piece, right);
                if (matchNumRight != -1) {
                    if (matchNumRight == piece.getRightNum()) {
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
        /*System.out.println("COMPUTERR HAND"+computerHand.toString());
        System.out.println("Turn" +turn);*/
        boolean cpuChecker = false;

        if (turn == false) {
            for (int i = 0; i < computerHand.size(); i++) {
                //System.out.println(""+turn);
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

            }

        }

    }

    public boolean gameOver() {


        if (dominoDeck.size() == 0 || gameEndDraw == 2) {
            if (anyValidPiece(computerHand) == false ||
                    anyValidPiece(playerHand) == false) {
                int playerHandNum = 0;
                int computerHandNum = 0;
                for (int i = 0; i < playerHand.size(); i++) {
                    playerHandNum = playerHand.get(i).getRightNum() +
                            playerHand.get(i).getLeftNum();

                }
                for (int i = 0; i < computerHand.size(); i++) {
                    computerHandNum = computerHand.get(i).getRightNum() +
                            computerHand.get(i).getLeftNum();

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


}
