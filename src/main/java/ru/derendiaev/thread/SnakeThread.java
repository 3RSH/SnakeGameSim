package ru.derendiaev.thread;

import static java.lang.Thread.sleep;

import ru.derendiaev.controller.SnakeController;

/**
 * Created by DDerendiaev on 19-Jan-22.
 */
public class SnakeThread implements Runnable {

  private final SnakeController controller;

  public SnakeThread(SnakeController controller) {
    this.controller = controller;
  }

  @Override
  public void run() {
    while (controller.isLive()) {
      try {
        sleep(300);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      controller.moveSnake();
    }
  }
}
