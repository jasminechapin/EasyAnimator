package cs3500.model.model;

import java.util.List;

public class Oval extends AbstractShape {
  private double centerX;
  private double centerY;
  private double xRadius;
  private double yRadius;

  /**
   * Creates a representation of an Oval shape by using its color, name, position, and dimensions.
   *
   * @param id      name of shape
   * @param r       R val of color
   * @param g       G val of color
   * @param b       B val of color
   * @param centerX x pos of Oval center
   * @param centerY y pos of Oval center
   * @param xRadius x radius of shape
   * @param yRadius y radius of shape
   */
  public Oval(String id, double r, double g, double b, double centerX, double
          centerY, double xRadius, double yRadius, double radians) {
    super(id, r, g, b, radians);
    this.centerX = centerX;
    this.centerY = centerY;
    this.xRadius = xRadius;
    this.yRadius = yRadius;
  }

  @Override
  public String getDescription() {
    String output = "";
    output += "Name: " + name + "\nType: " + this.getType() + "\nCenter: (" + centerX + ","
            + centerY + ")," + " X radius: " + xRadius + ", Y radius: " + yRadius + ", Color: ("
            + r + "," + g + "," + b + ")\n";
    return output;
  }

  @Override
  public Enum getType() {
    return Type.ellipse;
  }

  @Override
  public double getX() {
    return centerX;
  }

  @Override
  public double getY() {
    return centerY;
  }

  @Override
  public double getWidth() {
    return xRadius;
  }

  @Override
  public double getHeight() {
    return yRadius;
  }

  @Override
  public void mutatePos(double x, double y) {
    centerX = x;
    centerY = y;
  }

  @Override
  public void mutateDim(double x, double y) {
    xRadius = x;
    yRadius = y;
  }

  @Override
  public void mutateColors(double newR, double newG, double newB) {
    r = newR;
    g = newG;
    b = newB;
  }

  @Override
  public void mutateRad(double newRad) {
    radians = newRad;
  }


  @Override
  public IReadableShape deepCopyR() {
    return new Oval(name, r, g, b, centerX, centerY, xRadius, yRadius, radians);
  }

  @Override
  public IWritableShape deepCopyW() {
    return new Oval(name, r, g, b, centerX, centerY, xRadius, yRadius, radians);
  }
}