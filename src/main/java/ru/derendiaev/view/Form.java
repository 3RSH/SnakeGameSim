package ru.derendiaev.view;

import java.awt.event.ActionEvent;
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
public class Form {

  private JPanel mainPanel;
  private JPanel menu;
  private JButton startButton;
  private GameField gameField;

  private final SnakeController snakeController;
  private final List<FrogController> frogControllers;

  public Form(SnakeController snakeController, List<FrogController> frogControllers) {
    this.snakeController = snakeController;
    this.frogControllers = frogControllers;
  }

  JPanel getMainPanel() {
    startButton.addActionListener(new Action() {
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
