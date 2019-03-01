package cs3500.model.model;

public abstract class AbstractShape implements IWritableShape {
  protected String name;
  protected double r;
  protected double g;
  protected double b;
  protected double radians;

  public enum Type { ellipse, rect }

  /**
   * Initializes the fields that all AShapes share to the default values.
   *
   * @param name The name of the shape.
   * @param r    The r value of the color.
   * @param g    The g value of the color.
   * @param b    The b value of the color.
   */
  protected AbstractShape(String name, double r, double g, double b, double radians) {
    this.name = name;
    this.r = r;
    this.g = g;
    this.b = b;
    this.radians = radians;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public double getR() {
    return this.r;
  }

  @Override
  public double getG() {
    return this.g;
  }

  @Override
  public double getB() {
    return this.b;
  }

  @Override
  public abstract Enum getType();

  @Override
  public double getRadians() {
    return this.radians;
  }
}
