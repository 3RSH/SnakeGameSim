package ru.derendiaev.model;

import static java.lang.Thread.sleep;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

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
    while (isLive) {
      try {
        sleep(1000 / object.getSpeed());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      nextCell = getNextCell();

      if (canObjectMove()) {
        move();
      } else {
        isLive = false;
      }
    }
  }

  @Override
  void move() {
    object.getCells().forEach(cell -> {
      field.getCoords()[cell.getCellX()][cell.getCellY()] = CellType.FREE;
      Cell freeCell = new Cell(cell.getCellX(), cell.getCellY());
      observer.firePropertyChange("changeCell", cell, freeCell);

      if (nextCell.getType() == CellType.FROG && cell.getType() == CellType.TAIL) {
        Cell newCell = new Cell(cell.getCellX(), cell.getCellY());
        newCell.setType(cell.getType());
        cell.setType(CellType.BODY);
        object.getCells().add(newCell);
      }

      Direction direction = object.getDirection();

      if (direction == Direction.RIGHT) {
        cell.setCellX(cell.getCellX() + 1);
      } else if (direction == Direction.LEFT) {
        cell.setCellX(cell.getCellX() - 1);
      } else if (direction == Direction.DOWN) {
        cell.setCellY(cell.getCellY() + 1);
      } else if (direction == Direction.UP) {
        cell.setCellY(cell.getCellY() - 1);
      }
    });

    object.getCells().forEach(cell -> {
      Cell newCell = new Cell(cell.getCellX(), cell.getCellY());
      field.getCoords()[cell.getCellX()][cell.getCellY()] = cell.getType();
      observer.firePropertyChange("changeCell", newCell, cell);
    });
  }

  @Override
  boolean canObjectMove() {
    return nextCell != null
        && nextCell.getType() != CellType.BODY
        && nextCell.getType() != CellType.TAIL;
  }

  @Override
  Cell getNextCell() {
    Cell head = object.getCells().stream()
        .filter(cell -> cell.getType() == CellType.HEAD)
        .findFirst()
        .orElse(null);

    if (head != null) {
      int headX = head.getCellX();
      int headY = head.getCellY();

      Direction direction = object.getDirection();

      if (direction == Direction.RIGHT) {
        headX++;
      } else if (direction == Direction.LEFT) {
        headX--;
      } else if (direction == Direction.DOWN) {
        headY++;
      } else if (direction == Direction.UP) {
        headY--;
      }

      if (headX > field.getCoords().length - 1 || headX < 0
          || headY > field.getCoords()[headX].length - 1 || headY < 0) {
        return null;
      }

      CellType nextCellType = field.getCoords()[headX][headY];
      Cell nextCell = new Cell(headX, headY);
      nextCell.setType(nextCellType);

      return nextCell;
    }

    return null;
  }
}
