package cs3500.model.model;

import java.util.ArrayList;
import java.util.List;

public class ColorAnimation extends AbstractAnimation {
  private double startR;
  private double startG;
  private double startB;
  private double endR;
  private double endG;
  private double endB;

  /**
   * Creates a color animation using the given shape and the initial and final color states.
   *
   * @param shape     the shape to be color animated.
   * @param startTime the time the animation begins
   * @param endTime   the time the animation ends
   * @param startR    the starting R val
   * @param startG    the starting G val
   * @param startB    the starting B val
   * @param endR      the final R val
   * @param endG      the final G val
   * @param endB      the final B val
   */
  public ColorAnimation(IAnimatableShape shape, double startTime, double endTime, double startR,
                        double startG, double startB, double endR, double endG, double endB) {
    super(shape, startTime, endTime);
    this.startR = startR;
    this.startG = startG;
    this.startB = startB;
    this.endR = endR;
    this.endG = endG;
    this.endB = endB;
  }

  @Override
  public IAnimation deepCopy() {
    return new ColorAnimation(shape.deepCopy(), startTime, endTime, startR, startG, startB, endR,
            endG, endB);
  }

  @Override
  public String describeAnimation() {
    String output = "Shape " + shape.getName() + " changes color from (" + startR + "," + startG
            + "," + startB + ") to (" + endR + "," + endG + "," + endB + ") ";
    return output;
  }

  @Override
  public boolean isValidColorA(int otherStartT, int otherEndT) {
    return ((otherStartT < startTime) && (otherEndT <= startTime)) || ((otherStartT >= endTime)
            && (otherEndT > endTime));
  }

  @Override
  public void tween(int frameCount, int fps) {
    double appearFrame = fps * startTime;
    double disappearFrame = fps * endTime;
    double frameDiff = disappearFrame - appearFrame;
    double step = frameCount - appearFrame;
    double newR = startR + ((endR - startR) / frameDiff) * step;
    double newG = startG + ((endG - startG) / frameDiff) * step;
    double newB = startB + ((endB - startB) / frameDiff) * step;
    shape.getIWritableShape().mutateColors(newR, newG, newB);
  }

  @Override
  public Type getType() {
    return Type.color;
  }

  @Override
  public List<String> getEndProperties() {
    List<String> properties = new ArrayList<>();
    properties.add(Double.toString(this.endR));
    properties.add(Double.toString(this.endG));
    properties.add(Double.toString(this.endB));
    return properties;
  }
}
