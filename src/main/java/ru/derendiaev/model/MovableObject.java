package ru.derendiaev.model;

import java.beans.PropertyChangeSupport;
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
   * Field object constructor.
   */
  public MovableObject(List<Cell> cells, Direction direction, int speed) {
    super(cells);
    this.direction = direction;
    this.speed = speed;
  }
}
