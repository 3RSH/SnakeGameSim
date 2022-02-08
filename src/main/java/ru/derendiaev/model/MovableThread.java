package ru.derendiaev.model;

/**
 * Created by DDerendiaev on 03-Feb-22.
 */
public abstract class MovableThread implements Runnable {

  protected final MovableObject object;
  protected final Field field;

  protected boolean isLive;
  protected Cell nextCell;

  /**
   * Thread constructor.
   */
  public MovableThread(MovableObject object, Field field) {
    this.object = object;
    this.field = field;
    isLive = true;
  }

  @Override
  public abstract void run();

  abstract void move();

  abstract boolean canObjectMove();

  abstract Cell getNextCell();
}
