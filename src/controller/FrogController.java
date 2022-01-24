package controller;

import model.Frog;
import model.FrogThread;
import view.GameField;

/**
 * Created by DDerendiaev on 21-Jan-22.
 */
public class FrogController {

  private final GameField gameField;
  private Frog frog;

  /**
   * Frog controller constructor.
   */
  public FrogController(GameField gameField) {
    this.gameField = gameField;
  }

  /**
   * Frog controller initialization.
   */
  public void init() {
    frog = new Frog(20, 20);
    FrogThread frogThread = new FrogThread(frog, this);
    new Thread(frogThread).start();
  }

  public void repaint() {
    gameField.repaint();
  }

  public int getX() {
    return frog.getFrogX();
  }

  public int getY() {
    return frog.getFrogY();
  }

  public void kill() {
    frog.setLive(false);
  }
}
