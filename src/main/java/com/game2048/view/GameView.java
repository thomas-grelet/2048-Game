package com.game2048.view;

import com.game2048.model.Direction;

public interface GameView {

  void displayGame(int size);
  void displayGrid(int[][] cells, int size);
  void displayScore(int score);
  void displayWin();
  void displayLose();
  Direction getDirection();

}