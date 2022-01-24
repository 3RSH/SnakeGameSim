package model;

import static java.lang.Thread.sleep;

import controller.SnakeController;
import model.Snake.Direction;

/**
 * Created by DDerendiaev on 19-Jan-22.
 */
public class SnakeThread implements Runnable {

  private final Snake snake;
  private final SnakeController controller;
  private Direction lastDirection;

  public SnakeThread(Snake snake, SnakeController controller) {
    this.snake = snake;
    this.controller = controller;
  }

  @Override
  public void run() {
    while (snake.isLive()) {
      try {
        sleep(250);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      snake.move();
      snake.checkCollisions();
      controller.repaint();
    }
  }
}
