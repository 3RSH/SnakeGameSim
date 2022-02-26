package ru.derendiaev.model.thread;

import static java.lang.Thread.sleep;

import lombok.Getter;
import lombok.Setter;
import ru.derendiaev.model.CanMoveException;
import ru.derendiaev.model.Coords;
import ru.derendiaev.model.Field;
import ru.derendiaev.model.ModelManager;
import ru.derendiaev.model.object.Direction;
import ru.derendiaev.model.object.MovableFieldObject;

/**
 * Created by DDerendiaev on 03-Feb-22.
 */
public abstract class MovableThread<T extends MovableFieldObject> implements Runnable {

  protected final Field field;
  protected final ModelManager manager;
  protected final T fieldObject;

  @Getter
  @Setter
  protected boolean isLive;

  /**
   * Thread constructor.
   */
  public MovableThread(T fieldObject, Field field, ModelManager manager) {
    this.fieldObject = fieldObject;
    this.field = field;
    this.manager = manager;
    isLive = true;
  }

  @Override
  public void run() {
    while (isLive) {
      try {
        if (canObjectMove()) {
          move();
        }
      } catch (CanMoveException e) {
        manager.stopModel();
      }

      try {
        sleep(1000 / fieldObject.getSpeed());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public abstract void move();

  public abstract boolean canObjectMove() throws CanMoveException;

  protected Coords getNextHeadCoords(Direction direction) {
    Coords headCoords = fieldObject.getHeadCellObject().getCoords();

    int newHeadX = headCoords.getCoordX();
    int newHeadY = headCoords.getCoordY();

    if (direction == Direction.RIGHT) {
      newHeadX++;
    } else if (direction == Direction.LEFT) {
      newHeadX++;
    } else if (direction == Direction.DOWN) {
      newHeadY++;
    } else if (direction == Direction.UP) {
      newHeadY--;
    }

    return new Coords(newHeadX, newHeadY);
  }
}
