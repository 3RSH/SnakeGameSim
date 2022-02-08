package ru.derendiaev.model;

import java.util.List;
import lombok.Getter;

/**
 * Created by DDerendiaev on 05-Feb-22.
 */
public abstract class FieldObject {

  @Getter
  private final List<Cell> cells;

  protected FieldObject(List<Cell> cells) {
    this.cells = cells;
  }
}
