package Game;

import GameStructure.DominoPiece;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class DominoGUI extends Application {
    GameLogic game = new GameLogic();
    List<Button> buttonList = new LinkedList<>();
    VBox globalFlipBox = new VBox();
    CheckBox flipbox = new CheckBox();
    CheckBox leftRight = new CheckBox();
    Label leftRightSelector = new Label();
    int height = 500;
    int width = 1500;
    Canvas canvas = new Canvas(width, height);
    Pane root = new Pane(canvas);
    int skipTurnNum = 0;
    boolean gameoverBool = false;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * paints the players hand on the GUI
     * @param root Pane that holds everything
     * @return an Hbox with each domino
     */
    public HBox paintHand(Pane root) {
        HBox playerHandBox = new HBox();
        playerHandBox.setLayoutX(50);
        playerHandBox.setLayoutY(150);
        int rightNum = 0;
        int leftNum = 0;
        int leftNumOffset = 0;
        int rightNumOffset = 40;

        for (int i = 0; i < game.playerHand.size(); i++) {
            HBox leftNumBox = new HBox();
            HBox rightNumBox = new HBox();

            Label rightNumLabel = new Label();
            Label leftNumLabel = new Label();
            rightNumLabel.setFont(new Font(18));
            leftNumLabel.setFont(new Font(18));
            HBox domino = new HBox();


            domino.setSpacing(35);
            domino.setStyle(("-fx-padding: 5;" +
                    "-fx-border-style: solid inside;"
                    + "-fx-border-width: 3;" + "-fx-border-insets: 2;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: black;"));
            rightNum = game.playerHand.get(i).getRightNum();
            leftNum = game.playerHand.get(i).getLeftNum();
            rightNumLabel.setText("" + rightNum);
            leftNumLabel.setText("" + leftNum);
            leftNumBox.getChildren().add(leftNumLabel);
            rightNumBox.getChildren().add(rightNumLabel);
            domino.getChildren().addAll(leftNumLabel, rightNumLabel);

            int finalLeftNum = leftNum;
            int finalRightNum = rightNum;
            domino.setOnMouseClicked(event -> {
                if (skipTurnNum == 3 || game.dominoDeck.size() == 0 &&
                        !aicanPlay() && !playerCanPlay()) {
                    gameoverBool = true;
                    if (gameoverBool=true){
                        Label gameoverLabel = new Label();
                        gameoverLabel.setFont(new Font(60));
                        gameoverLabel.setTextFill(Color.DARKOLIVEGREEN);
                        if (gameOverPoints(game.playerHand)<gameOverPoints
                                (game.computerHand))

                            gameoverLabel.setText("YOU WIN!   ");
                        else{
                            gameoverLabel.setText("YOU LOSE :)   ");
                        }
                        playerHandBox.getChildren().add(gameoverLabel);
                        root.getChildren().addAll(playerHandBox);

                    }


                }
                DominoPiece myPiece = new DominoPiece(finalLeftNum,
                        finalRightNum);
                if (leftRight.isSelected()) {


                    if (addRight(myPiece) == true) {


                        removePiece(myPiece, true);
                        computerPlayerAi();

                    }
                } else {
                    if (addLeft(myPiece) == true) {
                        removePiece(myPiece, true);
                        computerPlayerAi();


                    }
                }


                if (playerCanPlay() == false) {

                    draw(game.playerHand);


                }


                clearAndSet(root);
            });




            playerHandBox.setLayoutX(50);
            playerHandBox.setLayoutX(60);
            playerHandBox.getChildren().addAll(domino);


        }
        return playerHandBox;
    }

    /**
     * checks if the ai has a legal move to play
     * @return true if the ai can play
     */
    public boolean aicanPlay() {
        return 0 != game.computerHand.stream().filter(s -> legal(s,
                true) || legal(s, false)).count();
    }
    /**
     * checks if the player has a legal move to play
     * @return true if the player can play
     */
    public boolean playerCanPlay() {
        return 0 != game.playerHand.stream().filter(s -> legal(s,
                true) || legal(s, false)).count();
    }

    /**
     * draws a domino from the boneyard if the computer or player has to
     * @param hand the hand of the caller
     */
    public void draw(List<DominoPiece> hand) {
        if (game.dominoDeck.size() > 0) {

            hand.add(game.dominoDeck.get(game.dominoDeck.size() - 1));
            game.dominoDeck.remove(game.dominoDeck.size() - 1);
        } else {
            skipTurnNum++;
        }
    }

    /**
     * checks the amount of points on the dominos and adds them up
     * @param hand hand of the caller
     * @return the number of points on the dominos left in hand after gameover
     */
    public int gameOverPoints(List<DominoPiece> hand) {
        return hand.stream().mapToInt(s -> s.getLeftNum() +
                s.getRightNum()).sum();


    }

    /**
     * AI checks every piece in its hand and tries to place them
     */
    public void computerPlayerAi() {

        List<DominoPiece> pieces = game.playerHand.stream()
                .filter(s -> legal(s, true) || legal(s,
                        false)).collect(Collectors.toList());
        if (pieces.size() == 0) {
            draw(game.computerHand);

        } else {
            Collections.shuffle(pieces);
            DominoPiece computerPiece = pieces.get(0);


            if (addRight(computerPiece) == true) {
                removePiece(computerPiece, false);
                return;


            }

            if (addLeft(computerPiece) == true) {
                removePiece(computerPiece, false);
                return;


            }
        }
    }

    /**
     * Paints the gameboard on the GUI
     * @param root Pane that holds everything
     * @return vbox of the games two rows offset accordingly
     */
    public VBox paintGameBoard(Pane root) {
        VBox vBox = new VBox();
        vBox.setLayoutX(100);
        vBox.setLayoutY(50);
        HBox rowOneGame = new HBox();
        HBox rowTwoGame = new HBox();
        boolean xOffset = false;


        int rightNum = 0;
        int leftNum = 0;
        for (int i = 0; i < game.gameBoard.size(); i++) {
            if (i % 2 == 0) {
                Label rightNumLabel = new Label();
                Label leftNumLabel = new Label();
                rightNumLabel.setFont(new Font(18));
                leftNumLabel.setFont(new Font(18));
                HBox domino = new HBox();
                domino.setSpacing(35);
                domino.setStyle(("-fx-padding: 5;" +
                        "-fx-border-style: solid inside;"
                        + "-fx-border-width: 3;" + "-fx-border-insets: 2;"
                        + "-fx-border-radius: 5;" +
                        "-fx-border-color: black;"));
                rightNum = game.gameBoard.get(i).getRightNum();
                leftNum = game.gameBoard.get(i).getLeftNum();
                rightNumLabel.setText("" + rightNum);
                leftNumLabel.setText("" + leftNum);
                domino.getChildren().addAll(leftNumLabel, rightNumLabel);
                rowOneGame.getChildren().addAll(domino);


            } else {
                HBox hBoxOffset = new HBox();
                hBoxOffset.setLayoutX(25);

                Label rightNumLabel = new Label();
                Label leftNumLabel = new Label();
                rightNumLabel.setFont(new Font(18));
                leftNumLabel.setFont(new Font(18));
                HBox domino = new HBox();
                HBox offset = new HBox();
                if (xOffset == false) {
                    offset.setStyle(("-fx-padding: 14;" +
                            "-fx-border-style: solid inside;"
                            + "-fx-border-width: 3;" + "-fx-border-insets: 2;"
                            + "-fx-border-radius: 5;" +
                            "-fx-border-color: Transparent;"));

                    offset.getChildren().addAll(leftNumLabel, rightNumLabel);
                    xOffset = true;

                }
                if (xOffset = true) {

                    domino.setSpacing(35);
                    domino.setStyle(("-fx-padding: 5;" +
                            "-fx-border-style: solid inside;"
                            + "-fx-border-width: 3;" + "-fx-border-insets: 2;"
                            + "-fx-border-radius: 5;" +
                            "-fx-border-color: black;"));
                    rightNum = game.gameBoard.get(i).getRightNum();
                    leftNum = game.gameBoard.get(i).getLeftNum();
                    rightNumLabel.setText("" + rightNum);
                    leftNumLabel.setText("" + leftNum);
                    domino.getChildren().addAll(leftNumLabel, rightNumLabel);

                    rowTwoGame.getChildren().addAll(offset, domino);

                }
            }


        }
        vBox.getChildren().addAll(rowOneGame, rowTwoGame);
        return vBox;
    }


    /**
     * checks if a valid piece is legal to play
     * @param myPiece the callers piece
     * @param dominoSide the side open on the board
     * @return
     */
    public boolean legal(DominoPiece myPiece, boolean dominoSide) {
        if (game.getGameBoard().size() == 0) {
            return true;
        }
        int outPiece = -1;
        int mySide = -1;

        if (dominoSide) {
            outPiece = game.gameBoard.get(0).getLeftNum();
            mySide = myPiece.getRightNum();
        } else {
            outPiece = game.gameBoard.get(game.gameBoard.size() - 1)
                    .getRightNum();
            mySide = myPiece.getLeftNum();
        }

        return mySide == 0 || outPiece == 0 || mySide == outPiece;
    }

    public DominoPiece flipPiece(DominoPiece myPiece) {

        return new DominoPiece(myPiece.getRightNum(), myPiece.getLeftNum());

    }

    /**
     * removes a domino piece from the player or computers hand
     * @param myPiece the piece to remove
     * @param AIorPlayer to determine whos hand we are removing the piece from
     */
    public void removePiece(DominoPiece myPiece, boolean AIorPlayer) {
        if (AIorPlayer == true) {
            game.playerHand = game.playerHand.stream().filter(s
                    -> !(s.getRightNum() ==
                    myPiece.getRightNum() && s.getLeftNum()
                    == myPiece.getLeftNum() ||

                    s.getLeftNum() == myPiece.getRightNum() && s.getRightNum()
                            == myPiece.getLeftNum()))
                    .collect(Collectors.toList());
        } else {

            game.computerHand = game.computerHand.stream().filter(s ->
                    !(s.getRightNum() ==
                    myPiece.getRightNum() && s.getLeftNum() ==
                            myPiece.getLeftNum() ||

                    s.getLeftNum() == myPiece.getRightNum() && s.getRightNum()
                            == myPiece.getLeftNum()))
                    .collect(Collectors.toList());
        }
    }

    /**
     * adding to the left side
     * @param piece the piece to be added to the left side
     * @return true if we placed
     */
    public boolean addLeft(DominoPiece piece) {
        if (legal(piece, true)) {
            skipTurnNum = 0;
            game.gameBoard.add(0, piece);
            return true;
        }
        if (legal(flipPiece(piece), true)) {
            skipTurnNum = 0;
            game.gameBoard.add(0, flipPiece(piece));
            return true;
        }
        return false;
    }
    /**
     * adding to the right side
     * @param piece the piece to be added to the right side
     * @return true if we placed
     */
    public boolean addRight(DominoPiece piece) {
        if (legal(piece, false)) {
            game.gameBoard.add(piece);
            skipTurnNum = 0;
            return true;
        }
        if (legal(flipPiece(piece), false)) {
            game.gameBoard.add(flipPiece(piece));
            skipTurnNum = 0;
            return true;
        }
        return false;
    }

    /**
     * clears the root of everything and re adds everything back
     * @param root
     */
    public void clearAndSet(Pane root) {
        Label boneYard = new Label();
        root.getChildren().clear();
        boneYard.setFont(new Font(20));
        boneYard.setText("BONEYARD SIZE: " + game.dominoDeck.size());
        root.getChildren().addAll(globalFlipBox,
                paintGameBoard(root), paintHand(root), paintGameBoard(root),
                leftRight, boneYard,leftRightSelector);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        game.createDominos();
        game.draw();
        primaryStage.setTitle("Domino Game");

        leftRight.setLayoutX(50);
        leftRight.setLayoutY(300);
        leftRightSelector.setText("Selected = playing at right side");
        leftRightSelector.setLayoutX(50);
        leftRightSelector.setLayoutY(330);

        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();

        root.getChildren().addAll(globalFlipBox,
                paintGameBoard(root), paintHand(root), paintGameBoard(root),
                leftRight,leftRightSelector);

    }
}


