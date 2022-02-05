package ru.derendiaev.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * Created by DDerendiaev on 01-Feb-22.
 */
public class Field {

  @Getter
  private final List<Cell> cells = new ArrayList<>();

  /**
   * Field constructor.
   */
  public Field(int fieldSizeX, int fieldSizeY) {
    for (int i = 0; i < fieldSizeX; i++) {
      for (int j = 0; j < fieldSizeY; j++) {
        cells.add(new Cell(i, j));
      }
    }
  }
}
