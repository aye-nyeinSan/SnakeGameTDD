package com.chapter5.snakegametdd.model;

import com.chapter5.snakegametdd.Launcher;
import com.chapter5.snakegametdd.view.Platform;
import javafx.geometry.Point2D;
import javafx.scene.control.DialogPane;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private Direction direction;
    private Point2D head;
    private Point2D prev_tail;
    private int score=0;

    private ArrayList<Point2D> body;
    public Snake(Point2D position) {
        direction = Direction.DOWN;
        body = new ArrayList<>();
        this.head = position;
        this.body.add(this.head);


    }
    public void update(){
        head = head.add(direction.current);
        prev_tail = body.remove(body.size()-1);
        body.add(0,head);
    }
    public boolean isDead() {
        boolean isOutOfBound= head.getX()<0 || head.getY() <0
                || head.getX()> Platform.WIDTH||head.getY()>Platform.HEIGHT;
        boolean isHitBody = body.lastIndexOf(head)> 0;


        return isOutOfBound|| isHitBody;
    }
    public boolean isCollidingWith(Food food)
    {

        return head.equals(food.getPosition());
    }
    public void grow() {body.add(prev_tail);}
    public int getLength(){ return body.size();}
    public List<Point2D> getBody() { return body;}
public void setCurrentDirection(Direction direction){
        this.direction = direction;
}
 public Direction getCurrentDirection(){ return this.direction;}
    public Point2D getHead() {
        return head;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setHead(Point2D head) {
        this.head = head;
    }

    public Point2D getPrev_tail() {
        return prev_tail;
    }

    public void setPrev_tail(Point2D prev_tail) {
        this.prev_tail = prev_tail;
    }

    public void setBody(ArrayList<Point2D> body) {
        this.body = body;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
