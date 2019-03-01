package cs3500.model.model;

public class AnimatableShape implements IAnimatableShape {
  private IWritableShape shape;
  private double appearTime;
  private double disappearTime;

  /**
   * Creates a shape that can be animated during the given times.
   *
   * @param shape         the shape to be animated
   * @param appearTime    the time the shape is first animated
   * @param disappearTime the time the shape is last seen
   */
  public AnimatableShape(IWritableShape shape, double appearTime, double disappearTime) {
    this.shape = shape;
    this.appearTime = appearTime;
    this.disappearTime = disappearTime;
  }

  @Override
  public IAnimatableShape deepCopy() {
    return new AnimatableShape(shape.deepCopyW(), appearTime, disappearTime);
  }

  @Override
  public String description() {
    return shape.getDescription() + "Appears at t=" + appearTime + "\nDisappears at t="
            + disappearTime;
  }

  @Override
  public String getName() {
    return shape.getName();
  }

  @Override
  public Enum getType() {
    return shape.getType();
  }

  @Override
  public double appearanceTime() {
    return appearTime;
  }

  @Override
  public double disappearanceTime() {
    return disappearTime;
  }

  @Override
  public IWritableShape getIWritableShape() {
    return shape;
  }

  @Override
  public IReadableShape getReadableShape() {
    return shape;
  }
}