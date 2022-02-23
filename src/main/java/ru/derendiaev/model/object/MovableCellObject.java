package ru.derendiaev.model.object;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by DDerendiaev on 07-Feb-22.
 */
public class MovableCellObject extends CellObject {

  @Getter
  @Setter
  private Direction direction;

  @Getter
  @Setter
  private int speed;

  /**
   * MovableCellObject constructor.
   */
  public MovableCellObject(ObjectType type, Direction direction, int speed) {
    super(type);
    this.direction = direction;
    this.speed = speed;
  }
}
