package ru.derendiaev;

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

  /**
   * Main(input-point) method.
   */
  public static void main(String[] args) {
    Snake snake = new Snake(3, 20, 20);
    SnakeController snakeController = new SnakeController(snake);

    Frog frog = new Frog(20, 20);
    FrogController frogController = new FrogController(frog);

    Window window = new Window(snakeController, frogController);

    SwingUtilities.invokeLater(window::init);
  }
}