package ru.derendiaev.model.object;

import java.util.List;
import lombok.Getter;

/**
 * Created by DDerendiaev on 05-Feb-22.
 */
public class FieldObject {

  @Getter
  private final List<Cell> cells;

  /**
   * Field object constructor.
   */
  public FieldObject(List<Cell> cells) {
    this.cells = cells;
  }
}
