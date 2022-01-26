package ru.derendiaev.model;

import java.util.Random;

/**
 * Created by DDerendiaev on 21-Jan-22.
 */
public class Frog {

  //Global param.
  private static final int CELL_SIZE = 16;

  //Frog params.
  private int frogX;
  private int frogY;
  private boolean isLive;

  //GameField params.
  private final int fieldCellsX;
  private final int fieldCellsY;

  /**
   * Frog constructor.
   */
  public Frog(int fieldCellsX, int fieldCellsY) {
    this.fieldCellsX = fieldCellsX;
    this.fieldCellsY = fieldCellsY;
    init();
  }

  public void respawn() {
    frogX = (new Random().nextInt(fieldCellsX) * CELL_SIZE);
    frogY = (new Random().nextInt(fieldCellsY) * CELL_SIZE);
  }

  /**
   * Move frog.
   */
  public void move() {
    int direction = new Random().nextInt(4);

    if (direction == 0 && frogX != (fieldCellsX - 1) * CELL_SIZE) {
      frogX += CELL_SIZE;
    } else if (direction == 1 && frogY != (fieldCellsY - 1) * CELL_SIZE) {
      frogY += CELL_SIZE;
    } else if (direction == 2 && frogX != 0) {
      frogX -= CELL_SIZE;
    } else if (direction == 3 && frogY != 0) {
      frogY -= CELL_SIZE;
    }
  }

  public void init() {
    isLive = true;
    respawn();
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
}
