package view;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Created by DDerendiaev on 14-Jan-22.
 */
public class Form {

  private JPanel mainPanel;
  private JPanel menu;
  private JButton startButton;
  private GameField gameField;

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
    gameField = new GameField();
  }
}
