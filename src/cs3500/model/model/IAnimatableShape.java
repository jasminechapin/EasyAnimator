package cs3500.model.model;

/**
 * Represents the behaviors of all IAnimatableShapes.
 */
public interface IAnimatableShape {
  /**
   * Returns a description of the shapes fields + the appearance and disappearance times.
   *
   * @return Formatted string representing the IAnimatableShape's fields
   */
  String description();

  /**
   * Outputs the name of the animatable shape.
   *
   * @return name of the shape as a string
   */
  String getName();

  /**
   * Outputs the type of the animatable shape.
   *
   * @return type of the shape as an enum
   */
  Enum getType();

  /**
   * Gets the time when the shape first appears.
   *
   * @return the appearance time of the shape
   */
  double appearanceTime();

  /**
   * Gets the time when the shape disappears.
   *
   * @return the disappearance time of the shape
   */
  double disappearanceTime();

  /**
   * Returns a version of the contained shape that can be mutated.
   *
   * @return the writable version of the shape
   */
  IWritableShape getIWritableShape();

  /**
   * Returns a read-only version of the contained shape that cannot be mutated.
   *
   * @return the readable version of the shape
   */
  IReadableShape getReadableShape();

  /**
   * Returns a new IAnimatableShape with the same shape and times as this one.
   *
   * @return a deep of this animatable shape
   */
  IAnimatableShape deepCopy();
}
