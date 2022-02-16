package ru.derendiaev.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.Getter;
import ru.derendiaev.model.exception.CollisionException;
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

  /**
   * Get CellType by coordinates.
   */
  public CellType getCoordsCellType(Coords cellCoords) throws CollisionException {
    checkCollision(cellCoords);

    return getFieldCoords()[cellCoords.getCoordX()][cellCoords.getCoordY()];
  }

  /**
   * Set CellType by coordinates.
   */
  public void setCoordsCellType(Coords cellCoords, CellType type) {
    int cellX = cellCoords.getCoordX();
    int cellY = cellCoords.getCoordY();
    fieldCoords[cellX][cellY] = type;
  }

  /**
   * Get list of coordinates for new frog-object.
   */
  public List<Coords> getNewFrogCoords() {
    int frogX;
    int frogY;
    Random random = new Random();

    do {
      frogX = random.nextInt(fieldCoords.length);
      frogY = random.nextInt(fieldCoords[0].length);
    } while (fieldCoords[frogX][frogY] != CellType.FREE);

    fieldCoords[frogX][frogY] = CellType.FROG;

    List<Coords> frogCells = new ArrayList<>(1);
    frogCells.add(new Coords(frogX, frogY));

    return frogCells;
  }

  private void checkCollision(Coords cellCoords) throws CollisionException {
    int cellX = cellCoords.getCoordX();
    int cellY = cellCoords.getCoordY();

    if (cellX > fieldCoords.length - 1 || cellX < 0
        || cellY > fieldCoords[cellX].length - 1 || cellY < 0) {
      throw new CollisionException();
    }
  }
}
