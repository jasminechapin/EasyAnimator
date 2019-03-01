package cs3500.model.model;

public interface IWritableShape extends IReadableShape {

  /**
   * Mutates this shape so that its position reflects the given parameters.
   *
   * @param x the new x-coordinate of the position
   * @param y the new y-coordinate of the position
   */
  void mutatePos(double x, double y);

  /**
   * Mutates this shape so that its dimensions reflect the given parameters.
   *
   * @param dimX the new horizontal dimension of the scale change
   * @param dimY the new vertical dimension of the scale change
   */
  void mutateDim(double dimX, double dimY);

  /**
   * Mutates this shape so that its position reflects the given parameters.
   *
   * @param newR the new red component of the color
   * @param newG the new green component of the color
   * @param newB the new blue component of the color
   */
  void mutateColors(double newR, double newG, double newB);

  void mutateRad(double newRad);

  /**
   * Constructs a deep copy of this writable shape.
   *
   * @return a copy of the shape that can be mutated
   */
  IWritableShape deepCopyW();
}
