package ru.derendiaev.model.thread;

import ru.derendiaev.model.Coords;
import ru.derendiaev.model.Field;
import ru.derendiaev.model.ModelManager;
import ru.derendiaev.model.RandomDirectionGenerator;
import ru.derendiaev.model.object.CellObject;
import ru.derendiaev.model.object.FrogObject;

/**
 * Created by DDerendiaev on 05-Feb-22.
 */
public class FrogThread extends MovableThread<FrogObject> {

  /**
   * FrogThread constructor.
   */
  public FrogThread(FrogObject fieldObject, Field field, ModelManager manager) {
    super(fieldObject, field, manager);
  }

  @Override
  public void move() {
    Coords currentCoords = fieldObject.getHeadCellObject().getCoords();
    Coords nextCoords = getNextHeadCoords(fieldObject.getDirection());

    fieldObject.setNewCoords(getNextHeadCoords(fieldObject.getDirection()));

    CellObject cellObject = fieldObject.getHeadCellObject();
    field.setCellObjectByCoords(cellObject, nextCoords);
    field.deleteCellObjectByCoords(currentCoords);

    changeDirection();
  }

  @Override
  public boolean canObjectMove() {
    Coords nextHeadCoords = getNextHeadCoords(fieldObject.getDirection());

    if (field.isCollision(nextHeadCoords)) {
      return false;
    }

    return field.getCellObjectByCoords(nextHeadCoords) == null;
  }

  private void changeDirection() {
    fieldObject.setDirection(RandomDirectionGenerator.getRandomObjectDirection());
  }
}
