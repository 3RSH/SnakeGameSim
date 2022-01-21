package model;

import static java.lang.Thread.sleep;

import controller.SnakeController;

/**
 * Created by DDerendiaev on 19-Jan-22.
 */
public class SnakeThread implements Runnable {

  private final Snake snake;
  private final SnakeController controller;

  public SnakeThread(Snake snake, SnakeController controller) {
    this.snake = snake;
    this.controller = controller;
  }

  @Override
  public void run() {
    while (snake.isLive()) {
      try {
        sleep(300);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      snake.move();
      snake.checkCollisions();
      controller.repaintSnake();
    }
  }
}
