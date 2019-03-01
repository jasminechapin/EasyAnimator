package cs3500.view;

import java.util.HashMap;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import cs3500.model.model.IAnimatableShape;
import cs3500.model.model.IAnimation;
import cs3500.model.model.IReadableShape;
import cs3500.view.panels.AbstractDrawingPanel;
import cs3500.view.panels.VisualDrawingPanel;

public class VisualView extends AbstractView implements IView {
  /**
   * Constructs a view that displays a visual representation of the animation.
   *
   * @param ap               the appendable
   * @param frames           all of the frames in the animation
   * @param animations       all of the animations on each shape
   * @param fps              the speed of the animation
   * @param animatableShapes all of the animatable shapes in the animation
   */
  protected VisualView(Appendable ap, List<List<IReadableShape>> frames,
                       List<IAnimation> animations, double fps, HashMap<String,
          IAnimatableShape> animatableShapes) {
    super(ap, frames, animations, fps, animatableShapes);
    this.drawingPanel = new VisualDrawingPanel();
    jScrollPane = new JScrollPane((AbstractDrawingPanel) drawingPanel);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.add(jScrollPane);
    setVisible(true);
  }

  @Override
  public void drawingShapes(int frame) {
    this.shapesToDraw = frames.get(frame);
    drawingPanel.drawShapes(this.shapesToDraw);
  }

  @Override
  public void drawingText() {
    throw new UnsupportedOperationException("This view cannot represent the animation as text.");
  }

  @Override
  public String getViewType() {
    return "visual";
  }

  @Override
  public void setIsRepeating(boolean isRepeating) {
    throw new UnsupportedOperationException("Cannot loop this type of animation");
  }
}
