package ru.derendiaev.model.thread;

import lombok.Getter;
import lombok.Setter;
import ru.derendiaev.model.CellType;
import ru.derendiaev.model.Field;
import ru.derendiaev.model.ModelManager;
import ru.derendiaev.model.object.Coords;
import ru.derendiaev.model.object.MovableObject;

/**
 * Created by DDerendiaev on 05-Feb-22.
 */
public class FrogThread extends MovableThread {

  @Setter
  @Getter
  private int index;

  public FrogThread(MovableObject fieldObject, Field field, ModelManager manager) {
    super(fieldObject, field, manager);
  }

  @Override
  void move(Coords nextHeadCoords) {
    Coords currentHeadCoords = fieldObject.getAllCoords().get(0);

    if (field.getCoordsCellType(currentHeadCoords) != CellType.FROG) {
      manager.respawnFrog(index);

    } else if (canObjectMove(nextHeadCoords)) {
      field.setCoordsCellType(nextHeadCoords, CellType.FROG);
      field.setCoordsCellType(currentHeadCoords, CellType.FREE);
      fieldObject.getAllCoords().set(0, nextHeadCoords);
    }
  }

  private boolean canObjectMove(Coords nextHeadCoords) {
    CellType nextCellType = field.getCoordsCellType(nextHeadCoords);

    return nextCellType != CellType.FREE;
  }
}
