package view;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Created by DDerendiaev on 12-Jan-22.
 */
public class Window extends JFrame {

  private final Form form;

  /**
   * Main window constructor.
   */
  public Window() {
    form = new Form();

    setTitle("Змейка");
    setSize(
        form.getMainPanel().getMinimumSize().width,
        form.getMainPanel().getMinimumSize().height + 25);
    setResizable(false);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    add(form.getMainPanel());
    setVisible(true);
  }
}
