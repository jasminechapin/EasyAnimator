package cs3500.view;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import cs3500.model.model.IAnimatableShape;
import cs3500.model.model.IAnimation;
import cs3500.model.model.IReadableShape;
import cs3500.view.panels.DrawingPanel;

public abstract class AbstractView extends JFrame implements IView {
  protected DrawingPanel drawingPanel;
  protected JScrollPane jScrollPane;
  protected double fps;
  protected Appendable ap;
  protected List<List<IReadableShape>> frames;
  protected List<IAnimation> animations;
  protected List<IReadableShape> shapesToDraw;
  protected HashMap<String, IAnimatableShape> animatableShapes;

  /**
   * Initializes an abstract view that sets the size of the view and takes in all of the necessary
   * information that subviews can use.
   *
   * @param ap               the appendable location
   * @param frames           all of the frames of the animation
   * @param animations       all of the animations on each shape in the entire playthrough
   * @param fps              the speed of the animation
   * @param animatableShapes the animatable shapes in the animation
   */
  protected AbstractView(Appendable ap, List<List<IReadableShape>> frames,
                         List<IAnimation> animations, double fps, HashMap<String,
          IAnimatableShape> animatableShapes) {
    setSize(new Dimension(1600, 900));
    setPreferredSize(new Dimension(1600, 1600));
    this.frames = frames;
    this.animations = animations;
    this.fps = fps;
    this.ap = ap;
    this.animatableShapes = animatableShapes;
  }

  public static class ViewFactory {
    /**
     * Returns the appropriate view type depending on the given string.
     *
     * @param type The type of the view to be constructed
     * @param ap   The Appendable to output to if required
     * @param fps  the tick rate of the animation
     * @return TextView, SVGView, or VisualView
     */
    public static IView makeView(String type, Appendable ap, List<List<IReadableShape>> frames,
                                 List<IAnimation> animations, double fps, HashMap<String,
            IAnimatableShape> animatableShapes) {
      if (type.equalsIgnoreCase("text")) {
        return new TextView(ap, frames, animations, fps, animatableShapes);
      } else if (type.equalsIgnoreCase("svg")) {
        return new SVGView(ap, frames, animations, fps, animatableShapes);
      } else if (type.equalsIgnoreCase("visual")) {
        return new VisualView(ap, frames, animations, fps, animatableShapes);
      } else if (type.equalsIgnoreCase("interactive")) {
        return new InteractiveView(ap, frames, animations, fps, animatableShapes);
      } else {
        throw new IllegalArgumentException("Invalid shape");
      }
    }
  }

  @Override
  public void setFPS(double fps) {
    this.fps = fps;
  }
}
