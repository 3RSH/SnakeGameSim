package ru.derendiaev.view;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import ru.derendiaev.controller.FrogController;
import ru.derendiaev.controller.SnakeController;

/**
 * Created by DDerendiaev on 12-Jan-22.
 */
public class Window extends JFrame {

  private Form form;

  SnakeController snakeController;
  FrogController frogController;

  /**
   * ru.derendiaev.Main window constructor.
   */
  public Window(SnakeController snakeController, FrogController frogController) {
    this.snakeController = snakeController;
    this.frogController = frogController;
  }

  /**
   * Window initialization method.
   */
  public void init() {
    form = new Form(snakeController, frogController);

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
