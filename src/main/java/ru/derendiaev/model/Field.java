package ru.derendiaev.model;

import ru.derendiaev.model.object.CellObject;

/**
 * Created by DDerendiaev on 01-Feb-22.
 */
public class Field {

  private final CellObject[][] objects;

  /**
   * Field constructor.
   */
  public Field(int fieldSizeX, int fieldSizeY) {
    objects = new CellObject[fieldSizeX][fieldSizeY];
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
  public synchronized CellObject getObjectByCoords(Coords coords) {
    if (isCollision(coords)) {
      return null;
    }

    return objects[coords.getCoordX()][coords.getCoordY()];
  }

  /**
   * Set FieldObject by coordinates.
   */
  public synchronized void setObjectByCoords(CellObject object, Coords coords) {
    if (!isCollision(coords)) {
      object.setCoords(coords);
      objects[coords.getCoordX()][coords.getCoordY()] = object;
    }
  }

  /**
   * Delete FieldObject by coordinates.
   */
  public synchronized void deleteObjectByCoords(Coords coords) {
    if (!isCollision(coords)) {
      objects[coords.getCoordX()][coords.getCoordY()] = null;
    }
  }
}
