package ru.derendiaev.view;

import lombok.Getter;

/**
 * Created by DDerendiaev on 09-Mar-22.
 */
public class ViewCoords {

  @Getter
  private final int coordX;

  @Getter
  private final int coordY;

  /**
   * ViewCoords constructor.
   */
  public ViewCoords(int coordX, int coordY) {
    this.coordX = coordX;
    this.coordY = coordY;
  }
}
