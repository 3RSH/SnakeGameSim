package ru.derendiaev.model.mvc.model;

import java.util.Random;
import lombok.Getter;

/**
 * Created by DDerendiaev on 01-Feb-22.
 */
public class NewFrog implements Movable {

  private final int speed;
  private final Field field;

  @Getter
  private int frogX;

  @Getter
  private int frogY;
  private boolean isLive;
  private NewSnake snake;

  /**
   * Frog constructor.
   */
  public NewFrog(int speed, Field field) {
    this.speed = speed;
    this.field = field;
  }

  @Override
  public void move() {
    int direction = new Random().nextInt(4);

    if (direction == 0
        && frogX != field.getFieldSizeX() - 1
        && isCorrectCoords(frogX + 1, frogY)) {

      frogX++;
    } else if (direction == 1
        && frogY != field.getFieldSizeY() - 1
        && isCorrectCoords(frogX, frogY + 1)) {

      frogY++;
    } else if (direction == 2
        && frogX != 0
        && isCorrectCoords(frogX - 1, frogY)) {

      frogX--;
    } else if (direction == 3
        && frogY != 0
        && isCorrectCoords(frogX, frogY - 1)) {
      frogY--;
    }
  }

  @Override
  public int getSpeed() {
    return speed;
  }

  @Override
  public boolean isLive() {
    return isLive;
  }

  /**
   * Frog initialization.
   */
  public void init(NewSnake snake) {
    this.snake = snake;
    respawn();
    isLive = true;
  }

  /**
   * Kill frog.
   */
  public void kill() {
    isLive = false;
  }

  /**
   * Respawn frog.
   */
  public void respawn() {
    int planX = new Random().nextInt(field.getFieldSizeX());
    int planY = new Random().nextInt(field.getFieldSizeY());

    while (!isCorrectCoords(planX, planY)) {
      planX = new Random().nextInt(field.getFieldSizeX());
      planY = new Random().nextInt(field.getFieldSizeY());
    }

    frogX = planX;
    frogY = planY;
  }

  private boolean isCorrectCoords(int planX, int planY) {
    for (int i = 0; i < snake.getCurrentSize(); i++) {
      if (planX == snake.getSnakeX()[i] && planY == snake.getSnakeY()[i]) {
        return false;
      }
    }
    return true;
  }
}
