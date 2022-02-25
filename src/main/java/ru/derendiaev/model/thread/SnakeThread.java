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

  private final List<MovableCellObject> snakeObjects;

  /**
   * SnakeThread constructor.
   */
  public SnakeThread(
      MovableCellObject snakeHeadObjects,
      Field field, ModelManager manager,
      List<MovableCellObject> snakeObjects) {

    super(snakeHeadObjects, field, manager);
    this.snakeObjects = snakeObjects;
  }

  @Override
  void move() {
    Coords nextHeadCoords = getNextHeadCoords();

    if (canObjectMove(nextHeadCoords)) {
      if (canObjectGrow(nextHeadCoords)) {
        CellObject nextCoordsObject = field.getObjectByCoords(nextHeadCoords);
        manager.snakeEatFrog(nextCoordsObject, this);
        snakeObjects.add((MovableCellObject) nextCoordsObject);
        headObject = (MovableCellObject) nextCoordsObject;
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
    for (int i = 0; i < snakeObjects.size(); i++) {
      if (i == 0) {
        snakeObjects.get(i).setType(ObjectType.TAIL);
      } else if (i == snakeObjects.size() - 1) {
        snakeObjects.get(i).setType(ObjectType.HEAD);
      } else {
        snakeObjects.get(i).setType(ObjectType.BODY);
      }
    }
  }

  private void changeCoords(Coords nextHeadCoords) {
    for (int i = 0; i < snakeObjects.size(); i++) {
      CellObject object = snakeObjects.get(i);

      if (i == 0) {
        field.deleteObjectByCoords(object.getCoords());
        object.setCoords(snakeObjects.get(i + 1).getCoords());
        field.setObjectByCoords(object, object.getCoords());
      } else if (i == snakeObjects.size() - 1) {
        object.setCoords(nextHeadCoords);
        field.setObjectByCoords(object, nextHeadCoords);
      } else {
        object.setCoords(snakeObjects.get(i + 1).getCoords());
        field.setObjectByCoords(object, object.getCoords());
      }
    }
  }
}
