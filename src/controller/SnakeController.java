package controller;

import model.Snake;
import model.Snake.Direction;
import model.SnakeThread;
import view.GameField;

/**
 * Created by DDerendiaev on 19-Jan-22.
 */
public class SnakeController {

  private final GameField gameField;
  private final Snake snake;

  public SnakeController(GameField gameField) {
    this.gameField = gameField;
    snake = new Snake(Direction.RIGHT, 3, 20, 20);
  }

  public void initThread() {
    SnakeThread snakeThread = new SnakeThread(snake, this);
    new Thread(snakeThread).start();
  }

  public void repaintSnake() {
    gameField.repaint();
  }

  public void changeDirection(Direction direction) {
    snake.setDirection(direction);
  }

  public int getSnakeSize() {
    return snake.getSize();
  }

  public int[] getSnakeX() {
    return snake.getSnakeX();
  }

  public int[] getSnakeY() {
    return snake.getSnakeY();
  }

  public boolean snakeIsLive() {
    return snake.isLive();
  }

  public Direction getSnakeDirection() {
    return snake.getDirection();
  }

  public void growSnake() {
    snake.growSnake();
  }
}
