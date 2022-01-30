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
    int cellSize = 16;
    int fieldCellsX = 30;
    int fieldCellsY = 30;
    int snakeSize = 3;

    Snake snake = new Snake(cellSize, fieldCellsX, fieldCellsY, snakeSize);
    SnakeController snakeController = new SnakeController(snake);

    List<Frog> frogs = new ArrayList<>();

    for (int i = 0; i < 6; i++) {
      frogs.add(new Frog(cellSize, fieldCellsX, fieldCellsY));
    }

    List<FrogController> frogControllers =
        frogs.stream().map(FrogController::new).collect(Collectors.toList());

    Window window = new Window(snakeController, frogControllers);

    SwingUtilities.invokeLater(window::init);
  }
}