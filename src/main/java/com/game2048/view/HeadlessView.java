package com.game2048.view;

import java.util.Scanner;

import com.game2048.model.Direction;

public class HeadlessView implements GameView {

  private Scanner clavier = new Scanner(System.in);

  @Override
  public void displayGame(int size) {
    String separator = "=".repeat(2*size-1);
    System.out.println("\n" + separator + " 2048-Game " + separator);
    System.out.println("-> Size: " + size + "x" + size);
}

  @Override
  public void displayGrid(int[][] cells, int size) {
    for (int i=0 ; i<size ; i++) {
      for (int j=0 ; j<size ; j++) {
        System.out.print("+-----");
      }
      System.out.println("+");
      for (int j=0 ; j<size ; j++) {
        if (cells[i][j] == 0) {       
          System.out.printf("|%5s", "");
        } else {
          System.out.printf("|%5s", String.valueOf(cells[i][j]));
        }
      }
      System.out.println("|");
    }
    for (int j=0 ; j<size ; j++) {
        System.out.print("+-----");
    }
    System.out.println("+\n");
  }

  @Override
  public void displayScore(int score) {
    System.out.println("\n-> Score : " + score);
  }

  @Override
  public void displayWin() {
    System.out.println("GG! The game is won!\n");
  }

  @Override
  public void displayLose() {
    System.out.println("OH no... The game is lost.\n");
  }

  @Override
  public Direction getDirection() {
    String choice = getChoice();
    if (("z".equals(choice)) || ("Z".equals(choice))) {
      return Direction.UP;
    } else if (("s".equals(choice)) || ("S".equals(choice))) {
      return Direction.DOWN;
    } else if (("q".equals(choice)) || ("Q".equals(choice))) {
      return Direction.LEFT;
    } else if (("d".equals(choice)) || ("D".equals(choice))) {
      return Direction.RIGHT;
    }
    return null;
  }

  private String getChoice() {
    System.out.print("[Action] Move (ZQSD) : ");
    return clavier.next();
  }
    
}