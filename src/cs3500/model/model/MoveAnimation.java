package cs3500.model.model;

import java.util.ArrayList;
import java.util.List;

public class MoveAnimation extends AbstractAnimation {
  private double startX;
  private double startY;
  private double targetX;
  private double targetY;

  /**
   * Creates a move animation using the given shape, animation duration, and initial and final
   * positions.
   *  @param shape     the shape to be animated.
   * @param startTime the start time of the animation.
   * @param endTime   the end time of the animation.
   * @param startX    the starting xpos
   * @param startY    the starting ypos
   * @param targetX   the final xpos
   * @param targetY   the final ypos
   */
  public MoveAnimation(IAnimatableShape shape, double startTime, double endTime, double startX,
                       double startY, double targetX, double targetY) {
    super(shape, startTime, endTime);
    this.startX = startX;
    this.startY = startY;
    this.targetX = targetX;
    this.targetY = targetY;
  }

  @Override
  public void tween(int frameCount, int fps) {
    double appearFrame = fps * startTime;
    double disappearFrame = fps * endTime;
    double frameDiff = disappearFrame - appearFrame;
    double step = frameCount - appearFrame;
    double newX = startX + ((targetX - startX) / frameDiff) * step;
    double newY = startY + ((targetY - startY) / frameDiff) * step;
    shape.getIWritableShape().mutatePos(newX, newY);
  }

  @Override
  public String describeAnimation() {
    String output = "Shape " + shape.getName() + " moves from (" + startX + "," + startY + ") to "
            + "(" + targetX + "," + targetY + ") ";
    return output;
  }

  @Override
  public boolean isValidMoveA(int otherStartT, int otherEndT) {
    return ((otherStartT < startTime) && (otherEndT <= startTime)) || ((otherStartT >= endTime)
            && (otherEndT > endTime));
  }

  @Override
  public String shapeName() {
    return shape.getName();
  }

  @Override
  public Type getType() {
    return Type.move;
  }

  @Override
  public List<String> getEndProperties() {
    List<String> properties = new ArrayList<>();
    properties.add(Double.toString(this.targetX));
    properties.add(Double.toString(this.targetY));
    return properties;
  }

  @Override
  public IAnimation deepCopy() {
    return new MoveAnimation(shape.deepCopy(), startTime, endTime, startX, startY, targetX,
            targetY);
  }
}