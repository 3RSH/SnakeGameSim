package ru.derendiaev.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
  public void run() {
    while (entity.isLive()) {
      super.run();

      if (isEaten()) {
        respawn();
      } else {
        Cell nextCell = getNextCell();

        if (canEntityMove(nextCell)) {
          move(nextCell);
        }
      }
    }
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

  private boolean isEaten() {
    return entity.getCells().get(0).getType() == CellType.SNAKE;
  }

  private void respawn() {
    Random random = new Random();
    int fieldSize = field.getCells().size();
    int cellIndex = random.nextInt(fieldSize);

    while (!canEntityMove(field.getCells().get(cellIndex))) {
      cellIndex = random.nextInt(fieldSize);
    }

    move(field.getCells().get(cellIndex));
  }
}
