package ru.derendiaev.model.mvc.model;

import static java.lang.Thread.sleep;

/**
 * Created by DDerendiaev on 02-Feb-22.
 */
public class EntityThread implements Runnable {

  private final Movable entity;

  public EntityThread(Movable entity) {
    this.entity = entity;
  }

  @Override
  public void run() {
    while (entity.isLive()) {
      try {
        sleep(1000 / entity.getSpeed());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      entity.move();
    }
  }
}
