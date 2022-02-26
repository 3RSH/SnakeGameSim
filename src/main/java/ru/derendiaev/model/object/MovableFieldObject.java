package ru.derendiaev.model.object;

import lombok.Getter;
import lombok.Setter;
import ru.derendiaev.model.Coords;

/**
 * Created by DDerendiaev on 26-Feb-22.
 */
public abstract class MovableFieldObject {

  @Getter
  @Setter
  private Direction direction;

  @Getter
  @Setter
  private int speed;

  public MovableFieldObject(Direction direction, int speed) {
    this.direction = direction;
    this.speed = speed;
  }

  public abstract void setNewCoords(Coords newHeadCoords);

  public abstract CellObject getHeadCellObject();
}
