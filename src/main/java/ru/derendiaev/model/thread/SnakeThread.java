package ru.derendiaev.model.thread;

import java.beans.PropertyChangeListener;
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
  public SnakeThread(
      SnakeObject fieldObject, Field field, ModelManager manager, PropertyChangeListener listener) {
    super(fieldObject, field, manager, listener);
  }

  @Override
  public void move() {
    synchronized (field) {
      Coords nextCoords = getNextCoords();

      if (canGrow(nextCoords)) {
        Cell frogCell = field.getCellObjectByCoords(nextCoords);

        manager.killFrog(frogCell, object);
        object.growSnake(frogCell);

        List<Cell> snakeCells = getSnakeCells();
        List<Coords> snakeCoords = snakeCells.stream().map(Cell::getCoords)
            .collect(Collectors.toList());
        observer.firePropertyChange("grow", null, snakeCoords);

        manager.respawnFrog();

      } else {
        List<Cell> snakeCells = getSnakeCells();
        Coords tailCoords = object.getTailCoords();
        field.deleteCellObjectByCoords(tailCoords);
        object.setCoords(nextCoords);
        snakeCells
            .forEach(cellObject -> field.setCellObjectByCoords(cellObject, cellObject.getCoords()));
        List<Coords> newCoords = snakeCells.stream().map(Cell::getCoords)
            .collect(Collectors.toList());

        observer.firePropertyChange("move", tailCoords, newCoords);
      }
    }
  }

  @Override
  public boolean canMove() throws StopModelException {
    Coords nextHeadCoords = getNextCoords();
    Cell nextCell = field.getCellObjectByCoords(nextHeadCoords);

    if ((nextCell == null && !field.isCollision(nextHeadCoords))
        || (nextCell != null && nextCell.getType() == Type.FROG)) {
      return true;
    }

    throw new StopModelException();
  }

  private boolean canGrow(Coords nextHeadCoords) {
    if (field.getCellObjectByCoords(nextHeadCoords) != null) {
      return field.getCellObjectByCoords(nextHeadCoords).getType() == Type.FROG;
    }

    return false;
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
