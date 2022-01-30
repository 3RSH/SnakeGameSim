package ru.derendiaev.model;

import java.util.Random;

/**
 * Created by DDerendiaev on 21-Jan-22.
 */
public class Frog {

  //Frog params.
  private int frogX;
  private int frogY;
  private boolean isLive;

  //GameField params.
  private final int cellSize;
  private final int fieldCellsX;
  private final int fieldCellsY;

  /**
   * Frog constructor.
   */
  public Frog(int cellSize, int fieldCellsX, int fieldCellsY, Snake snake) {
    this.cellSize = cellSize;
    this.fieldCellsX = fieldCellsX;
    this.fieldCellsY = fieldCellsY;
    init(snake.getSnakeX(), snake.getSnakeY(), snake.getCurrentSize());
  }

  /**
   * Respawn frog.
   */
  public void respawn(int[] snakeX, int[] snakeY, int snakeSize) {
    int planX = new Random().nextInt(fieldCellsX) * cellSize;
    int planY = new Random().nextInt(fieldCellsY) * cellSize;

    while (!isCorrectCoords(planX, planY, snakeX, snakeY, snakeSize)) {
      planX = new Random().nextInt(fieldCellsX) * cellSize;
      planY = new Random().nextInt(fieldCellsY) * cellSize;
    }

    frogX = planX;
    frogY = planY;
  }

  /**
   * Move frog.
   */
  public void move(int[] snakeX, int[] snakeY, int snakeSize) {
    int direction = new Random().nextInt(4);

    if (direction == 0
        && frogX != (fieldCellsX - 1) * cellSize
        && isCorrectCoords(frogX + cellSize, frogY, snakeX, snakeY, snakeSize)) {

      frogX += cellSize;
    } else if (direction == 1
        && frogY != (fieldCellsY - 1) * cellSize
        && isCorrectCoords(frogX, frogY + cellSize, snakeX, snakeY, snakeSize)) {

      frogY += cellSize;
    } else if (direction == 2
        && frogX != 0
        && isCorrectCoords(frogX - cellSize, frogY, snakeX, snakeY, snakeSize)) {

      frogX -= cellSize;
    } else if (direction == 3
        && frogY != 0
        && isCorrectCoords(frogX, frogY - cellSize, snakeX, snakeY, snakeSize)) {
      frogY -= cellSize;
    }
  }

  public void init(int[] snakeX, int[] snakeY, int snakeSize) {
    isLive = true;
    respawn(snakeX, snakeY, snakeSize);
  }

  public boolean isLive() {
    return isLive;
  }

  public void setLive(boolean live) {
    isLive = live;
  }

  public int getFrogX() {
    return frogX;
  }

  public int getFrogY() {
    return frogY;
  }

  private boolean isCorrectCoords(
      int planX, int planY, int[] snakeX, int[] snakeY, int snakeSize) {

    for (int i = 0; i < snakeSize; i++) {
      if (planX == snakeX[i] && planY == snakeY[i]) {
        return false;
      }
    }
    return true;
  }
}
