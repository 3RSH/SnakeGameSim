package ru.derendiaev.model.thread;

import ru.derendiaev.model.Coords;
import ru.derendiaev.model.Field;
import ru.derendiaev.model.ModelManager;
import ru.derendiaev.model.RandomDirectionGenerator;
import ru.derendiaev.model.object.MovableCellObject;

/**
 * Created by DDerendiaev on 05-Feb-22.
 */
public class FrogThread extends MovableThread {

  /**
   * FrogThread constructor.
   */
  public FrogThread(MovableCellObject frogObject, Field field, ModelManager manager) {
    super(frogObject, field, manager);
  }

  @Override
  void move() {
    Coords currentHeadCoords = headObject.getCoords();
    Coords nextHeadCoords = getNextHeadCoords();

    if (canObjectMove(nextHeadCoords)) {
      headObject.setCoords(nextHeadCoords);
      field.setObjectByCoords(headObject, nextHeadCoords);
      field.deleteObjectByCoords(currentHeadCoords);
    }

    changeDirection();
  }

  private boolean canObjectMove(Coords nextHeadCoords) {
    if (field.isCollision(nextHeadCoords)) {
      return false;
    }

    return field.getObjectByCoords(nextHeadCoords) == null;
  }

  private void changeDirection() {
    headObject.setDirection(RandomDirectionGenerator.getRandomObjectDirection());
  }
}
