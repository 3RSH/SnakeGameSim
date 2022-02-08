package ru.derendiaev.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by DDerendiaev on 02-Feb-22.
 */
public class Cell {

  @Getter
  @Setter
  private int cellX;

  @Getter
  @Setter
  private int cellY;

  @Getter
  @Setter
  private CellType type;

  /**
   * Cell constructor.
   */
  public Cell(int cellX, int cellY) {
    this.cellX = cellX;
    this.cellY = cellY;
    type = CellType.FREE;
  }
}
