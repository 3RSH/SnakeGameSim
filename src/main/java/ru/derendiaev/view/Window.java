package ru.derendiaev.view;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import ru.derendiaev.controller.FrogController;
import ru.derendiaev.controller.SnakeController;

/**
 * Created by DDerendiaev on 12-Jan-22.
 */
public class Window extends JFrame {

  SnakeController snakeController;
  List<FrogController> frogControllers;

  /**
   * ru.derendiaev.Main window constructor.
   */
  public Window(SnakeController snakeController, List<FrogController> frogControllers) {
    this.snakeController = snakeController;
    this.frogControllers = frogControllers;
  }

  /**
   * Window initialization method.
   */
  public void init() {
    Form form = new Form(snakeController, frogControllers);

    setTitle("Змейка");
    setSize(
        form.getMainPanel().getMinimumSize().width,
        form.getMainPanel().getMinimumSize().height + 25);
    setResizable(false);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    add(form.getMainPanel());
    setVisible(true);
  }
}
