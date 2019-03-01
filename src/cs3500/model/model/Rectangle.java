package cs3500.model.model;

import java.util.List;

public class Rectangle extends AbstractShape {
  private double cornerX;
  private double cornerY;
  private double width;
  private double height;

  /**
   * Creates a representation of a Rectangle shape by using its color, name, position, and
   * dimensions.
   *
   * @param r          R val of color
   * @param g          B val of color
   * @param b          B val of color
   * @param id         name of shape
   * @param cornerXPos x pos of Rectangle center
   * @param cornerYPos y pos of Rectangle center
   * @param width      width of shape
   * @param height     height of shape
   */
  public Rectangle(String id, double r, double g, double b, double cornerXPos, double
          cornerYPos, double width, double height, double radians) {
    super(id, r, g, b, radians);
    this.cornerX = cornerXPos;
    this.cornerY = cornerYPos;
    this.width = width;
    this.height = height;
  }

  public String getColor() {
    return "(" + r + "," + g + "," + b + ")";
  }

  @Override
  public double getWidth() {
    return this.width;
  }

  @Override
  public double getHeight() {
    return this.height;
  }

  @Override
  public String getDescription() {
    String output = "";
    output += "Name: " + name + "\nType: " + this.getType() + "\nCorner: (" + cornerX + ","
            + cornerY + ")," + " Width: " + width + ", Height: " + height + ", Color: (" + r
            + "," + g + "," + b + ")\n";
    return output;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Enum getType() {
    return Type.rect;
  }


  @Override
  public double getX() {
    return cornerX;
  }

  @Override
  public double getY() {
    return cornerY;
  }

  @Override
  public void mutatePos(double x, double y) {
    cornerX = x;
    cornerY = y;
  }

  @Override
  public void mutateDim(double x, double y) {
    width = x;
    height = y;
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
    return new Rectangle(name, r, g, b, cornerX, cornerY, width, height, radians);
  }

  @Override
  public IWritableShape deepCopyW() {
    return new Rectangle(name, r, g, b, cornerX, cornerY, width, height, radians);
  }
}
