package ru.derendiaev.model;

import static java.lang.Thread.sleep;

/**
 * Created by DDerendiaev on 03-Feb-22.
 */
public class SnakeThread implements Runnable {

  private final Snake snake;
  private final Field field;

  public SnakeThread(Snake snake, Field field) {
    this.snake = snake;
    this.field = field;
  }

  @Override
  public void run() {
    while (snake.isLive()) {
      Cell newCell;
      Direction direction = snake.getDirection();
      if (direction == Direction.RIGHT) {

        newCell = field.getCells().stream()
            .filter(cell -> cell.getCellX() == snake.getCells().get(0).getCellX() + 1)
            .findFirst()
            .orElse(null);

        if (moveSnake(newCell)) {
          break;
        }
      } else if (direction == Direction.DOWN) {

        newCell = field.getCells().stream()
            .filter(cell -> cell.getCellY() == snake.getCells().get(0).getCellY() + 1)
            .findFirst()
            .orElse(null);

        if (moveSnake(newCell)) {
          break;
        }
      } else if (direction == Direction.LEFT) {

        newCell = field.getCells().stream()
            .filter(cell -> cell.getCellX() == snake.getCells().get(0).getCellX() - 1)
            .findFirst()
            .orElse(null);

        if (moveSnake(newCell)) {
          break;
        }
      } else if (direction == Direction.UP) {

        newCell = field.getCells().stream()
            .filter(cell -> cell.getCellY() == snake.getCells().get(0).getCellY() - 1)
            .findFirst()
            .orElse(null);

        if (moveSnake(newCell)) {
          break;
        }
      }

      try {
        sleep(1000 / snake.getCurrentSpeed());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private boolean moveSnake(Cell cell) {
    if (cell == null) {
      snake.setLive(false);
      field.setInGame(false);
      return false;
    }

    if (cell.isOccupied()) {
      if (snake.getCells().contains(cell)) {
        snake.setLive(false);
        field.setInGame(false);
        return false;
      }

      field.respawnFrog(cell);
    }

    snake.move(cell);
    return true;
  }
}
