package ru.derendiaev.model.thread;

import lombok.Getter;
import lombok.Setter;
import ru.derendiaev.model.CellType;
import ru.derendiaev.model.Field;
import ru.derendiaev.model.ModelManager;
import ru.derendiaev.model.RandomDirectionGenerator;
import ru.derendiaev.model.object.Coords;
import ru.derendiaev.model.object.MovableObject;

/**
 * Created by DDerendiaev on 05-Feb-22.
 */
public class FrogThread extends MovableThread {

  @Setter
  @Getter
  private int index;

  @Getter
  @Setter
  private String name;

  public FrogThread(MovableObject fieldObject, Field field, ModelManager manager) {
    super(fieldObject, field, manager);
  }

  @Override
  void move(Coords nextHeadCoords) {
    Coords currentHeadCoords = fieldObject.getAllCoords().get(0);
    CellType frog = new CellType();
    frog.setName(name);

    if (canObjectMove(nextHeadCoords)) {
      field.setCoordsCellType(nextHeadCoords, frog);
      field.setCoordsCellType(currentHeadCoords, new CellType());
      fieldObject.getAllCoords().set(0, nextHeadCoords);
    }

    changeDirection();
  }

  private boolean canObjectMove(Coords nextHeadCoords) {
    CellType nextCellType = field.getCoordsCellType(nextHeadCoords);

    return nextCellType.getName() != null;
  }

  private void changeDirection() {
    fieldObject.setDirection(RandomDirectionGenerator.getRandomObjectDirection());
  }
}
