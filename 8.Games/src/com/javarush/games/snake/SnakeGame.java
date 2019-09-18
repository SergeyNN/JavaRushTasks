package com.javarush.games.snake;
import com.javarush.engine.cell.*;

public class SnakeGame extends Game{

    public static final int WIDTH=15;
    public static final int HEIGHT=15;
    private static final int GOAL = 28;
    private Snake snake;
    private int turnDelay;
    private Apple apple;
    private boolean isGameStopped;
    private int score;
    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
        //super.initialize();
    }

    private void createGame(){
        score=0;
        setScore(score);
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        //apple = new Apple(5,5);
        //apple.draw(this);
        createNewApple();
        //apple.draw(this);
        isGameStopped = false;
        drawScene();
        turnDelay = 300;
        setTurnTimer(turnDelay);
       // Apple apple = new Apple(7,7);
       // apple.draw(this);
    }

    private void drawScene(){
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                //setCellColor(x, y, Color.DARKSEAGREEN);
                setCellValueEx(x, y, Color.DARKSEAGREEN,"");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    @Override
    public void onTurn(int step) {
        snake.move(apple);
        if (!apple.isAlive) {
            createNewApple();
            score=score+5;
            setScore(score);
            turnDelay=turnDelay-10;
            setTurnTimer(turnDelay);
        }
        if (snake.isAlive==false) gameOver();
        if (snake.getLength()>GOAL) win();
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        switch (key){
            case UP:
                snake.setDirection(Direction.UP);
                break;
            case DOWN:
                snake.setDirection(Direction.DOWN);
                break;
            case LEFT:
                snake.setDirection(Direction.LEFT);
                break;
            case RIGHT:
                snake.setDirection(Direction.RIGHT);
                break;
            case SPACE:
                if (isGameStopped == true)
                createGame();
                break;
        }
    }

    private void createNewApple(){
        //getRandomNumber(WIDTH);
        //getRandomNumber(HEIGHT);
        //apple = new Apple(getRandomNumber(WIDTH),getRandomNumber(HEIGHT));
        //snake.checkCollision(apple);

        do {
            apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        }while(snake.checkCollision(apple));
    }

    private void gameOver(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.AQUA, "game over", Color.BISQUE, 20);
    }

    private void win(){
        stopTurnTimer();
        isGameStopped=true;
        showMessageDialog(Color.AQUA, "you win", Color.BISQUE, 20);
    }
}


