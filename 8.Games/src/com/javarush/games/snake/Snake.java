package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    public int x,y;
    private static final String HEAD_SIGN="\uD83D\uDC7E";
    private static final String BODY_SIGN="\u26AB";
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;
    private List<GameObject> snakeParts=new ArrayList<GameObject>();

    public Snake(int x, int y) {
        snakeParts.add(new GameObject(x, y));
        snakeParts.add(new GameObject(x+1, y));
        snakeParts.add(new GameObject(x+2, y));
        //super(x, y);
    }

    public void draw(Game game){
        Color colorSnake;
        if (!isAlive) colorSnake=Color.RED;
        else colorSnake = Color.BLACK;
        for (int i=0; i< snakeParts.size(); i++) {
            if (i==0) {
                //game.setCellValue(snakeParts.get(i).x, snakeParts.get(i).y, HEAD_SIGN);
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, colorSnake, HEAD_SIGN, colorSnake, 75);
                //createNewHead(snakeParts.get(i).x, snakeParts.get(i).y);
            }
            else {
               // game.setCellValue(snakeParts.get(i).x, snakeParts.get(i).y, BODY_SIGN);
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, colorSnake, BODY_SIGN, colorSnake, 75);
            }


        }

    }

    public void setDirection(Direction direction) {

        if ((this.direction==Direction.LEFT && direction == Direction.RIGHT) || (this.direction==Direction.RIGHT && direction == Direction.LEFT) || (this.direction==Direction.UP && direction == Direction.DOWN) || (this.direction==Direction.DOWN && direction == Direction.UP)
                || (this.direction==Direction.LEFT && snakeParts.get(0).x==snakeParts.get(1).x)
                || (this.direction==Direction.RIGHT && snakeParts.get(0).x==snakeParts.get(1).x)
                || (this.direction==Direction.UP && snakeParts.get(0).y==snakeParts.get(1).y)
                || (this.direction==Direction.DOWN && snakeParts.get(0).y==snakeParts.get(1).y)) ;
        else this.direction = direction;

    }

    public void move(Apple apple){
        GameObject newHead = createNewHead();
        if (newHead.x < 0 || newHead.x >= SnakeGame.WIDTH
                || newHead.y < 0 || newHead.y >= SnakeGame.HEIGHT) {
            isAlive = false;
            return;
        } else {
            if (checkCollision(newHead)) {
                isAlive=false;
            }
            else {
                snakeParts.add(0, newHead);
                if (newHead.x==apple.x && newHead.y==apple.y) {
                    apple.isAlive=false;
                }
                else removeTail();
            }


        }

        /*GameObject newHead = createNewHead();
        if (newHead.x==0 || newHead.y==0) isAlive=false;
        else {
            snakeParts.add(0,newHead);
            removeTail();
        }*/


    }

    public GameObject createNewHead(){
        int headX,headY;
        headX = snakeParts.get(0).x;
        headY = snakeParts.get(0).y;
        GameObject gameObject = null;
        if (direction==Direction.LEFT) gameObject = new GameObject(headX-1, headY);
        if (direction==Direction.DOWN) gameObject = new GameObject(headX, headY+1);
        if (direction==Direction.RIGHT) gameObject = new GameObject(headX+1, headY);
        if (direction==Direction.UP) gameObject = new GameObject(headX, headY-1);
        //GameObject gameObject = new GameObject(x1,y1);
        return gameObject;
    }

    public void removeTail(){
        if (snakeParts.size()>0) snakeParts.remove(snakeParts.size()-1);
    }

    public boolean checkCollision(GameObject gameObject){
        for (int i=0; i< snakeParts.size(); i++){
            if (gameObject.x==snakeParts.get(i).x && gameObject.y==snakeParts.get(i).y) return true;
            //else return false;
        }
        return false;
    }

    public int getLength(){
        return snakeParts.size();
    }
}
