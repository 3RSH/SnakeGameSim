package ru.derendiaev.model.object;

import lombok.Getter;
import lombok.Setter;
import ru.derendiaev.model.Coords;


/**
 * Created by DDerendiaev on 26-Feb-22.
 */
@Getter
@Setter
public abstract class MovableObject {

  private Direction direction;
  private int speed;

  public MovableObject(Direction direction, int speed) {
    this.direction = direction;
    this.speed = speed;
  }

  public abstract void setCoords(Coords coords);

  public abstract Coords getCoords();
}
