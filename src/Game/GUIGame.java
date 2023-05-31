/*
DID NOT USE THIS OLD VERSION THAT TRIED TO MANGLE THE TEXT BASED VERSION








package Game;

import GameStructure.DominoGame;
import GameStructure.DominoPiece;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GUIGame extends Application {
    Map<Integer,DominoPiece> gameBoard = new HashMap();
    DominoGame game =  new DominoGame();
    int xOffset= 0;
    int y = 100;
    int x =0;
    boolean test = true;
    boolean flipPress = false;
    boolean flip = false;

    public boolean isFlip() {
        return flip;
    }

    boolean gameStart = true;
    HashMap<Integer,Button> buttonMap = new HashMap<>();
    public boolean flipButtons(Pane root){
        HBox flipBox = new HBox();
        Button flipButton = new Button();
        Button noFlipButton = new Button();
        flipButton.setText("Flip");
        noFlipButton.setText("Dont Flip");
        flipBox.getChildren().addAll(flipButton,noFlipButton);
        flipBox.setLayoutX(10);
        flipBox.setLayoutY(310);
        root.getChildren().addAll(flipBox);
        flipButton.setOnAction(event -> {
            flip = true;
            flipPress = true;

        });
        noFlipButton.setOnAction(event -> {
            flip = false;
            flipPress = true;

        });
        return false;


    }
    public void gameBoardDominos(Pane root){
        HBox gameBox = new HBox();
        Button leftButton = new Button();
        Button rightButton = new Button();
        leftButton.setText("Left");
        rightButton.setText("Right");
        gameBox.getChildren().addAll(leftButton,rightButton);
        gameBox.setLayoutX(10);
        gameBox.setLayoutY(280);
        root.getChildren().addAll(gameBox);
        leftButton.setOnAction(event -> {


        });
        rightButton.setOnAction(event -> {


        });


    }

    public void selectDomino(List<DominoPiece> hand){
        for (int i = 0; i < hand.size(); i++) {
            int test =i;
            buttonMap.get(i).setOnAction(event -> {
                game.setGuiMode(true);
                game.setMyDeleteDomino(test);
                game.pickDomino();




            });

        }



        }


    public void dominoButtons(Pane root,List<DominoPiece> hand){
        HBox buttonBox = new HBox();
        buttonBox.setLayoutX(50);
        buttonBox.setLayoutY(250);
        int xOffset=0;


        for (int i = 0; i < hand.size(); i++) {
            Button dominoButton = new Button();
            buttonMap.put(i,dominoButton);
            dominoButton.setText("Domino: "+i);
            buttonBox.getChildren().addAll(dominoButton);




        }
        root.getChildren().add(buttonBox);



    }
    public void paintDominos(Pane root,List<DominoPiece> hand,int xOffset,int y){
            if (gameStart==true){
                for (int i = 0; i < hand.size(); i++) {
                    int rightNum = hand.get(i).getRightNum();
                    int leftNum = hand.get(i).getLeftNum();



                    Rectangle domino = new Rectangle();
                    Label rightNumLabel = new Label();
                    Label leftNumLabel = new Label();
                    rightNumLabel.setFont(new Font(18));
                    leftNumLabel.setFont(new Font(18));
                    //rightNumLabel.set
                    rightNumLabel.setText(""+rightNum);
                    rightNumLabel.setLayoutX(xOffset+44);
                    rightNumLabel.setLayoutY(y+2);

                    leftNumLabel.setText(""+leftNum);
                    leftNumLabel.setLayoutX(xOffset+4);
                    leftNumLabel.setLayoutY(y+2);

                    domino.setWidth(60);
                    domino.setHeight(30);
                    domino.setStroke(Color.BLACK);

                    domino.setHeight(30);

                    domino.setFill(Color.TRANSPARENT);
                    domino.setX(xOffset);
                    domino.setY(y);


                    root.getChildren().addAll(domino,rightNumLabel,leftNumLabel);
                    xOffset+=65;
                    gameStart = true;

                }



            }







        
    }






    @Override
    public void start(Stage primaryStage) throws Exception {
        game.setGuiMode(true);
        game.createDominos();
        game.draw();
        int height = 1000;
        int width = 1500;
        primaryStage.setTitle("Domino Game");
        Canvas canvas = new Canvas(width, height);
        Label boneYard = new Label();
        VBox vBox = new VBox();
        Pane root =  new Pane(canvas);
        vBox.getChildren().addAll(boneYard);
        root.getChildren().addAll(vBox);
        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();



        paintDominos(root,game.getDominoDeck(),0,100);
        paintDominos(root,game.getPlayerHand(),5,200);
        dominoButtons(root,game.getPlayerHand());
        selectDomino(game.getPlayerHand());
        gameBoardDominos(root);
        flipButtons(root);
        

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                boneYard.setText("Boneyard Size: "+game.getDominoDeck().size());











            }
        };
        animationTimer.start();




    }


    public static void main(String[] args) {
        launch(args);

    }
}
*/
