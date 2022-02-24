package ru.derendiaev.model.thread;

import java.util.List;
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
  public FrogThread(List<MovableCellObject> objects, Field field, ModelManager manager) {
    super(objects, field, manager);
  }

  @Override
  void move() {
    if (objects.size() > 0) {
      MovableCellObject frogObject = objects.get(0);
      Coords currentHeadCoords = frogObject.getCoords();
      Coords nextHeadCoords = getNextHeadCoords();

      if (canObjectMove(nextHeadCoords)) {
        frogObject.setCoords(nextHeadCoords);
        field.setObjectByCoords(frogObject, nextHeadCoords);
        field.deleteObjectByCoords(currentHeadCoords);
      }

      changeDirection();
    }
  }

  private boolean canObjectMove(Coords nextHeadCoords) {
    if (field.isCollision(nextHeadCoords)) {
      return false;
    }

    return field.getObjectByCoords(nextHeadCoords) == null;
  }

  private void changeDirection() {
    objects.get(0).setDirection(RandomDirectionGenerator.getRandomObjectDirection());
  }
}
