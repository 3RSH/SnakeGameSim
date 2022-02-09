package ru.derendiaev.model.thread;

import static java.lang.Thread.sleep;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import ru.derendiaev.model.CellType;
import ru.derendiaev.model.Field;
import ru.derendiaev.model.object.Cell;
import ru.derendiaev.model.object.MovableObject;

/**
 * Created by DDerendiaev on 05-Feb-22.
 */
public class SnakeThread extends MovableThread {

  private final PropertyChangeSupport observer = new PropertyChangeSupport(this);

  /**
   * SnakeThread constructor.
   */
  public SnakeThread(MovableObject object, Field field, PropertyChangeListener listener) {
    super(object, field);
    observer.addPropertyChangeListener(listener);
  }

  @Override
  public void run() {
    while (true) {
      try {
        sleep(1000 / object.getSpeed());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      nextCell = getNextCell();

      if (canObjectMove()) {
        move();
      } else {
        observer.firePropertyChange("dieThread", true, false);
        break;
      }
    }
  }

  @Override
  void move() {
    List<Cell> cells = object.getCells();

    //Fix old coords.
    List<Cell> oldCells = new ArrayList<>(cells);

    //Head setting.
    cells.set(0, nextCell);

    int bodySize = oldCells.size() - 1;

    //Check nextCell and grow snake.
    if (isGrow()) {
      bodySize = oldCells.size();
      cells.add(cells.get(cells.size() - 1));
    }

    //Body shift.
    for (int i = 0; i < bodySize; i++) {
      cells.set(i + 1, oldCells.get(i));
    }

    //Field's types change.
    int cellX;
    int cellY;

    for (int i = 0; i < cells.size(); i++) {
      cellX = cells.get(i).getCellX();
      cellY = cells.get(i).getCellY();

      if (i == 0) {
        field.getCoords()[cellX][cellY] = CellType.HEAD;
      } else if (i == cells.size() - 1) {
        field.getCoords()[cellX][cellY] = CellType.TAIL;
      } else {
        field.getCoords()[cellX][cellY] = CellType.BODY;
      }
    }

    if (cells.size() == oldCells.size()) {
      Cell oldCell = oldCells.get(oldCells.size() - 1);
      oldCells.clear();
      oldCells.add(oldCell);
      cellX = oldCell.getCellX();
      cellY = oldCell.getCellY();
      field.getCoords()[cellX][cellY] = CellType.FREE;
    } else {
      oldCells.clear();
    }

    //Fire cells change.
    observer.firePropertyChange("changeCells", oldCells, cells);
  }

  @Override
  boolean canObjectMove() {
    int nextX = nextCell.getCellX();
    int nextY = nextCell.getCellY();

    //Collision check.
    if (isCollision(nextX, nextY)) {
      return false;
    }

    //CellType check and return.
    CellType nextCellType = field.getCoords()[nextX][nextY];

    return nextCellType != CellType.BODY
        && nextCellType != CellType.TAIL;
  }

  private boolean isGrow() {
    int cellX = nextCell.getCellX();
    int cellY = nextCell.getCellY();

    return field.getCoords()[cellX][cellY] == CellType.FROG;
  }
}
