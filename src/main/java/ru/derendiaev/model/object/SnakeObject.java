package ru.derendiaev.model.object;

import java.util.List;
import lombok.Getter;
import ru.derendiaev.model.Coords;

/**
 * Created by DDerendiaev on 26-Feb-22.
 */
public class SnakeObject extends MovableFieldObject {

  @Getter
  private final List<CellObject> cellObjects;

  /**
   * SnakeObject constructor.
   */
  public SnakeObject(List<CellObject> cellObjects, Direction direction, int speed) {
    super(direction, speed);
    this.cellObjects = cellObjects;
    updateCellObjectsTypes();
  }

  @Override
  public void setNewCoords(Coords newHeadCoords) {
    for (int i = 0; i < cellObjects.size(); i++) {
      CellObject object = cellObjects.get(i);

      if (i == 0) {
        object.setCoords(cellObjects.get(i + 1).getCoords());
      } else if (i == cellObjects.size() - 1) {
        object.setCoords(newHeadCoords);
      } else {
        object.setCoords(cellObjects.get(i + 1).getCoords());
      }
    }
  }

  @Override
  public CellObject getHeadCellObject() {
    return cellObjects.get(cellObjects.size() - 1);
  }

  public void growSnake(CellObject newCellObject) {
    cellObjects.add(newCellObject);
    updateCellObjectsTypes();
  }

  /**
   * Set actually CellObjectType to cellObjects by index.
   */
  public void updateCellObjectsTypes() {
    for (int i = 0; i < cellObjects.size(); i++) {
      if (i == 0) {
        cellObjects.get(i).setType(CellObjectType.TAIL);
      } else if (i == cellObjects.size() - 1) {
        cellObjects.get(i).setType(CellObjectType.HEAD);
      } else {
        cellObjects.get(i).setType(CellObjectType.BODY);
      }
    }
  }
}
