package ru.derendiaev.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Created by DDerendiaev on 14-Jan-22.
 */
public class Form implements PropertyChangeListener {

  private static final String START_BUTTON_TEXT = "START";
  private static final String STOP_BUTTON_TEXT = "STOP";

  private JPanel mainPanel;
  private JButton startStopButton;
  private GameField gameField;

  JPanel getMainPanel() {
    startStopButton.setText(START_BUTTON_TEXT);
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
        if (gameField.isInGame() && startStopButton.getText().equals(START_BUTTON_TEXT)) {
          startStopButton.setText(STOP_BUTTON_TEXT);
          gameField.startThreads();
        } else if (!gameField.isInGame()) {
          gameField.init();
          startStopButton.setText(STOP_BUTTON_TEXT);
          gameField.startThreads();
        } else {
          gameField.stopGame();
          startStopButton.setText(START_BUTTON_TEXT);
        }
      }
    });

    return mainPanel;
  }

  //for CheckStyle: IDE generated methods
  private void createUIComponents() {
    gameField = new GameField(this);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String eventName = evt.getPropertyName();

    if (eventName.equals("stopGame")) {
      startStopButton.setText(START_BUTTON_TEXT);
    }
  }
}
