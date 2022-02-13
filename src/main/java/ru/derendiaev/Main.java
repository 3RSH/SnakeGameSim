package ru.derendiaev;

import java.util.Arrays;
import javax.swing.SwingUtilities;
import ru.derendiaev.view.Window;

/**
 * Created by DDerendiaev on 12-Jan-22.
 */
public class Main {

  /**
   * Main(input-point) method.
   */
  public static void main(String[] args) {
    if (args.length == 4 && isNumArgs(args)) {
      Config.setCustomParam(args);
    }

    Window window = new Window();
    SwingUtilities.invokeLater(window::init);
  }

  private static boolean isNumArgs(String[] args) {
    return Arrays.stream(args).allMatch(arg -> arg.matches("[0-9]{1,3}"));
  }
}