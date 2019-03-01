package cs3500.model.model;

public abstract class AbstractAnimation implements IAnimation {
  protected IAnimatableShape shape;
  protected double startTime;
  protected double endTime;

  public enum Type { scale, move, color, rotation }


  /**
   * Constructs an instance of an abstract animation.
   *
   * @param shape     the shape the animation will be enacted upon
   * @param startTime the time in which the animation begins
   * @param endTime   the time in which the animation ends
   */
  protected AbstractAnimation(IAnimatableShape shape, double startTime, double endTime) {
    this.shape = shape;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  @Override
  public boolean isValidColorA(int timeStart, int timeEnd) {
    return true;
  }

  @Override
  public boolean isValidScaleA(int timeStart, int timeEnd) {
    return true;
  }

  @Override
  public boolean isValidMoveA(int timeStart, int timeEnd) {
    return true;
  }

  @Override
  public boolean isValidRotationA(int timeStart, int timeEnd) {
    return true;
  }

  @Override
  public double timeStart() {
    return this.startTime;
  }

  @Override
  public double timeEnd() {
    return this.endTime;
  }

  @Override
  public String shapeName() {
    return this.shape.getName();
  }

  @Override
  public IAnimatableShape getAnimatableShape() {
    return this.shape;
  }
}
