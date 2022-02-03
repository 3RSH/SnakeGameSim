package ru.derendiaev.model;

import java.util.List;
import java.util.Random;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by DDerendiaev on 01-Feb-22.
 */
public class Frog {

  private static final String NULL_DIRECTION_ERROR = "Direction is NULL";

  @Getter
  private final int speed;

  @Getter
  private Cell cell;

  @Getter
  @Setter
  private boolean isLive;
  private Direction direction;

  /**
   * Frog constructor.
   */
  public Frog(int speed) {
    this.speed = speed;
  }

  /**
   * Frog initialization.
   */
  public void init(List<Cell> fieldCells) {
    respawn(fieldCells);
    isLive = true;
  }

  /**
   * Respawn frog.
   */
  public void respawn(List<Cell> fieldCells) {
    int cellIndex = new Random().nextInt(fieldCells.size());
    cell = fieldCells.get(cellIndex);

    while (cell.isOccupied()) {
      cellIndex = new Random().nextInt(fieldCells.size());
      cell = fieldCells.get(cellIndex);
    }

    cell.setOccupied(true);
  }

  /**
   * Move frog.
   */
  public void move(Cell newCell) {
    if (isLive) {
      cell.setOccupied(false);
      cell = newCell;
      cell.setOccupied(true);
    }
  }

  /**
   * Frog turn right.
   */
  public void turnRight() {
    switch (direction) {
      case RIGHT:
        direction = Direction.DOWN;
        break;
      case DOWN:
        direction = Direction.LEFT;
        break;
      case LEFT:
        direction = Direction.UP;
        break;
      case UP:
        direction = Direction.RIGHT;
        break;
      default:
        throw new NullPointerException(NULL_DIRECTION_ERROR);
    }
  }

  /**
   * Frog turn right.
   */
  public void turnLeft() {
    switch (direction) {
      case RIGHT:
        direction = Direction.UP;
        break;
      case UP:
        direction = Direction.LEFT;
        break;
      case LEFT:
        direction = Direction.DOWN;
        break;
      case DOWN:
        direction = Direction.RIGHT;
        break;
      default:
        throw new NullPointerException(NULL_DIRECTION_ERROR);
    }
  }
}
