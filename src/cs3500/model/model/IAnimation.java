package cs3500.model.model;

import java.util.List;

/**
 * All IAnimations must implement a description of their affect.
 */
public interface IAnimation {

  /**
   * Outputs a string representation of the type of animation and the duration of the animation.
   *
   * @return The string description of the animation.
   */
  String describeAnimation();

  /**
   * Checks if the chosen move can occur.
   *
   * @param timeStart the beginning time of the animation
   * @param timeEnd   the end time of the animation
   * @return true if the move is possible
   */
  boolean isValidMoveA(int timeStart, int timeEnd);

  /**
   * Checks if the chosen color change can occur.
   *
   * @param timeStart the beginning time of the animation
   * @param timeEnd   the end time of the animation
   * @return true if the color change is possible
   */
  boolean isValidColorA(int timeStart, int timeEnd);

  /**
   * Checks if the chosen scale animation can occur.
   *
   * @param timeStart the beginning time of the animation
   * @param timeEnd   the end time of the animation
   * @return true if the scale animation is possible
   */
  boolean isValidScaleA(int timeStart, int timeEnd);

  boolean isValidRotationA(int timeStart, int timeEnd);

  /**
   * Returns the name of the shape being animated.
   *
   * @return a string of the shape's id
   */
  String shapeName();

  /**
   * Mutates the shape's fields to a future state according to the given frameCount and fps.
   *
   * @param frameCount the number of frames in the animation
   * @param fps        the number of frames that occur each second
   */
  void tween(int frameCount, int fps);

  /**
   * Returns the start time of this animation.
   *
   * @return an int representing this animation's start time
   */
  double timeStart();

  /**
   * Returns the end time of this animation.
   *
   * @return an int representing this animation's end time
   */
  double timeEnd();

  /**
   * Returns an enum of the type of animation that will occur at the set time.
   *
   * @return a scale, move or color change enum
   */
  AbstractAnimation.Type getType();

  /**
   * Gets the animatable shape that contains a reference to the shape being animated and the time it
   * first appears and when it disappears.
   *
   * @return the animatable shape in this animation
   */
  IAnimatableShape getAnimatableShape();

  /**
   * Gets the new properties of the shape after it undergoes this animation.
   */
  List<String> getEndProperties();

  IAnimation deepCopy();
}
