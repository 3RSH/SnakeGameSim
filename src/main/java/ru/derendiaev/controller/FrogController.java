package ru.derendiaev.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import lombok.Setter;
import ru.derendiaev.model.object.Cell;
import ru.derendiaev.model.thread.FrogThread;
import ru.derendiaev.view.GameField;

/**
 * Created by DDerendiaev on 10-Feb-22.
 */
public class FrogController implements PropertyChangeListener {

  @Setter
  private int frogIndex;

  public GameField gameField;
  public FrogThread frogThread;

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String eventName = evt.getPropertyName();

    //from model to view event
    if (eventName.equals("changeCell")) {
      gameField.getFrogsCoords().set(frogIndex, (Cell) evt.getNewValue());

      //from model to view event
    } else if (eventName.equals("dieThread")) {
      gameField.setFrogCount(gameField.getFrogCount() - 1);
      gameField.setSnakeSize(gameField.getSnakeSize() + 1);
      gameField.incrementPoints();
      gameField.getObserver().removePropertyChangeListener(this);
      gameField.respawnFrog(frogIndex);

      //from view to model event
    } else if (eventName.equals("stopGame") && frogThread.isLive()) {
      if (frogThread.isLive()) {
        frogThread.setLive(false);
      }
    }
  }
}
