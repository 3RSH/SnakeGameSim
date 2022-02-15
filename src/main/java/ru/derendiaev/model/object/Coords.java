package ru.derendiaev.model.object;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by DDerendiaev on 02-Feb-22.
 */
public class Coords {

  @Getter
  @Setter
  private int coordX;

  @Getter
  @Setter
  private int coordY;

  /**
   * Cell constructor.
   */
  public Coords(int coordX, int coordY) {
    this.coordX = coordX;
    this.coordY = coordY;
  }
}
