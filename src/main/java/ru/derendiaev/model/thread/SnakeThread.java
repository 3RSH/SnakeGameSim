package ru.derendiaev.model.thread;

import java.util.ArrayList;
import java.util.List;
import ru.derendiaev.model.CellType;
import ru.derendiaev.model.Field;
import ru.derendiaev.model.ModelManager;
import ru.derendiaev.model.object.Coords;
import ru.derendiaev.model.object.MovableObject;

/**
 * Created by DDerendiaev on 05-Feb-22.
 */
public class SnakeThread extends MovableThread {

  public SnakeThread(MovableObject fieldObject, Field field, ModelManager manager) {
    super(fieldObject, field, manager);
  }

  @Override
  void move(Coords nextHeadCoords) {
    if (canObjectMove(nextHeadCoords)) {
      List<Coords> currentObjectCoords = fieldObject.getAllCoords();
      List<Coords> nextObjectCoords = new ArrayList<>(currentObjectCoords);

      nextObjectCoords.set(0, nextHeadCoords);

      for (int i = 1; i < nextObjectCoords.size(); i++) {
        nextObjectCoords.set(i, currentObjectCoords.get(i - 1));
      }

      if (canObjectGrow(nextHeadCoords)) {
        nextObjectCoords.add(currentObjectCoords.get(currentObjectCoords.size() - 1));
      }

      changeField(currentObjectCoords, nextObjectCoords);
      fieldObject.setAllCoords(nextObjectCoords);

    } else {
      manager.stopModel();
    }
  }

  @Override
  boolean canObjectMove(Coords nextHeadCoords) {
    CellType nextCellType = field.getCoordsCellType(nextHeadCoords);

    return nextCellType == CellType.FREE || nextCellType == CellType.FROG;
  }

  public MovableObject getSnake() {
    return fieldObject;
  }

  private boolean canObjectGrow(Coords nextHeadCoords) {
    int cellX = nextHeadCoords.getCoordX();
    int cellY = nextHeadCoords.getCoordY();

    return field.getFieldCoords()[cellX][cellY] == CellType.FROG;
  }

  private void changeField(List<Coords> currentObjectCoords, List<Coords> nextObjectCoords) {

    for (int i = 0; i < nextObjectCoords.size(); i++) {
      if (i == 0) {
        field.setCoordsCellType(nextObjectCoords.get(i), CellType.HEAD);
      } else if (i == nextObjectCoords.size() - 1) {
        field.setCoordsCellType(nextObjectCoords.get(i), CellType.TAIL);
      } else {
        field.setCoordsCellType(nextObjectCoords.get(i), CellType.BODY);
      }
    }

    if (nextObjectCoords.size() == currentObjectCoords.size()) {
      Coords currentTailCoords = currentObjectCoords.get(currentObjectCoords.size() - 1);
      field.setCoordsCellType(currentTailCoords, CellType.FREE);
    }
  }
}
