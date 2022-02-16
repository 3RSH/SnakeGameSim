package ru.derendiaev;

import java.util.Arrays;
import javax.swing.SwingUtilities;
import lombok.Getter;
import ru.derendiaev.controller.FrogController;
import ru.derendiaev.controller.SnakeController;
import ru.derendiaev.model.Field;
import ru.derendiaev.view.Window;

/**
 * Created by DDerendiaev on 12-Jan-22.
 */
public class Main {

  @Getter
  private static Field fieldModel;
  private static SnakeController snakeController;
  private static FrogController frogController;

  /**
   * Main(input-point) method.
   */
  public static void main(String[] args) {
    if (args.length == 4 && isNumArgs(args)) {
      Config.setCustomParam(args);
    }

    fieldModel = new Field(Config.getFieldSizeX(), Config.getFieldSizeY());
    snakeController = new SnakeController();
    frogController = new FrogController();
    Window window = new Window();
    SwingUtilities.invokeLater(window::init);
  }

  private static boolean isNumArgs(String[] args) {
    return Arrays.stream(args).allMatch(arg -> arg.matches("[0-9]{1,3}"));
  }
}