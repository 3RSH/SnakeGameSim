package ru.derendiaev;

import java.util.ArrayList;
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

  /**
   * Main(input-point) method.
   */
  public static void main(String[] args) {
    Snake snake = new Snake(3, 20, 20);
    SnakeController snakeController = new SnakeController(snake);

    List<Frog> frogs = new ArrayList<>();

    for (int i = 0; i < 3; i++) {
      frogs.add(new Frog(20, 20));
    }

    List<FrogController> frogControllers =
        frogs.stream().map(FrogController::new).collect(Collectors.toList());

    Window window = new Window(snakeController, frogControllers);

    SwingUtilities.invokeLater(window::init);
  }
}