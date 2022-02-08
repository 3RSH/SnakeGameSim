package ru.derendiaev.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by DDerendiaev on 01-Feb-22.
 */
public class Field {

  @Getter
  @Setter
  private CellType[][] coords;

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
