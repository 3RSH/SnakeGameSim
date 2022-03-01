package ru.derendiaev.model.object;

import ru.derendiaev.model.Coords;

/**
 * Created by DDerendiaev on 26-Feb-22.
 */
public class FrogObject extends MovableObject {

  private final Cell cell;

  /**
   * FrogObject constructor.
   */
  public FrogObject(Cell cell, Direction direction, int speed) {
    super(direction, speed);
    this.cell = cell;
    cell.setType(Type.FROG);
  }

  @Override
  public void setCoords(Coords coords) {
    cell.setCoords(coords);
  }

  @Override
  public Coords getCoords() {
    return cell.getCoords();
  }
}
