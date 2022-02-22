package ru.derendiaev.model;

import java.util.Random;
import ru.derendiaev.model.object.Coords;

/**
 * Created by DDerendiaev on 01-Feb-22.
 */
public class Field {

  private final CellType[][] fieldCoords;

  /**
   * Field constructor.
   */
  public Field(int fieldSizeX, int fieldSizeY) {
    fieldCoords = new CellType[fieldSizeX][fieldSizeY];
  }

  /**
   * Get CellType by coordinates.
   */
  public synchronized CellType getCoordsCellType(Coords cellCoords) {
    if (isCollision(cellCoords)) {
      return null;
    }

    return fieldCoords[cellCoords.getCoordX()][cellCoords.getCoordY()];
  }

  /**
   * Set CellType by coordinates.
   */
  public synchronized void setCoordsCellType(Coords cellCoords, CellType type) {
    int cellX = cellCoords.getCoordX();
    int cellY = cellCoords.getCoordY();
    fieldCoords[cellX][cellY] = type;
  }

  /**
   * Get list of coordinates for new frog-object.
   */
  public synchronized Coords getAnyFreeCoords() {
    int frogX;
    int frogY;
    Random random = new Random();

    do {
      frogX = random.nextInt(fieldCoords.length);
      frogY = random.nextInt(fieldCoords[0].length);
    } while (fieldCoords[frogX][frogY].getName() != null);

    return new Coords(frogX, frogY);
  }

  private boolean isCollision(Coords cellCoords) {
    int cellX = cellCoords.getCoordX();
    int cellY = cellCoords.getCoordY();

    return cellX > fieldCoords.length - 1 || cellX < 0
        || cellY > fieldCoords[cellX].length - 1 || cellY < 0;
  }
}
