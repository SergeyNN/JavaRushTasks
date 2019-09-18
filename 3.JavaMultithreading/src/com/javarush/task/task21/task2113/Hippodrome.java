package com.javarush.task.task21.task2113;


import java.util.ArrayList;
import java.util.List;

public class Hippodrome {
    private List<Horse> horses;
    static Hippodrome game;
    public Hippodrome(List<Horse> horses) {
        this.horses = horses;
    }

    public List<Horse> getHorses() {
        return horses;
    }

    public static void main(String[] args) throws InterruptedException {
        game = new Hippodrome(new ArrayList<Horse>());
        Horse horse1 = new Horse("Sleven",3d,0d);
        Horse horse2 = new Horse("Lucky",3d,0d);
        Horse horse3 = new Horse("Gomer",3d,0d);
        game.getHorses().add(horse1);
        game.getHorses().add(horse2);
        game.getHorses().add(horse3);
        game.run();
        game.printWinner();
    }

    public void move() {
        for (Horse horse: getHorses()) {
            horse.move();
        }
    }

    public void print() {
        for (Horse horse: getHorses()) {
            horse.print();
        }
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }

    public void run() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            move();
            print();
            Thread.sleep(200);
        }
    }

    public Horse getWinner() {
        Horse winner = null;
        for (Horse horse : getHorses()){
            if (winner != null ? horse.getDistance() > winner.getDistance() : true) {
                winner = horse;
            }
        }
        return winner;
    }

    public void printWinner() {
        System.out.println(String.format("Winner is %s!", getWinner().getName()));
    }
}
