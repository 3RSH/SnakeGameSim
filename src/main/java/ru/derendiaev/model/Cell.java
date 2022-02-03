package ru.derendiaev.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by DDerendiaev on 02-Feb-22.
 */
public class Cell {

  @Getter
  private final int cellX;

  @Getter
  private final int cellY;

  @Getter
  @Setter
  private boolean isOccupied;

  public Cell(int cellX, int cellY) {
    this.cellX = cellX;
    this.cellY = cellY;
  }
}
