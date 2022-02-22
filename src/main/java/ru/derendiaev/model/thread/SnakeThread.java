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

  public MovableObject getSnake() {
    return fieldObject;
  }

  private boolean canObjectMove(Coords nextHeadCoords) {
    CellType nextCellType = field.getCoordsCellType(nextHeadCoords);

    if (nextCellType != null) {
      return nextCellType.getName() == null || nextCellType.getName().matches("frog[0-9]+");
    }

    return false;
  }

  private boolean canObjectGrow(Coords nextHeadCoords) {
    String coordsType = field.getCoordsCellType(nextHeadCoords).getName();

    if (coordsType.matches("frog[0-9]+")) {
      manager.respawnFrog(coordsType);
      return true;
    }

    return false;
  }

  private void changeField(List<Coords> currentObjectCoords, List<Coords> nextObjectCoords) {
    CellType head = new CellType();
    CellType tail = new CellType();
    CellType body = new CellType();
    head.setName("head");
    tail.setName("tail");
    body.setName("body");

    for (int i = 0; i < nextObjectCoords.size(); i++) {
      if (i == 0) {
        field.setCoordsCellType(nextObjectCoords.get(i), head);
      } else if (i == nextObjectCoords.size() - 1) {
        field.setCoordsCellType(nextObjectCoords.get(i), tail);
      } else {
        field.setCoordsCellType(nextObjectCoords.get(i), body);
      }
    }

    if (nextObjectCoords.size() == currentObjectCoords.size()) {
      Coords currentTailCoords = currentObjectCoords.get(currentObjectCoords.size() - 1);
      CellType free = new CellType();
      field.setCoordsCellType(currentTailCoords, free);
    }
  }
}
