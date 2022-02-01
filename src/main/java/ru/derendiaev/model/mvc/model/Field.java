package ru.derendiaev.model.mvc.model;

import lombok.Getter;

/**
 * Created by DDerendiaev on 01-Feb-22.
 */
public class Field {

  @Getter
  private final int fieldSizeX;

  @Getter
  private final int fieldSizeY;

  public Field(int fieldSizeX, int fieldSizeY) {
    this.fieldSizeX = fieldSizeX;
    this.fieldSizeY = fieldSizeY;
  }
}
