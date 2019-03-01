package cs3500.model.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an animation where the dimensions of the shape can change over time.
 */
public class ScaleAnimation extends AbstractAnimation {
  private double dimStartX;
  private double dimStartY;
  private double dimEndX;
  private double dimEndY;

  /**
   * Creates a scale animation using the given shape, animation duration, and initial and final
   * sizes.
   * @param shape the shape to be scaled
   * @param startTime the start time of the animation
   * @param endTime the end time of the animation
   * @param dimStartX the starting x dimension
   * @param dimStartY the starting y dimension
   * @param dimEndX the final x dimension
   * @param dimEndY the final y dimension
   */
  public ScaleAnimation(IAnimatableShape shape, double startTime, double endTime, double dimStartX,
                        double dimStartY, double dimEndX, double dimEndY) {
    super(shape, startTime, endTime);
    this.dimStartX = dimStartX;
    this.dimStartY = dimStartY;
    this.dimEndX = dimEndX;
    this.dimEndY = dimEndY;
  }

  /**
   * In addition to the interface's description, this implementation formats the output with W/H
   * or xRadius/yRadius depending on the type of the shape.
   * @return A String description of the shape to be animated
   */
  @Override
  public String describeAnimation() {
    String output = "Shape " + shape.getName();
    if (shape.getType() == AbstractShape.Type.rect) {
      output += " scales from Width: " + dimStartX + ", Height: " + dimStartY + " to Width: "
              + dimEndX + ", Height: " + dimEndY + " ";
    }
    else {
      output += " scales from X radius: " + dimStartX + ", Y radius: " + dimStartY + " to X "
              + "radius: " + dimEndX + ", Y radius: " + dimEndY + " ";
    }
    return output;
  }

  @Override
  public boolean isValidScaleA(int otherStartT, int otherEndT) {
    return ((otherStartT < startTime) && (otherEndT <= startTime)) || ((otherStartT >= endTime)
            && (otherEndT > endTime));
  }

  @Override
  public String shapeName() {
    return shape.getName();
  }

  @Override
  public void tween(int frameCount, int fps) {
    double appearFrame = fps * startTime;
    double disappearFrame = fps * endTime;
    double frameDiff = disappearFrame - appearFrame;
    double step = frameCount - appearFrame;
    double newX = dimStartX + ( (dimEndX - dimStartX) / frameDiff ) * step;
    double newY = dimStartY + ( (dimEndY - dimStartY) / frameDiff ) * step;
    shape.getIWritableShape().mutateDim(newX, newY);
  }

  @Override
  public Type getType() {
    return Type.scale;
  }

  @Override
  public IAnimation deepCopy() {
    return new ScaleAnimation(shape.deepCopy(), startTime, endTime, dimStartX, dimStartY, dimEndX,
            dimEndY);
  }

  @Override
  public List<String> getEndProperties() {
    List<String> properties = new ArrayList<>();
    properties.add(Double.toString(this.dimEndX));
    properties.add(Double.toString(this.dimEndY));
    return properties;
  }
}