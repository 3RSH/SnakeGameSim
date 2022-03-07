package ru.derendiaev.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import lombok.Setter;
import ru.derendiaev.controller.FrogController;
import ru.derendiaev.controller.SnakeController;

/**
 * Created by DDerendiaev on 12-Jan-22.
 */
@Setter
public class Window extends JFrame {

  SnakeController snakeController;
  FrogController frogController;


  /**
   * Window initialization method.
   */
  public void init() {
    setTitle("Snake");
    setResizable(false);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    Form form = new Form();

    GameField gameField = form.getGameField();
    snakeController.setGameField(gameField);
    frogController.setGameField(gameField);

    gameField.addListener(snakeController);

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
