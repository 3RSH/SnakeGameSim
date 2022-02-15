package ru.derendiaev.model.thread;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import ru.derendiaev.model.CellType;
import ru.derendiaev.model.CollisionExeption;
import ru.derendiaev.model.object.Coords;
import ru.derendiaev.model.object.MovableObject;

/**
 * Created by DDerendiaev on 05-Feb-22.
 */
public class SnakeThread extends MovableThread {

  private final PropertyChangeSupport observer = new PropertyChangeSupport(this);

  public MovableObject getSnake() {
    return fieldObject;
  }

  @Override
  void move(Coords nextHeadCoords) {
    try {
      checkObjectMove(nextHeadCoords);

      List<Coords> currentObjectCoords = fieldObject.getAllCoords();
      List<Coords> nextObjectCoords = new ArrayList<>(currentObjectCoords);

      nextObjectCoords.set(0, nextHeadCoords);

      for (int i = 1; i < nextObjectCoords.size(); i++) {
        nextObjectCoords.set(i, currentObjectCoords.get(i - 1));
      }

      if (canObjectGrow(nextHeadCoords)) {
        nextObjectCoords.add(currentObjectCoords.get(currentObjectCoords.size() - 1));
      }

      int cellX;
      int cellY;

      for (int i = 0; i < nextObjectCoords.size(); i++) {
        cellX = nextObjectCoords.get(i).getCoordX();
        cellY = nextObjectCoords.get(i).getCoordY();

        if (i == 0) {
          field.getFieldCoords()[cellX][cellY] = CellType.HEAD;
        } else if (i == nextObjectCoords.size() - 1) {
          field.getFieldCoords()[cellX][cellY] = CellType.TAIL;
        } else {
          field.getFieldCoords()[cellX][cellY] = CellType.BODY;
        }
      }

      if (nextObjectCoords.size() == currentObjectCoords.size()) {
        Coords currentTailCoords = currentObjectCoords.get(currentObjectCoords.size() - 1);
        cellX = currentTailCoords.getCoordX();
        cellY = currentTailCoords.getCoordY();
        field.getFieldCoords()[cellX][cellY] = CellType.FREE;
      }

      fieldObject.setAllCoords(nextObjectCoords);
      observer.firePropertyChange("changeObjectCoords", currentObjectCoords, nextHeadCoords);


    } catch (CollisionExeption e) {
      isLive = false;
      observer.firePropertyChange("threadIsDead", true, false);
    }
  }

  @Override
  void checkObjectMove(Coords nextHeadCoords) throws CollisionExeption {
    CellType nextCellType = field.getCoordsCellType(nextHeadCoords);

    if (nextCellType == CellType.BODY || nextCellType == CellType.TAIL) {
      throw new CollisionExeption();
    }
  }

  private boolean canObjectGrow(Coords nextHeadCoords) {
    int cellX = nextHeadCoords.getCoordX();
    int cellY = nextHeadCoords.getCoordY();

    return field.getFieldCoords()[cellX][cellY] == CellType.FROG;
  }
}
