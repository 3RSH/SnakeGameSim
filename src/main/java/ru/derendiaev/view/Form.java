package ru.derendiaev.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import ru.derendiaev.controller.FrogController;
import ru.derendiaev.controller.SnakeController;

/**
 * Created by DDerendiaev on 14-Jan-22.
 */
public class Form implements PropertyChangeListener {

  private JPanel mainPanel;
  private JPanel menu;
  private JButton startStopButton;
  private GameField gameField;

  private final SnakeController snakeController;
  private final List<FrogController> frogControllers;

  /**
   * Main form constructor.
   */
  public Form(SnakeController snakeController, List<FrogController> frogControllers) {
    this.snakeController = snakeController;
    this.frogControllers = frogControllers;
    snakeController.addPropertyChangeListener(this);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if (!snakeController.isLive()) {
      startStopButton.setText("START");
    }
  }

  JPanel getMainPanel() {
    startStopButton.setText("START");
    startStopButton.setPreferredSize(new Dimension(78, 30));
    startStopButton.addActionListener(new Action() {
      @Override
      public Object getValue(String key) {
        return null;
      }

      @Override
      public void putValue(String key, Object value) {

      }

      @Override
      public void setEnabled(boolean b) {

      }

      @Override
      public boolean isEnabled() {
        return false;
      }

      @Override
      public void addPropertyChangeListener(PropertyChangeListener listener) {

      }

      @Override
      public void removePropertyChangeListener(PropertyChangeListener listener) {

      }

      @Override
      public void actionPerformed(ActionEvent e) {
        if (!gameField.isInGame()) {
          gameField.init();
          startStopButton.setText("STOP");
        } else {
          snakeController.killSnake();
          startStopButton.setText("START");
        }
      }
    });

    return mainPanel;
  }

  //for CheckStyle: IDE generated method
  private void createUIComponents() {
    gameField = new GameField(snakeController, frogControllers);
  }
}
