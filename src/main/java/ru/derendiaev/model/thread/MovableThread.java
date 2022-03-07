package ru.derendiaev.model.thread;

import static java.lang.Thread.sleep;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import lombok.Getter;
import lombok.Setter;
import ru.derendiaev.model.Coords;
import ru.derendiaev.model.Field;
import ru.derendiaev.model.ModelManager;
import ru.derendiaev.model.StopModelException;
import ru.derendiaev.model.object.Direction;
import ru.derendiaev.model.object.MovableObject;

/**
 * Created by DDerendiaev on 03-Feb-22.
 */
public abstract class MovableThread<T extends MovableObject> implements Runnable {

  protected final Field field;
  protected final ModelManager manager;
  protected final T object;
  protected final PropertyChangeSupport observer;

  @Getter
  @Setter
  protected boolean isLive;

  /**
   * Thread constructor.
   */
  public MovableThread(
      T object, Field field, ModelManager manager, PropertyChangeListener listener) {

    this.object = object;
    this.field = field;
    this.manager = manager;
    this.observer = new PropertyChangeSupport(this);
    observer.addPropertyChangeListener(listener);
    isLive = true;
  }

  @Override
  public void run() {
    while (isLive) {
      synchronized (field) {
        try {
          if (canMove()) {
            move();
          }
        } catch (StopModelException e) {
          manager.stopModel();
        }
      }

      try {
        sleep(1000 / object.getSpeed());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    observer.firePropertyChange("dieThread", true, false);
  }

  public abstract void move();

  public abstract boolean canMove() throws StopModelException;

  protected Coords getNextCoords() {
    Coords headCoords = object.getCoords();

    int newHeadX = headCoords.getCoordX();
    int newHeadY = headCoords.getCoordY();
    Direction direction = object.getDirection();

    if (direction == Direction.RIGHT) {
      newHeadX++;
    } else if (direction == Direction.LEFT) {
      newHeadX--;
    } else if (direction == Direction.DOWN) {
      newHeadY++;
    } else if (direction == Direction.UP) {
      newHeadY--;
    }

    return new Coords(newHeadX, newHeadY);
  }
}
