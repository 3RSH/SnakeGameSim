package ru.derendiaev.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DDerendiaev on 05-Feb-22.
 */
public class FrogThread extends EntityThread {

  /**
   * FrogThread constructor.
   */
  public FrogThread(Entity entity, Field field) {
    super(entity, field);
  }

  @Override
  void move(Cell nextCell) {
    List<Cell> oldCells = entity.getCells();
    List<Cell> newCells = new ArrayList<>();

    nextCell.setType(CellType.FROG);
    newCells.add(nextCell);
    oldCells.forEach(cell -> cell.setType(CellType.FREE));

    entity.setCells(newCells);
  }

  @Override
  boolean canEntityMove(Cell cell) {
    return cell != null
        && cell.getType() == CellType.FREE;
  }
}
