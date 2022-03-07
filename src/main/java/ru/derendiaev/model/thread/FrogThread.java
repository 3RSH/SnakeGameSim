package ru.derendiaev.model.thread;

import java.beans.PropertyChangeListener;
import ru.derendiaev.model.Coords;
import ru.derendiaev.model.Field;
import ru.derendiaev.model.ModelManager;
import ru.derendiaev.model.RandomDirectionGenerator;
import ru.derendiaev.model.object.Cell;
import ru.derendiaev.model.object.FrogObject;

/**
 * Created by DDerendiaev on 05-Feb-22.
 */
public class FrogThread extends MovableThread<FrogObject> {

  /**
   * FrogThread constructor.
   */
  public FrogThread(
      FrogObject fieldObject, Field field, ModelManager manager, PropertyChangeListener listener) {

    super(fieldObject, field, manager, listener);
  }

  @Override
  public void move() {
    synchronized (field) {
      Coords coords = object.getCoords();
      Coords nextCoords = getNextCoords();
      Cell frogCell = field.getCellObjectByCoords(coords);
      field.deleteCellObjectByCoords(coords);

      object.setCoords(nextCoords);
      field.setCellObjectByCoords(frogCell, nextCoords);

      observer.firePropertyChange("move", coords, nextCoords);
    }
  }

  @Override
  public boolean canMove() {
    changeDirection();
    Coords nextHeadCoords = getNextCoords();

    if (field.isCollision(nextHeadCoords)) {
      return false;
    }

    return field.getCellObjectByCoords(nextHeadCoords) == null;
  }

  private void changeDirection() {
    object.setDirection(RandomDirectionGenerator.getRandomObjectDirection());
  }
}
