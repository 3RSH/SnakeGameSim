package ru.derendiaev.model;

import lombok.Getter;

/**
 * Created by DDerendiaev on 01-Feb-22.
 */
public class Field {

  @Getter
  private final CellType[][] coords;

  /**
   * Field constructor.
   */
  public Field(int fieldSizeX, int fieldSizeY) {
    coords = new CellType[fieldSizeX][fieldSizeY];

    for (int i = 0; i < fieldSizeX; i++) {
      for (int j = 0; j < fieldSizeY; j++) {
        coords[i][j] = CellType.FREE;
      }
    }
  }
}
