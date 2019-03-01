package cs3500.view.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import cs3500.model.model.IReadableShape;
import cs3500.model.model.Oval;
import cs3500.model.model.Rectangle;

public class VisualDrawingPanel extends AbstractDrawingPanel {

  /**
   * Constructs and instance of a visual drawing panel.
   */
  public VisualDrawingPanel() {
    super();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    setBackground(Color.white);
    Graphics2D g2;

    if (shapesToDraw != null) {
      for (IReadableShape shape : shapesToDraw) {

        if (shape instanceof Rectangle) {
          g2 = (Graphics2D) g.create();
          g2.rotate(shape.getRadians(), shape.getX(), shape.getY());
          g2.setColor(new Color((int) shape.getR(), (int) shape.getG(), (int) shape.getB()));
          g2.fillRect((int) shape.getX(), (int) shape.getY(), (int) shape.getWidth(), (int) shape
                  .getHeight());

        } else if (shape instanceof Oval) {
          g2 = (Graphics2D) g.create();
          g2.rotate(shape.getRadians(), shape.getX(), shape.getY());
          g2.setColor(new Color((int) shape.getR(), (int) shape.getG(), (int) shape.getB()));
          g2.fillOval((int) (shape.getX() - (shape.getWidth() / 2)), (int) (shape.getY() - (shape
                  .getHeight() / 2)), (int) shape.getWidth(), (int) shape.getHeight());
        } else {
          throw new IllegalArgumentException("Unsupported shape");
        }
      }
    }
  }

  @Override
  public void drawShapes(List<IReadableShape> shapesToDraw) {
    this.shapesToDraw = shapesToDraw;
    this.repaint();
  }
}
