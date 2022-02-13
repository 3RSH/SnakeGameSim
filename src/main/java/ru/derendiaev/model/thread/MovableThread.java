package ru.derendiaev.model.thread;

import lombok.Getter;
import lombok.Setter;
import ru.derendiaev.model.Field;
import ru.derendiaev.model.object.Cell;
import ru.derendiaev.model.object.Direction;
import ru.derendiaev.model.object.MovableObject;

/**
 * Created by DDerendiaev on 03-Feb-22.
 */
public abstract class MovableThread implements Runnable {

  protected final MovableObject object;
  protected final Field field;

  protected Cell nextCell;

  @Getter
  @Setter
  protected boolean isLive;

  /**
   * Thread constructor.
   */
  public MovableThread(MovableObject object, Field field) {
    this.object = object;
    this.field = field;
    isLive = true;
  }

  @Override
  public abstract void run();

  abstract void move();

  abstract boolean canObjectMove();

  protected Cell getNextCell() {
    Cell cell = object.getCells().get(0);

    //Cell coords get.
    int cellX = cell.getCellX();
    int cellY = cell.getCellY();

    //Coords change.
    Direction direction = object.getDirection();

    if (direction == Direction.RIGHT) {
      cellX++;
    } else if (direction == Direction.LEFT) {
      cellX--;
    } else if (direction == Direction.DOWN) {
      cellY++;
    } else if (direction == Direction.UP) {
      cellY--;
    }

    //New cell create and return.
    return new Cell(cellX, cellY);
  }

  protected boolean isCollision(int cellX, int cellY) {
    return cellX > field.getCoords().length - 1 || cellX < 0
        || cellY > field.getCoords()[cellX].length - 1 || cellY < 0;
  }
}
