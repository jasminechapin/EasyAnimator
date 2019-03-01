package cs3500.view;

public interface IView {
  /**
   * Draws the text om the visual drawing panel.
   *
   */
  void drawingShapes(int frame);

  /**
   * Draws the text on the text drawing panel.
   */
  void drawingText();

  String getViewType();

  void setFPS(double fps);

  void setIsRepeating(boolean isRepeating);
}
