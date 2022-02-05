package ru.derendiaev.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DDerendiaev on 05-Feb-22.
 */
public class SnakeThread extends EntityThread {

  /**
   * SnakeThread constructor.
   */
  public SnakeThread(Entity entity, Field field) {
    super(entity, field);
  }

  @Override
  void move(Cell nextCell) {
    List<Cell> oldCells = entity.getCells();
    List<Cell> newCells = new ArrayList<>();

    if (nextCell.getType() == CellType.FREE) {
      nextCell.setType(CellType.SNAKE);
      newCells.add(nextCell);

      for (int i = 0; i < oldCells.size(); i++) {
        if (i != oldCells.size() - 1) {
          newCells.add(oldCells.get(i));
        } else {
          oldCells.get(i).setType(CellType.FREE);
        }
      }
    } else if (nextCell.getType() == CellType.FROG) {
      int points = entity.getPoints();
      entity.setPoints(++points);
      nextCell.setType(CellType.SNAKE);
      newCells.add(nextCell);
      newCells.addAll(oldCells);
    }

    entity.setCells(newCells);
  }

  @Override
  boolean canEntityMove(Cell cell) {
    return cell != null && cell.getType() != CellType.SNAKE;
  }
}
