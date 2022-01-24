package model;

import static java.lang.Thread.sleep;

import controller.FrogController;

/**
 * Created by DDerendiaev on 21-Jan-22.
 */
public class FrogThread implements Runnable {

  private final Frog frog;
  private final FrogController controller;

  public FrogThread(Frog frog, FrogController controller) {
    this.frog = frog;
    this.controller = controller;
  }

  @Override
  public void run() {
    while (frog.isLive()) {
      frog.move();
      controller.repaint();

      try {
        sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
