package cs3500.view.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JPanel;

import cs3500.model.model.IReadableShape;

public abstract class AbstractDrawingPanel extends JPanel implements DrawingPanel {
  BorderLayout layout;
  protected List<IReadableShape> shapesToDraw;

  /**
   * Constructs an abstracted version of every drawing panel and sets the default size and layout
   * of thepanels.
   */
  AbstractDrawingPanel() {
    setSize(new Dimension(800, 600));
    setPreferredSize(new Dimension(800, 600));
    this.setLayout(layout);
  }
}
