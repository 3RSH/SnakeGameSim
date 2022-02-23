package ru.derendiaev.model.thread;

import static java.lang.Thread.sleep;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import ru.derendiaev.model.Coords;
import ru.derendiaev.model.Field;
import ru.derendiaev.model.ModelManager;
import ru.derendiaev.model.object.Direction;
import ru.derendiaev.model.object.MovableCellObject;

/**
 * Created by DDerendiaev on 03-Feb-22.
 */
public abstract class MovableThread implements Runnable {

  protected final List<MovableCellObject> objects;
  protected final Field field;
  protected final ModelManager manager;

  @Getter
  @Setter
  protected boolean isLive;

  /**
   * Thread constructor.
   */
  public MovableThread(List<MovableCellObject> objects, Field field, ModelManager manager) {
    this.objects = objects;
    this.field = field;
    this.manager = manager;
    isLive = true;
  }

  @Override
  public void run() {
    while (isLive) {
      move();

      try {
        sleep(1000 / objects.get(0).getSpeed());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  abstract void move();

  protected Coords getNextHeadCoords(MovableCellObject object) {
    Coords headCoords = object.getCoords();

    int newHeadX = headCoords.getCoordX();
    int newHeadY = headCoords.getCoordY();

    Direction direction = object.getDirection();

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
