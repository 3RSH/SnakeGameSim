package ru.derendiaev.model;

import ru.derendiaev.model.object.Cell;

/**
 * Created by DDerendiaev on 01-Feb-22.
 */
public class Field {

  private final Cell[][] objects;

  /**
   * Field constructor.
   */
  public Field(int fieldSizeX, int fieldSizeY) {
    objects = new Cell[fieldSizeX][fieldSizeY];
  }

  /**
   * Check coordinates' collision.
   */
  public boolean isCollision(Coords coords) {
    return coords.getCoordX() > objects.length - 1 || coords.getCoordX() < 0
        || coords.getCoordY() > objects[0].length - 1 || coords.getCoordY() < 0;
  }

  /**
   * Get FieldObject by coordinates.
   */
  public Cell getCellObjectByCoords(Coords coords) {
    if (isCollision(coords)) {
      return null;
    }

    return objects[coords.getCoordX()][coords.getCoordY()];
  }

  /**
   * Set FieldObject by coordinates.
   */
  public void setCellObjectByCoords(Cell cellObject, Coords coords) {
    if (!isCollision(coords)) {
      cellObject.setCoords(coords);
      objects[coords.getCoordX()][coords.getCoordY()] = cellObject;
    }
  }

  /**
   * Delete FieldObject by coordinates.
   */
  public void deleteCellObjectByCoords(Coords coords) {
    if (!isCollision(coords)) {
      objects[coords.getCoordX()][coords.getCoordY()] = null;
    }
  }
}
