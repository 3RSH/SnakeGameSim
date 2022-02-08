package ru.derendiaev.model;

import static java.lang.Thread.sleep;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;

/**
 * Created by DDerendiaev on 05-Feb-22.
 */
public class FrogThread extends MovableThread {

  private final PropertyChangeSupport observer = new PropertyChangeSupport(this);

  /**
   * FrogThread constructor.
   */
  public FrogThread(MovableObject object, Field field, PropertyChangeListener listener) {
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

      changeObjectDirection();
      nextCell = getNextCell();

      if (canObjectMove()) {
        move();
      }
    }
  }

  @Override
  void move() {
    Cell cell = object.getCells().get(0);
    field.getCoords()[cell.getCellX()][cell.getCellY()] = CellType.FREE;
    Cell freeCell = new Cell(cell.getCellX(), cell.getCellY());
    observer.firePropertyChange("changeCell", cell, freeCell);
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

    freeCell = new Cell(cell.getCellX(), cell.getCellY());
    field.getCoords()[cell.getCellX()][cell.getCellY()] = cell.getType();
    observer.firePropertyChange("changeCell", freeCell, cell);
  }

  @Override
  boolean canObjectMove() {
    return nextCell != null && nextCell.getType() == CellType.FREE;
  }

  @Override
  Cell getNextCell() {
    Cell cell = object.getCells().get(0);

    int cellX = cell.getCellX();
    int cellY = cell.getCellY();

    Direction direction = object.getDirection();

    if (direction == Direction.RIGHT) {
      cellX++;
    } else if (direction == Direction.LEFT) {
      cellX--;
    } else if (direction == Direction.DOWN) {
      cellY++;
    } else if (direction == Direction.UP) {
      cellY--;
    }

    if (cellX > field.getCoords().length - 1 || cellX < 0
        || cellY > field.getCoords()[cellX].length - 1 || cellY < 0) {
      return null;
    }

    CellType nextCellType = field.getCoords()[cellX][cellY];
    Cell nextCell = new Cell(cellX, cellY);
    nextCell.setType(nextCellType);

    return nextCell;
  }

  private void changeObjectDirection() {
    Random random = new Random();
    int index = random.nextInt(4);

    if (index == 0) {
      object.setDirection(Direction.RIGHT);
    } else if (index == 1) {
      object.setDirection(Direction.DOWN);
    } else if (index == 3) {
      object.setDirection(Direction.LEFT);
    } else {
      object.setDirection(Direction.UP);
    }
  }
}
