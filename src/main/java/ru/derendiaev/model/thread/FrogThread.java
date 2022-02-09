package ru.derendiaev.model.thread;

import static java.lang.Thread.sleep;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;
import ru.derendiaev.model.CellType;
import ru.derendiaev.model.Field;
import ru.derendiaev.model.object.Cell;
import ru.derendiaev.model.object.Direction;
import ru.derendiaev.model.object.MovableObject;

/**
 * Created by DDerendiaev on 05-Feb-22.
 */
public class FrogThread extends MovableThread {

  private final PropertyChangeSupport observer = new PropertyChangeSupport(this);

  /**
   * FrogThread constructor.
   */
  public FrogThread(MovableObject object, Field field, PropertyChangeListener listener) {
    super(object, field);
    observer.addPropertyChangeListener(listener);
  }

  @Override
  public void run() {
    while (isLive) {
      if (isEaten()) {
        observer.firePropertyChange("dieThread", isLive, false);
        break;
      }

      changeObjectDirection();
      nextCell = getNextCell();

      if (canObjectMove()) {
        move();
      }

      try {
        sleep(1000 / object.getSpeed());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  void move() {
    Cell cell = object.getCells().get(0);

    //Cell change.
    object.getCells().set(0, nextCell);

    //Field's types change.
    field.getCoords()[cell.getCellX()][cell.getCellY()] = CellType.FREE;
    field.getCoords()[nextCell.getCellX()][nextCell.getCellY()] = CellType.FROG;

    //Fire cells change.
    observer.firePropertyChange("changeCell", cell, nextCell);
  }

  @Override
  boolean canObjectMove() {
    int nextX = nextCell.getCellX();
    int nextY = nextCell.getCellY();

    //Collision check.
    if (isCollision(nextX, nextY)) {
      return false;
    }

    //CellType check and return.
    return field.getCoords()[nextX][nextY] == CellType.FREE;
  }

  private void changeObjectDirection() {
    Random random = new Random();
    int index = random.nextInt(4);

    if (index == 0) {
      object.setDirection(Direction.RIGHT);
    } else if (index == 1) {
      object.setDirection(Direction.DOWN);
    } else if (index == 3) {
      object.setDirection(Direction.LEFT);
    } else {
      object.setDirection(Direction.UP);
    }
  }

  private boolean isEaten() {
    Cell cell = object.getCells().get(0);
    int cellX = cell.getCellX();
    int cellY = cell.getCellY();

    return field.getCoords()[cellX][cellY] != CellType.FROG;
  }
}
