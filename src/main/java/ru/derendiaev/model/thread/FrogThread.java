package ru.derendiaev.model.thread;

import java.beans.PropertyChangeSupport;
import lombok.Getter;
import ru.derendiaev.CellTypeExeption;
import ru.derendiaev.model.CellType;
import ru.derendiaev.model.CollisionExeption;
import ru.derendiaev.model.Field;
import ru.derendiaev.model.object.Coords;
import ru.derendiaev.model.object.MovableObject;

/**
 * Created by DDerendiaev on 05-Feb-22.
 */
public class FrogThread extends MovableThread {

  @Getter
  private final PropertyChangeSupport observer = new PropertyChangeSupport(this);

  public FrogThread(MovableObject fieldObject, Field field) {
    super(fieldObject, field);
  }

  @Override
  void move(Coords nextHeadCoords) {
    try {
      Coords currentHeadCoords = fieldObject.getAllCoords().get(0);

      checkCellTypeExeption(currentHeadCoords);
      checkObjectMove(nextHeadCoords);

      field.setCoordsCellType(nextHeadCoords, CellType.FROG);
      field.setCoordsCellType(currentHeadCoords, CellType.FREE);

      fieldObject.getAllCoords().set(0, nextHeadCoords);

      observer.firePropertyChange("changeObjectCoords", currentHeadCoords, nextHeadCoords);
    } catch (CollisionExeption ignored) {
      //Do nothing because need only interrupt of method.
    } catch (CellTypeExeption e) {
      isLive = false;
      observer.firePropertyChange("threadIsDead", true, false);
    }
  }

  @Override
  void checkObjectMove(Coords nextHeadCoords) throws CollisionExeption {
    CellType nextCellType = field.getCoordsCellType(nextHeadCoords);

    if (nextCellType != CellType.FREE) {
      throw new CollisionExeption();
    }
  }

  private void checkCellTypeExeption(Coords headCoords) throws CollisionExeption, CellTypeExeption {

    if (field.getCoordsCellType(headCoords) != CellType.FROG) {
      throw new CellTypeExeption();
    }
  }
}
