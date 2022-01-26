package ru.derendiaev.thread;

import static java.lang.Thread.sleep;

import ru.derendiaev.controller.FrogController;

/**
 * Created by DDerendiaev on 21-Jan-22.
 */
public class FrogThread implements Runnable {

  private final FrogController controller;

  public FrogThread(FrogController controller) {
    this.controller = controller;
  }

  @Override
  public void run() {
    while (controller.isLive()) {
      controller.moveFrog();

      try {
        sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
