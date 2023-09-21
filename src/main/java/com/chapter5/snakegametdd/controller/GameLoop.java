package com.chapter5.snakegametdd.controller;

import com.chapter5.snakegametdd.Launcher;
import com.chapter5.snakegametdd.model.Direction;
import com.chapter5.snakegametdd.model.Food;
import com.chapter5.snakegametdd.model.Snake;
import com.chapter5.snakegametdd.view.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;

public class GameLoop  implements Runnable{
    private Platform platform;
    private Snake snake;
    private Food food1;
    private Food food2;
    private int score;
    private float interval = 1000.0f/ 10;
    private boolean running;

    public GameLoop(Platform platform, Snake snake, Food food1,Food food2) {
        this.snake = snake;
        this.platform = platform;
        this.food1 = food1;
        this.food2 = food2;
        running =true;
        this.score = 0;


    }



    @Override
    public void run() {
        while (running){
            update();
            checkCollision();
            redraw();
            try{
                Thread.sleep((long)interval);

            } catch (InterruptedException e) {
               e. printStackTrace();
            }
        }


    }
    private void update(){
        KeyCode cur_key = platform.getKey();
        Direction cur_direction = snake.getCurrentDirection();
        if (cur_key == KeyCode.UP && cur_direction != Direction.DOWN){
            snake.setCurrentDirection(Direction.UP);
        } else if (cur_key == KeyCode.DOWN && cur_direction != Direction.UP) {
            snake.setCurrentDirection(Direction.DOWN);
        }else if (cur_key == KeyCode.LEFT && cur_direction != Direction.RIGHT) {
            snake.setCurrentDirection(Direction.LEFT);
        }else if (cur_key == KeyCode.RIGHT && cur_direction != Direction.LEFT) {
            snake.setCurrentDirection(Direction.RIGHT);
        }
        snake.update();
    }
    private void checkCollision(){
        if (snake.isCollidingWith(food1)){
            snake.grow();
            food1.respawn();
            if(food1.isGreen()){
                score+=5;
            }
           else score++;
        }
        if (snake.isCollidingWith(food2)){
            snake.grow();
            food2.respawn();
            if(food2.isGreen()){
                score+=5;
            }
            else score++;
        }
        if (snake.isDead()){
            running = false;

                javafx.application.Platform.runLater(() -> {
                    AnchorPane pane = new AnchorPane();
                    VBox vbox = new VBox();

                    Text text = new Text("Game Over!!\n You are Dead!");
                    text.setTextAlignment(TextAlignment.CENTER);
                    text.setLineSpacing(10);
                    text.setFont(Font.font("Arial"));

                    Text scoreText = new Text(String.valueOf(score));
                    scoreText.setTextAlignment(TextAlignment.CENTER);
                    scoreText.setLineSpacing(35);
                    scoreText.setFont(Font.font("Arial",FontWeight.BOLD, 50));


                    vbox.getChildren().addAll(text, scoreText);
                    vbox.setAlignment(Pos.CENTER);

                    vbox.setSpacing(10);// Center-align the VBox content

                    BorderStroke dashedBorderStroke = new BorderStroke(
                            Color.BLACK,                       // Border color
                            BorderStrokeStyle.DASHED,          // Border style (DASHED)
                            CornerRadii.EMPTY,                // Corner radii (none)
                            new BorderWidths(1),              // Border widths
                            null                              // Border insets (null for default)
                    );

                    Border dashedBorder = new Border(dashedBorderStroke);
                    pane.setBorder(dashedBorder);
                    pane.setPadding(new Insets(20));
                    pane.setBackground(new Background(new BackgroundFill(Color.YELLOWGREEN, null, null)));
                    pane.setCenterShape(true);
                    pane.getChildren().addAll(vbox);
                    vbox.setLayoutX(40);
                    vbox.setLayoutY(20);



                    Popup popup = new Popup();
                    popup.getContent().addAll(pane);
                    popup.setHeight(500);
                    popup.setWidth(500);
                    popup.sizeToScene();


                    popup.show(Launcher.stage);
                    pane.setOnKeyPressed((e -> {
                        KeyCode key = e.getCode();
                        if (key == KeyCode.ESCAPE) {
                            popup.getScene().getWindow().hide();
                        }
                    }));
                });




        }

        }

    private void redraw() {
        platform.render(snake, food1,food2);
    }
    public Snake getSnake() {
        return snake;
    }

    public Platform getPlatform() {
        return platform;
    }
}
