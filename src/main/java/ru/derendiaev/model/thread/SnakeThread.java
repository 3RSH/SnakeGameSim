package ru.derendiaev.model.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import ru.derendiaev.model.Coords;
import ru.derendiaev.model.Field;
import ru.derendiaev.model.ModelManager;
import ru.derendiaev.model.StopModelException;
import ru.derendiaev.model.object.Cell;
import ru.derendiaev.model.object.SnakeObject;
import ru.derendiaev.model.object.Type;

/**
 * Created by DDerendiaev on 05-Feb-22.
 */
public class SnakeThread extends MovableThread<SnakeObject> {

  /**
   * SnakeThread constructor.
   */
  public SnakeThread(SnakeObject fieldObject, Field field, ModelManager manager) {
    super(fieldObject, field, manager);
  }

  @Override
  public void move() {
    synchronized (field) {
      Coords nextCoords = getNextCoords();

      if (canGrow(nextCoords)) {
        Cell frogCell = field.getCellObjectByCoords(nextCoords);

        manager.killFrog(frogCell, object);
        object.growSnake(frogCell);
        manager.respawnFrog();

      } else {
        List<Cell> snakeCells = getSnakeCells();
        field.deleteCellObjectByCoords(object.getTailCoords());
        object.setCoords(nextCoords);
        snakeCells
            .forEach(cellObject -> field.setCellObjectByCoords(cellObject, cellObject.getCoords()));
      }
    }
  }

  @Override
  public boolean canMove() throws StopModelException {
    Coords nextHeadCoords = getNextCoords();
    Cell nextCell = field.getCellObjectByCoords(nextHeadCoords);

    if (field.isCollision(nextHeadCoords)
        || nextCell == null
        || nextCell.getType() == Type.FROG) {

      throw new StopModelException();
    }

    return true;
  }

  private boolean canGrow(Coords nextHeadCoords) {
    return field.getCellObjectByCoords(nextHeadCoords).getType() == Type.FROG;
  }

  private List<Cell> getSnakeCells() {
    Coords headCoords = object.getCoords();
    List<Coords> bodyCoords = object.getBodyCoords();
    Coords tailCoords = object.getTailCoords();

    List<Cell> snakeCells = new ArrayList<>();

    snakeCells.add(field.getCellObjectByCoords(tailCoords));
    snakeCells.addAll(
        bodyCoords.stream()
            .map(field::getCellObjectByCoords)
            .collect(Collectors.toList())
    );
    snakeCells.add(field.getCellObjectByCoords(headCoords));

    return snakeCells;
  }
}
