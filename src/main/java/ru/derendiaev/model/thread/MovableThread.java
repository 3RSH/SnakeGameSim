package ru.derendiaev.model.thread;

import static java.lang.Thread.sleep;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import lombok.Getter;
import lombok.Setter;
import ru.derendiaev.model.CollisionExeption;
import ru.derendiaev.model.Field;
import ru.derendiaev.model.object.Coords;
import ru.derendiaev.model.object.Direction;
import ru.derendiaev.model.object.MovableObject;

/**
 * Created by DDerendiaev on 03-Feb-22.
 */
public abstract class MovableThread implements Runnable {

  protected final PropertyChangeSupport observer = new PropertyChangeSupport(this);
  protected final MovableObject fieldObject;
  protected final Field field;

  @Getter
  @Setter
  protected boolean isLive;

  /**
   * Thread constructor.
   */
  public MovableThread(MovableObject fieldObject, Field field, PropertyChangeListener listener) {
    observer.addPropertyChangeListener(listener);
    this.fieldObject = fieldObject;
    this.field = field;
    isLive = true;
  }

  @Override
  public void run() {
    while (isLive) {
      move(getNextHeadCoords());

      try {
        sleep(1000 / fieldObject.getSpeed());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  abstract void move(Coords nextHeadCoords);

  abstract void checkObjectMove(Coords nextHeadCoords) throws CollisionExeption;

  protected Coords getNextHeadCoords() {
    Coords headCoords = fieldObject.getAllCoords().get(0);

    int newHeadX = headCoords.getCoordX();
    int newHeadY = headCoords.getCoordY();

    Direction direction = fieldObject.getDirection();

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
