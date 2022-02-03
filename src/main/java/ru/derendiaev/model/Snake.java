package ru.derendiaev.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by DDerendiaev on 01-Feb-22.
 */
public class Snake {

  private static final String NULL_DIRECTION_ERROR = "Direction is NULL";

  private final int startSize;
  private final int startSpeed;

  @Getter
  private List<Cell> cells;

  @Getter
  private int currentSpeed;

  @Getter
  private int points;

  @Getter
  @Setter
  private boolean isLive;

  @Getter
  private Direction direction;
  private int stepCounter;

  /**
   * Snake constructor.
   */
  public Snake(int startSize, int startSpeed) {
    this.startSize = startSize;
    this.startSpeed = startSpeed;
  }

  /**
   * Snake initialization.
   */
  public void init(List<Cell> fieldCells) {
    currentSpeed = startSpeed;
    direction = Direction.RIGHT;
    points = 0;
    isLive = true;
    cells = new ArrayList<>();

    for (int i = startSize; i > 0; i--) {
      Cell fieldCell = fieldCells.get(i - 1);
      fieldCell.setOccupied(true);
      cells.add(fieldCell);
    }
  }

  /**
   * Move snake.
   */
  public void move(Cell newCell) {
    if (isLive) {
      cells.forEach(cell -> cell.setOccupied(false));

      for (int i = cells.size() - 1; i > 0; i--) {
        cells.set(i, cells.get(i - 1));
      }

      cells.forEach(cell -> cell.setOccupied(true));
      stepCounter++;
    }
  }

  /**
   * Grow snake.
   */
  public void grow() {
    if (isLive) {
      cells.add(cells.get(cells.size() - 1));
      points++;
    }

    if (points % 10 == 0) {
      currentSpeed++;
    }
  }

  /**
   * Snake turn right.
   */
  public void turnRight() {
    if (stepCounter > 0) {
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

      stepCounter = 0;
    }
  }

  /**
   * Snake turn right.
   */
  public void turnLeft() {
    if (stepCounter > 0) {
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

      stepCounter = 0;
    }
  }
}
