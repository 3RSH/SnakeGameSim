package ru.derendiaev.model.thread;

import java.util.List;
import ru.derendiaev.model.Coords;
import ru.derendiaev.model.Field;
import ru.derendiaev.model.ModelManager;
import ru.derendiaev.model.object.CellObject;
import ru.derendiaev.model.object.MovableCellObject;
import ru.derendiaev.model.object.ObjectType;

/**
 * Created by DDerendiaev on 05-Feb-22.
 */
public class SnakeThread extends MovableThread {

  /**
   * SnakeThread constructor.
   */
  public SnakeThread(List<MovableCellObject> objects, Field field, ModelManager manager) {
    super(objects, field, manager);
  }

  @Override
  void move() {
    Coords nextHeadCoords = getNextHeadCoords(objects.get(objects.size() - 1));

    if (canObjectMove(nextHeadCoords)) {
      if (canObjectGrow(nextHeadCoords)) {
        CellObject nextCoorsdObject = field.getObjectByCoords(nextHeadCoords);
        manager.snakeEatFrog(nextCoorsdObject, this);
        objects.add((MovableCellObject) nextCoorsdObject);
        updateCellObjectsTypes();
      } else {
        changeCoords(nextHeadCoords);
      }
    } else {
      manager.stopModel();
    }
  }

  private boolean canObjectMove(Coords nextHeadCoords) {
    if (field.isCollision(nextHeadCoords)) {
      return false;
    }

    CellObject nextCellObject = field.getObjectByCoords(nextHeadCoords);

    return nextCellObject == null || nextCellObject.getType() == ObjectType.FROG;
  }

  private boolean canObjectGrow(Coords nextHeadCoords) {
    return field.getObjectByCoords(nextHeadCoords).getType() == ObjectType.FROG;
  }

  private void updateCellObjectsTypes() {
    for (int i = 0; i < objects.size(); i++) {
      if (i == 0) {
        objects.get(i).setType(ObjectType.TAIL);
      } else if (i == objects.size() - 1) {
        objects.get(i).setType(ObjectType.HEAD);
      } else {
        objects.get(i).setType(ObjectType.BODY);
      }
    }
  }

  private void changeCoords(Coords nextHeadCoords) {
    for (int i = 0; i < objects.size(); i++) {
      CellObject object = objects.get(i);

      if (i == 0) {
        field.deleteObjectByCoords(object.getCoords());
        object.setCoords(objects.get(i + 1).getCoords());
        field.setObjectByCoords(object, object.getCoords());
      } else if (i == objects.size() - 1) {
        object.setCoords(nextHeadCoords);
        field.setObjectByCoords(object, nextHeadCoords);
      } else {
        object.setCoords(objects.get(i + 1).getCoords());
        field.setObjectByCoords(object, object.getCoords());
      }
    }
  }
}
