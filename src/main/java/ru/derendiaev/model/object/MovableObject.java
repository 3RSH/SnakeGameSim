package ru.derendiaev.model.object;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by DDerendiaev on 07-Feb-22.
 */
public class MovableObject extends FieldObject {

  @Getter
  @Setter
  private Direction direction;

  @Getter
  @Setter
  private int speed;

  /**
   * Field fieldObject constructor.
   */
  public MovableObject(List<Coords> allObjectCoords, Direction direction, int speed) {
    super(allObjectCoords);
    this.direction = direction;
    this.speed = speed;
  }
}
