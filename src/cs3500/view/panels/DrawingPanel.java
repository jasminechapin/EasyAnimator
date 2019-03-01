package cs3500.view.panels;

import java.util.List;

import cs3500.model.model.IReadableShape;

public interface DrawingPanel {
  /**
   * Takes in a list of shapes and sets them to the shapes to be displayed. Then draws them on
   * the panel.
   * @param shapesToDraw the shapes that will be displayed.
   */
  void drawShapes(List<IReadableShape> shapesToDraw);
}
