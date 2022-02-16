package ru.derendiaev.model.thread;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import ru.derendiaev.model.CellType;
import ru.derendiaev.model.CollisionExeption;
import ru.derendiaev.model.Field;
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

  public SnakeThread(MovableObject fieldObject, Field field,
      PropertyChangeListener listener) {
    super(fieldObject, field, listener);
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

      fieldObject.setAllCoords(nextObjectCoords);
      observer.firePropertyChange("changeObjectCoords", currentObjectCoords, nextObjectCoords);


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
