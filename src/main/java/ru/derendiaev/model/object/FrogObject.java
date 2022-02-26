package ru.derendiaev.model.object;

import ru.derendiaev.model.Coords;

/**
 * Created by DDerendiaev on 26-Feb-22.
 */
public class FrogObject extends MovableFieldObject {

  private final CellObject cellObject;

  /**
   * FrogObject constructor.
   */
  public FrogObject(CellObject cellObject, Direction direction, int speed) {
    super(direction, speed);
    this.cellObject = cellObject;
    cellObject.setType(CellObjectType.FROG);
  }

  @Override
  public void setNewCoords(Coords newHeadCoords) {
    cellObject.setCoords(newHeadCoords);
  }

  @Override
  public CellObject getHeadCellObject() {
    return cellObject;
  }
}
