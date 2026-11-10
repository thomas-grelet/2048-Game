package com.game2048.controller;

import com.game2048.model.Direction;
import com.game2048.model.Grid;
import com.game2048.view.GameView;

public class GameController {

  private GameView view;
  private Grid grid;
  private boolean isWon = false;

  public GameController(GameView view, int size) {
    this.view = view;
    if ((size < 2) || (size > 10)) {
      size = 4;
    }
    grid = new Grid(size);
  }

  public void startGame() {
    Direction direction;
    view.displayGame(grid.getSize());
    grid.addRandomTile();
    grid.addRandomTile();

    while(!grid.isGameLost()) {
      view.displayScore(grid.getScore());
      view.displayGrid(grid.getCells(), grid.getSize());

      if ((!isWon) && (grid.isGameWon())) {
        view.displayWin();
        isWon = true;
      }

      do {
        direction = view.getDirection();
      } while ((direction == null) || !grid.isMovePossible(direction));
      
      grid.move(direction);
      grid.addRandomTile();
    }

    view.displayScore(grid.getScore());
    view.displayGrid(grid.getCells(), grid.getSize());
    if (grid.isGameLost()) {
      view.displayLose();
    }
  }

}