package cs3500.view;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import cs3500.model.model.IAnimatableShape;
import cs3500.model.model.IAnimation;
import cs3500.model.model.IReadableShape;

public class TextView extends AbstractView {

  /**
   * Constructs a view that displays a description of an animation.
   *
   * @param ap               the appendable
   * @param frames           all of the frames in the animation
   * @param animations       all of the animations on each shape
   * @param fps              the speed of the animation
   * @param animatableShapes all of the animatable shapes in the animation
   */
  protected TextView(Appendable ap, List<List<IReadableShape>> frames,
                     List<IAnimation> animations, double fps, HashMap<String,
          IAnimatableShape> animatableShapes) {
    super(ap, frames, animations, fps, animatableShapes);
  }

  @Override
  public void drawingShapes(int frame) {
    throw new UnsupportedOperationException("This view cannot draw the animation");
  }

  @Override
  public void drawingText() {
    String description = setTextDescription();
    try {
      ap.append(description);
      ap.append("\n");
      if (ap instanceof BufferedWriter) {
        ((BufferedWriter) ap).close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getViewType() {
    return "text";
  }

  /**
   * Writes the description of the animation.
   *
   * @return the description of every shape and transformation in the animation.
   */
  private String setTextDescription() {
    String finalDescription = "";

    for (int i = 0; i < frames.size(); i++) {
      for (IReadableShape shape : frames.get(i)) {
        if (!finalDescription.contains(shape.getName())) {
          finalDescription += shape.getDescription();
          finalDescription += writeTimes(shape);
        }
      }
    }
    for (IAnimation animation : animations) {
      finalDescription += describeTextAnimation(animation);
    }
    return finalDescription;
  }

  /**
   * Writes the disappearance and appearance times of a shape.
   *
   * @param shape the shape being described
   * @return the description of the shape's times
   */
  private String writeTimes(IReadableShape shape) {
    String timesDescription = "";
    double appearanceTime = frames.size();
    double disappearanceTime = 0;

    for (IAnimation animation : animations) {
      if (animation.shapeName().equals(shape.getName())) {
        if (animation.getAnimatableShape().appearanceTime() < appearanceTime) {
          appearanceTime = animation.getAnimatableShape().appearanceTime();
        }
        if (animation.getAnimatableShape().disappearanceTime() >= disappearanceTime) {
          disappearanceTime = animation.getAnimatableShape().disappearanceTime();
        }
      } else {
        IAnimatableShape ishape = animatableShapes.get(shape.getName());
        appearanceTime = ishape.appearanceTime();
        disappearanceTime = ishape.disappearanceTime();
      }

    }

    appearanceTime = appearanceTime / fps;
    disappearanceTime = disappearanceTime / fps;

    timesDescription += "Appears at t=" + appearanceTime + "s" + "\n" + "Disappears "
            + "at " + "t=" + disappearanceTime + "s" + "\n" + "\n";
    return timesDescription;
  }

  /**
   * Writes a description of a animation that occurs during the animation.
   *
   * @param animation a transformation on a shape
   * @return the String representing the animation
   */
  private String describeTextAnimation(IAnimation animation) {
    return animation.describeAnimation() + "from t=" + (animation.timeStart() / fps)
            + "s" + " to " + "t=" + (animation.timeEnd() / fps) + "s" + "\n";
  }

  @Override
  public void setIsRepeating(boolean isRepeating) {
    throw new UnsupportedOperationException("Cannot loop this type of animation");
  }
}
