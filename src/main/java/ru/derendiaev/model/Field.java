package ru.derendiaev.model;

import lombok.Getter;
import ru.derendiaev.model.object.Coords;

/**
 * Created by DDerendiaev on 01-Feb-22.
 */
public class Field {

  @Getter
  private final CellType[][] fieldCoords;

  /**
   * Field constructor.
   */
  public Field(int fieldSizeX, int fieldSizeY) {
    fieldCoords = new CellType[fieldSizeX][fieldSizeY];

    for (int i = 0; i < fieldSizeX; i++) {
      for (int j = 0; j < fieldSizeY; j++) {
        fieldCoords[i][j] = CellType.FREE;
      }
    }
  }

  public CellType getCoordsCellType(Coords cellCoords) throws CollisionExeption {
    checkCollision(cellCoords);

    return getFieldCoords()[cellCoords.getCoordX()][cellCoords.getCoordY()];
  }

  private void checkCollision(Coords cellCoords) throws CollisionExeption {
    int cellX = cellCoords.getCoordX();
    int cellY = cellCoords.getCoordY();

    if (cellX > fieldCoords.length - 1 || cellX < 0
        || cellY > fieldCoords[cellX].length - 1 || cellY < 0) {
      throw new CollisionExeption();
    }
  }

}
