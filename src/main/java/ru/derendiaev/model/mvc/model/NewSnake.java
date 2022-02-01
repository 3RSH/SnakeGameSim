package ru.derendiaev.model.mvc.model;

import java.beans.PropertyChangeSupport;
import java.util.List;
import lombok.Getter;


/**
 * Created by DDerendiaev on 01-Feb-22.
 */
public class NewSnake implements Movable {

  private static final String NULL_DIRECTION_ERROR = "Direction is NULL";

  private final int startSize;
  private final int startSpeed;
  private final Field field;
  private final List<NewFrog> frogs;

  @Getter
  private int[] snakeX;

  @Getter
  private int[] snakeY;

  @Getter
  private int currentSize;
  private int currentSpeed;
  private boolean isLive;

  @Getter
  private int points;
  private Direction direction;
  private int stepCounter;

  PropertyChangeSupport change;

  /**
   * Snake constructor.
   */
  public NewSnake(Field field, List<NewFrog> frogs, int startSize, int startSpeed) {
    this.field = field;
    this.frogs = frogs;
    this.startSize = startSize;
    this.startSpeed = startSpeed;
    change = new PropertyChangeSupport(this);
  }

  @Override
  public void move() {
    checkCollisions();

    if (isLive) {

      for (int i = currentSize; i > 0; i--) {
        snakeX[i] = snakeX[i - 1];
        snakeY[i] = snakeY[i - 1];
      }

      if (direction == Direction.LEFT) {
        snakeX[0] -= 1;
      } else if (direction == Direction.RIGHT) {
        snakeX[0] += 1;
      } else if (direction == Direction.UP) {
        snakeY[0] -= 1;
      } else if (direction == Direction.DOWN) {
        snakeY[0] += 1;
      }

      stepCounter++;
      eat();
    }
  }

  @Override
  public int getSpeed() {
    return currentSpeed;
  }

  @Override
  public boolean isLive() {
    return isLive;
  }

  /**
   * Snake initialization.
   */
  public void init() {
    int fieldArea = field.getFieldSizeX() + field.getFieldSizeY();
    snakeX = new int[fieldArea];
    snakeY = new int[fieldArea];
    currentSize = startSize;
    currentSpeed = startSpeed;
    direction = Direction.RIGHT;
    points = 0;
    isLive = true;

    for (int i = 0; i < currentSize; i++) {
      snakeX[i] = currentSize - (i + 1);
      snakeY[i] = 0;
    }
  }

  /**
   * Kill snake.
   */
  public void kill() {
    isLive = false;
  }

  /**
   * Snake turn right.
   */
  public void turnRight() {
    if (stepCounter > 0) {
      switch (direction) {
        case RIGHT:
          direction = Direction.DOWN;
          break;
        case DOWN:
          direction = Direction.LEFT;
          break;
        case LEFT:
          direction = Direction.UP;
          break;
        case UP:
          direction = Direction.RIGHT;
          break;
        default:
          throw new NullPointerException(NULL_DIRECTION_ERROR);
      }

      stepCounter = 0;
    }
  }

  /**
   * Snake turn right.
   */
  public void turnLeft() {
    if (stepCounter > 0) {
      switch (direction) {
        case RIGHT:
          direction = Direction.UP;
          break;
        case UP:
          direction = Direction.LEFT;
          break;
        case LEFT:
          direction = Direction.DOWN;
          break;
        case DOWN:
          direction = Direction.RIGHT;
          break;
        default:
          throw new NullPointerException(NULL_DIRECTION_ERROR);
      }

      stepCounter = 0;
    }
  }

  private void checkCollisions() {
    for (int i = currentSize; i > 0; i--) {
      if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
        kill();
        break;
      }
    }

    if (snakeX[0] == field.getFieldSizeX() - 1 && direction == Direction.RIGHT) {
      kill();
    } else if (snakeX[0] == 0 && direction == Direction.LEFT) {
      kill();
    } else if (snakeY[0] == field.getFieldSizeY() - 1 && direction == Direction.DOWN) {
      kill();
    } else if (snakeY[0] == 0 && direction == Direction.UP) {
      kill();
    }
  }

  private void eat() {
    for (NewFrog frog : frogs) {
      if (canEat(frog)) {
        frog.respawn();
        grow();
      }
    }
  }

  private boolean canEat(NewFrog frog) {
    return snakeX[0] == frog.getFrogX() && snakeY[0] == frog.getFrogY();
  }

  private void grow() {
    if (isLive) {
      currentSize++;
      points++;

      if (points % 10 == 0) {
        currentSpeed++;
      }
    }
  }

  /**
   * Direction emun.
   */
  private enum Direction {
    RIGHT, DOWN, LEFT, UP
  }
}
