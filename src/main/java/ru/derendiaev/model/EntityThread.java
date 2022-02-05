package ru.derendiaev.model;

import static java.lang.Thread.sleep;

/**
 * Created by DDerendiaev on 03-Feb-22.
 */
public abstract class EntityThread implements Runnable {

  final Entity entity;
  final Field field;

  /**
   * Thread constructor.
   */
  public EntityThread(Entity entity, Field field) {
    this.entity = entity;
    this.field = field;
  }

  @Override
  public void run() {
    while (entity.isLive()) {
      try {
        sleep(1000 / entity.getSpeed());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      Cell nextCell = getNextCell();

      if (canEntityMove(nextCell)) {
        move(nextCell);
      } else if (entity.getCells().get(0).getType() == CellType.SNAKE) {
        entity.kill();
      }
    }
  }

  abstract void move(Cell nextCell);

  abstract boolean canEntityMove(Cell cell);

  private Cell getNextCell() {
    Cell head = entity.getCells().get(0);

    int headX = head.getCellX();
    int headY = head.getCellY();

    switch (entity.getDirection()) {
      case RIGHT:
        headX++;
        break;
      case LEFT:
        headX--;
        break;
      case DOWN:
        headY++;
        break;
      case UP:
        headY--;
        break;
      default:
        return null;
    }

    int finalHeadX = headX;
    int finalHeadY = headY;

    return field.getCells()
        .stream()
        .filter(cell -> cell.getCellX() == finalHeadX && cell.getCellY() == finalHeadY)
        .findFirst().orElse(null);
  }


}
