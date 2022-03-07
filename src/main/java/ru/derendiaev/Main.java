package ru.derendiaev;

import java.util.Arrays;
import javax.swing.SwingUtilities;
import ru.derendiaev.controller.FrogController;
import ru.derendiaev.controller.SnakeController;
import ru.derendiaev.model.ModelManager;
import ru.derendiaev.view.Window;

/**
 * Created by DDerendiaev on 12-Jan-22.
 */
public class Main {

  /**
   * Main(input-point) method.
   */
  public static void main(String[] args) {
    if (args.length == 4 && isNumArgs(args)) {
      Config.setCustomParam(args);
    }

    SnakeController snakeController = new SnakeController();
    FrogController frogController = new FrogController();
    ModelManager model = new ModelManager(snakeController, frogController);

    snakeController.setModelManager(model);

    Window window = new Window();
    window.setSnakeController(snakeController);
    window.setFrogController(frogController);

    SwingUtilities.invokeLater(window::init);
  }

  private static boolean isNumArgs(String[] args) {
    return Arrays.stream(args).allMatch(arg -> arg.matches("[0-9]{1,3}"));
  }
}