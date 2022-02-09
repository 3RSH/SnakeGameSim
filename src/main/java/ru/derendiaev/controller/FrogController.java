package ru.derendiaev.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import ru.derendiaev.model.object.Cell;
import ru.derendiaev.model.thread.FrogThread;
import ru.derendiaev.view.GameField;

/**
 * Created by DDerendiaev on 10-Feb-22.
 */
public class FrogController implements PropertyChangeListener {

  GameField gameField;
  FrogThread frogThread;

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    String eventName = evt.getPropertyName();

    //from model to view event
    if (eventName.equals("changeCell")) {
      List<Cell> cells = new ArrayList<>();

      cells.add((Cell) evt.getNewValue());
      cells.add((Cell) evt.getOldValue());

      gameField.repaintCells(cells);
      
      //from model to view event
    } else if (eventName.equals("dieThread")) {
      gameField.respawnFrog;

      //from view to model event
    } else if (eventName.equals("stopGame") && frogThread.isLive()) {
      frogThread.setLive(false);
    }
  }
}
