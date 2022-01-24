package model;

/**
 * Created by DDerendiaev on 17-Jan-22.
 */
public class Snake {

  //Global param.
  private static final int CELL_SIZE = 16;

  //Snake params.
  private final int[] snakeX;
  private final int[] snakeY;
  private boolean isLive = true;
  private int size;
  private Direction direction;
  private int stepCounter;

  //GameField params.
  private final int fieldCellsX;
  private final int fieldCellsY;

  /**
   * Snake constructor.
   */
  public Snake(Direction direction, int size, int fieldCellsX, int fieldCellsY) {
    this.direction = direction;
    this.size = size;
    this.fieldCellsX = fieldCellsX;
    this.fieldCellsY = fieldCellsY;

    int fieldCellsArea = fieldCellsX * fieldCellsY;
    snakeX = new int[fieldCellsArea];
    snakeY = new int[fieldCellsArea];

    init();
  }

  /**
   * Move snake.
   */
  public void move() {
    for (int i = size; i > 0; i--) {
      snakeX[i] = snakeX[i - 1];
      snakeY[i] = snakeY[i - 1];
    }

    if (direction == Direction.LEFT) {
      snakeX[0] -= CELL_SIZE;
    } else if (direction == Direction.RIGHT) {
      snakeX[0] += CELL_SIZE;
    } else if (direction == Direction.UP) {
      snakeY[0] -= CELL_SIZE;
    } else if (direction == Direction.DOWN) {
      snakeY[0] += CELL_SIZE;
    }

    stepCounter++;
  }

  /**
   * Grow snake when is eating.
   */
  public void growSnake() {
    size++;
  }

  /**
   * Check snake collisions.
   */
  public void checkCollisions() {
    for (int i = size; i > 0; i--) {
      if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
        isLive = false;
        break;
      }
    }

    if (snakeX[0] > (fieldCellsX * CELL_SIZE) - CELL_SIZE) {
      isLive = false;
    } else if (snakeX[0] < 0) {
      isLive = false;
    } else if (snakeY[0] > (fieldCellsY * CELL_SIZE) - CELL_SIZE) {
      isLive = false;
    } else if (snakeY[0] < 0) {
      isLive = false;
    }
  }

  /**
   * Set snake direction.
   */
  public void setDirection(Direction direction) {
    if (canChangeDirection(direction)) {
      this.direction = direction;
      stepCounter = 0;
    }
  }

  public boolean isLive() {
    return isLive;
  }


  public int getSize() {
    return size;
  }

  public int[] getSnakeX() {
    return snakeX;
  }

  public int[] getSnakeY() {
    return snakeY;
  }

  private void init() {
    for (int i = 0; i < size; i++) {
      snakeX[i] = 32 - i * CELL_SIZE;
      snakeY[i] = 0;
    }
  }

  private boolean canChangeDirection(Direction direction) {
    boolean upCondition =
        direction == Direction.UP
            && this.direction != Direction.DOWN
            && stepCounter > 0;
    boolean downCondition =
        direction == Direction.DOWN
            && this.direction != Direction.UP
            && stepCounter > 0;
    boolean rightCondition =
        direction == Direction.RIGHT
            && this.direction != Direction.LEFT
            && stepCounter > 0;
    boolean leftCondition =
        direction == Direction.LEFT
            && this.direction != Direction.RIGHT
            && stepCounter > 0;

    return upCondition || downCondition || rightCondition || leftCondition;
  }

  /**
   * Direction emun.
   */
  public enum Direction {
    RIGHT, DOWN, LEFT, UP
  }
}