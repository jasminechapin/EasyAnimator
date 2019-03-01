package cs3500.model.model;

public interface IReadableShape {
  /**
   * Returns the red color of the shape.
   *
   * @return a java.awt representation of a color
   */
  double getR();

  /**
   * Returns the green color of the shape.
   *
   * @return a java.awt representation of a color
   */
  double getG();

  /**
   * Returns the blue color of the shape.
   *
   * @return a java.awt representation of a color
   */
  double getB();

  /**
   * Returns the id/name of the shape.
   *
   * @return a string of the shape's name
   */
  String getName();

  /**
   * Returns the x-coordinate of the position of the shape.
   *
   * @return a double of the anchor point's x value
   */
  double getX();

  /**
   * Returns the y-coordinate of the position of the shape.
   *
   * @return a double of the anchor point's y value
   */
  double getY();

  /**
   * Returns the width of the shape.
   *
   * @return the width of the shape
   */
  double getWidth();

  /**
   * Returns the height of the shape.
   *
   * @return the height of the shape
   */
  double getHeight();

  /**
   * Returns a description of the shape's properties.
   *
   * @return a string of the shape's description
   */
  String getDescription();

  /**
   * Returns the type of the shape.
   *
   * @return Enum describing the shape's type
   */
  Enum getType();

  /**
   * Makes a deep copy of this shape.
   *
   * @return a new IReadable shape with the same properties as this one
   */
  IReadableShape deepCopyR();

  double getRadians();
}
