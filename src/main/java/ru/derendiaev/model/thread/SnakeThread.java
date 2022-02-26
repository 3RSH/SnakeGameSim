package ru.derendiaev.model.thread;

import java.util.List;
import ru.derendiaev.model.CanMoveException;
import ru.derendiaev.model.Coords;
import ru.derendiaev.model.Field;
import ru.derendiaev.model.ModelManager;
import ru.derendiaev.model.object.CellObject;
import ru.derendiaev.model.object.CellObjectType;
import ru.derendiaev.model.object.SnakeObject;

/**
 * Created by DDerendiaev on 05-Feb-22.
 */
public class SnakeThread extends MovableThread<SnakeObject> {

  /**
   * SnakeThread constructor.
   */
  public SnakeThread(SnakeObject fieldObject, Field field, ModelManager manager) {
    super(fieldObject, field, manager);
  }

  @Override
  public void move() {
    Coords nextHeadCoords = getNextHeadCoords();

    if (canObjectGrow(nextHeadCoords)) {
      CellObject frogCellObject = field.getCellObjectByCoords(nextHeadCoords);
      manager.snakeEatFrog(frogCellObject, this);
      fieldObject.growSnake(field.getCellObjectByCoords(nextHeadCoords));

    } else {
      List<CellObject> snakeCellObjects = fieldObject.getCellObjects();
      Coords currentTailCoords = snakeCellObjects.get(0).getCoords();

      fieldObject.setNewCoords(nextHeadCoords);
      snakeCellObjects
          .forEach(cellObject -> field.setCellObjectByCoords(cellObject, cellObject.getCoords()));
      field.deleteCellObjectByCoords(currentTailCoords);
    }
  }

  @Override
  public boolean canObjectMove() throws CanMoveException {
    Coords nextHeadCoords = getNextHeadCoords();
    CellObject nextCellObject = field.getCellObjectByCoords(nextHeadCoords);

    if (field.isCollision(nextHeadCoords)
        || nextCellObject == null
        || nextCellObject.getType() == CellObjectType.FROG) {

      throw new CanMoveException();
    }

    return true;
  }

  private boolean canObjectGrow(Coords nextHeadCoords) {
    return field.getCellObjectByCoords(nextHeadCoords).getType() == CellObjectType.FROG;
  }
}
