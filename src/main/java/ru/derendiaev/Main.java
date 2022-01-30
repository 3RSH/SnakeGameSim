package ru.derendiaev;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.SwingUtilities;
import ru.derendiaev.controller.FrogController;
import ru.derendiaev.controller.SnakeController;
import ru.derendiaev.model.Frog;
import ru.derendiaev.model.Snake;
import ru.derendiaev.view.Window;

/**
 * Created by DDerendiaev on 12-Jan-22.
 */
public class Main {

  private static final int CELL_SIZE = 16;

  private static int fieldCellsX = 30;
  private static int fieldCellsY = 30;
  private static int snakeStartSize = 3;
  private static int snakeStartSpeed = 2;

  /**
   * Main(input-point) method.
   */
  public static void main(String[] args) {
    if (args.length == 4 && isNumArgs(args)) {
      setCustomParam(args);
    }

    Snake snake = new Snake(CELL_SIZE, fieldCellsX, fieldCellsY, snakeStartSize, snakeStartSpeed);

    Window window = new Window(getSnakeController(snake), getFrogControllersWithFrogs(snake));
    SwingUtilities.invokeLater(window::init);
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
    return snakeSize > 1 && snakeSize < fieldCellsX / 2;
  }

  private static boolean isCorrectStartSpeed(int startSpeed) {
    return startSpeed > 0 && startSpeed < 11;
  }

  private static boolean isNumArgs(String[] args) {
    return Arrays.stream(args).allMatch(arg -> arg.matches("[0-9]{1,3}"));
  }

  private static void setCustomParam(String[] args) {
    if (isCorrectX(Integer.parseInt(args[0]))) {
      fieldCellsX = Integer.parseInt(args[0]);
    }
    if (isCorrectY(Integer.parseInt(args[1]))) {
      fieldCellsY = Integer.parseInt(args[1]);
    }
    if (isCorrectSnakeSize(Integer.parseInt(args[2]))) {
      snakeStartSize = Integer.parseInt(args[2]);
    }
    if (isCorrectStartSpeed(Integer.parseInt(args[3]))) {
      snakeStartSpeed = Integer.parseInt(args[3]);
    }
  }

  private static SnakeController getSnakeController(Snake snake) {
    return new SnakeController(snake);
  }

  private static List<FrogController> getFrogControllersWithFrogs(Snake snake) {
    List<Frog> frogs = new ArrayList<>();

    for (int i = 0; i < (fieldCellsX + fieldCellsY) / 10; i++) {
      frogs.add(new Frog(CELL_SIZE, fieldCellsX, fieldCellsY, snake));
    }

    return frogs.stream()
        .map(frog -> new FrogController(frog, snake))
        .collect(Collectors.toList());
  }
}