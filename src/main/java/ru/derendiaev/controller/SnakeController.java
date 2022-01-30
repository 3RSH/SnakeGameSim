package ru.derendiaev.controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import ru.derendiaev.model.Snake;
import ru.derendiaev.model.Snake.Direction;

/**
 * Created by DDerendiaev on 19-Jan-22.
 */
public class SnakeController {


  private final Snake snake;
  private final PropertyChangeSupport support;

  /**
   * Snake controller constructor.
   */
  public SnakeController(Snake snake) {
    this.snake = snake;
    support = new PropertyChangeSupport(this);
  }

  public Direction getDirection() {
    return snake.getDirection();
  }

  public void turnRight() {
    snake.turnRight();
  }

  public void turnLeft() {
    snake.turnLeft();
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

  public void initSnake() {
    snake.init();
  }

  public void killSnake() {
    snake.setLive(false);
  }

  public int getPoints() {
    return snake.getPoints();
  }

  public int[] getFieldParams() {
    return new int[]{snake.getCellSize(), snake.getFieldCellsX(), snake.getFieldCellsY()};
  }

  /**
   * Move snake method.
   */
  public void moveSnake() {
    int oldHeadX = snake.getHeadX();
    int oldHeadY = snake.getHeadY();
    snake.move();
    support.firePropertyChange("moveX", oldHeadX, snake.getHeadX());
    support.firePropertyChange("moveY", oldHeadY, snake.getHeadY());
    support.firePropertyChange("isLive", true, snake.isLive());
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }
}
