package ru.derendiaev;

import java.awt.Toolkit;
import lombok.Getter;

/**
 * Created by DDerendiaev on 12-Feb-22.
 */
public class Config {

  public static final int CELL_SIZE = 16;

  @Getter
  private static int fieldSizeX = 30;

  @Getter
  private static int fieldSizeY = 30;

  @Getter
  private static int snakeStartSize = 3;

  @Getter
  private static int snakeStartSpeed = 2;

  protected static void setCustomParam(String[] args) {
    if (isCorrectX(Integer.parseInt(args[0]))) {
      fieldSizeX = Integer.parseInt(args[0]);
    }
    if (isCorrectY(Integer.parseInt(args[1]))) {
      fieldSizeY = Integer.parseInt(args[1]);
    }
    if (isCorrectSnakeSize(Integer.parseInt(args[2]))) {
      snakeStartSize = Integer.parseInt(args[2]);
    }
    if (isCorrectStartSpeed(Integer.parseInt(args[3]))) {
      snakeStartSpeed = Integer.parseInt(args[3]);
    }
  }

  private static boolean isCorrectX(int fieldX) {
    return fieldX > 14
        && fieldX * CELL_SIZE < Toolkit.getDefaultToolkit().getScreenSize().width - 10;
  }

  private static boolean isCorrectY(int fieldY) {
    return fieldY > 14
        && fieldY * CELL_SIZE < Toolkit.getDefaultToolkit().getScreenSize().height - 120;
  }

  private static boolean isCorrectSnakeSize(int snakeSize) {
    return snakeSize > 1 && snakeSize < fieldSizeX / 2;
  }

  private static boolean isCorrectStartSpeed(int startSpeed) {
    return startSpeed > 0 && startSpeed < 11;
  }
}
