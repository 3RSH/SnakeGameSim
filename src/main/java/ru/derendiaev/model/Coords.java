package ru.derendiaev.model;

import lombok.Getter;

/**
 * Created by DDerendiaev on 23-Feb-22.
 */
public class Coords {

  @Getter
  private final int coordX;

  @Getter
  private final int coordY;

  /**
   * Coords constructor.
   */
  public Coords(int coordX, int coordY) {
    this.coordX = coordX;
    this.coordY = coordY;
  }
}
