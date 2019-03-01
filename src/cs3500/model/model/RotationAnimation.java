package cs3500.model.model;

import java.util.ArrayList;
import java.util.List;

public class RotationAnimation extends AbstractAnimation {
  /**
   * Represents an animation where the degree of rotation of the shape can change over time.
   */
  private double startRadians;
  private double endRadians;

  /**
   * Creates a scale animation using the given shape, animation duration, and initial and final
   * sizes.
   * @param shape the shape to be scaled
   * @param startTime the start time of the animation
   * @param endTime the end time of the animation
   * @param startRadians the original angle of the shape
   * @param endRadians the target angle after the animation
   */
  public RotationAnimation(IAnimatableShape shape, double startTime, double endTime, double
          startRadians, double endRadians) {
    super(shape, startTime, endTime);
    this.startRadians = startRadians;
    this.endRadians = endRadians;
  }

  /**
   * In addition to the interface's description, this implementation formats the output with W/H
   * or xRadius/yRadius depending on the type of the shape.
   * @return A String description of the shape to be animated
   */
  @Override
  public String describeAnimation() {
    String output = "Shape " + shape.getName();
      output += " rotates from: " + startRadians + "radians to " +
              endRadians + "radians";
    return output;
  }

  @Override
  public boolean isValidRotationA(int otherStartT, int otherEndT) {
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
    double newRad = startRadians + ((endRadians - startRadians) / frameDiff ) * step;
    shape.getIWritableShape().mutateRad(newRad);
  }

  @Override
  public Type getType() {
    return Type.rotation ;
  }

  @Override
  public IAnimation deepCopy() {
    return new RotationAnimation(shape.deepCopy(), startTime, endTime, startRadians, endRadians);
  }

  @Override
  public List<String> getEndProperties() {
    List<String> properties = new ArrayList<>();
    properties.add(Double.toString(this.endRadians));
    return properties;
  }
}