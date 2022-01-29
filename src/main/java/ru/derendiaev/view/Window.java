package ru.derendiaev.view;

import java.awt.Dimension;
import java.awt.Toolkit;
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
    setTitle("Змейка");
    setResizable(false);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    Form form = new Form(snakeController, frogControllers);
    add(form.getMainPanel());
    pack();
    setCenterPosition();
    setVisible(true);
  }

  private void setCenterPosition() {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = this.getPreferredSize();

    this.setLocation((screenSize.width - frameSize.width) / 2,
        (screenSize.height - frameSize.height) / 2);
  }
}
