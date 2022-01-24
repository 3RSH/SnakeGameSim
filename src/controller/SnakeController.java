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
  private Snake snake;

  /**
   * Snake controller constructor.
   */
  public SnakeController(GameField gameField) {
    this.gameField = gameField;
  }

  /**
   * Snake controller initialization.
   */
  public void init() {
    snake = new Snake(Direction.RIGHT, 3, 20, 20);
    SnakeThread snakeThread = new SnakeThread(snake, this);
    new Thread(snakeThread).start();
  }

  public void repaint() {
    gameField.repaint();
  }

  public void changeDirection(Direction direction) {
    snake.setDirection(direction);
  }

  public int getSize() {
    return snake.getSize();
  }

  public int[] getX() {
    return snake.getSnakeX();
  }

  public int[] getY() {
    return snake.getSnakeY();
  }

  public boolean isLive() {
    return snake.isLive();
  }

  public void grow() {
    snake.growSnake();
  }
}
