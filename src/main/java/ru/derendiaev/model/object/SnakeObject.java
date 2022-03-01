package ru.derendiaev.model.object;

import java.util.List;
import java.util.stream.Collectors;
import ru.derendiaev.model.Coords;

/**
 * Created by DDerendiaev on 26-Feb-22.
 */
public class SnakeObject extends MovableObject {

  private final List<Cell> cells;

  /**
   * SnakeObject constructor.
   */
  public SnakeObject(List<Cell> cells, Direction direction, int speed) {
    super(direction, speed);
    this.cells = cells;
    updateTypes();
  }

  @Override
  public void setCoords(Coords coords) {
    for (int i = 0; i < cells.size(); i++) {
      Cell object = cells.get(i);

      if (i == 0) {
        object.setCoords(cells.get(i + 1).getCoords());
      } else if (i == cells.size() - 1) {
        object.setCoords(coords);
      } else {
        object.setCoords(cells.get(i + 1).getCoords());
      }
    }
  }

  @Override
  public Coords getCoords() {
    return cells.get(cells.size() - 1).getCoords();
  }

  public void growSnake(Cell newCell) {
    cells.add(newCell);
    updateTypes();
  }

  public Coords getTailCoords() {
    return cells.get(0).getCoords();
  }

  /**
   * Get list of Body Coords.
   */
  public List<Coords> getBodyCoords() {
    return cells.stream()
        .filter(cell -> cell.getType() == Type.BODY)
        .map(Cell::getCoords).collect(Collectors.toList());
  }

  private void updateTypes() {
    for (int i = 0; i < cells.size(); i++) {
      if (i == 0) {
        cells.get(i).setType(Type.TAIL);
      } else if (i == cells.size() - 1) {
        cells.get(i).setType(Type.HEAD);
      } else {
        cells.get(i).setType(Type.BODY);
      }
    }
  }
}
