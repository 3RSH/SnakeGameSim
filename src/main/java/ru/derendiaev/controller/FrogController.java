package ru.derendiaev.controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import ru.derendiaev.model.Frog;
import ru.derendiaev.model.Snake;

/**
 * Created by DDerendiaev on 21-Jan-22.
 */
public class FrogController {

  private final Snake snake;
  private final Frog frog;
  private final PropertyChangeSupport support;

  /**
   * Frog controller constructor.
   */
  public FrogController(Frog frog, Snake snake) {
    this.frog = frog;
    this.snake = snake;
    support = new PropertyChangeSupport(this);
  }

  public int getX() {
    return frog.getFrogX();
  }

  public int getY() {
    return frog.getFrogY();
  }

  /**
   * Move frog method.
   */
  public void moveFrog() {
    int oldX = frog.getFrogX();
    int oldY = frog.getFrogY();
    frog.move(snake.getSnakeX(), snake.getSnakeY(), snake.getCurrentSize());
    support.firePropertyChange("moveX", oldX, frog.getFrogX());
    support.firePropertyChange("moveY", oldY, frog.getFrogY());
  }

  public boolean isLive() {
    return frog.isLive();
  }

  public void respawnFrog() {
    frog.respawn(snake.getSnakeX(), snake.getSnakeY(), snake.getCurrentSize());
  }

  public void kill() {
    frog.setLive(false);
  }

  public void initFrog() {
    frog.init(snake.getSnakeX(), snake.getSnakeY(), snake.getCurrentSize());
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }
}
