package cs3500.model.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface IReadableModel {

  /**
   * Returns the last frame of the animation's index.
   *
   * @return the total number of frames in the animation
   */
  double maxTime();

  /**
   * Gets the frame at the specified index.
   *
   * @param i the target index
   * @return all of the readable shapes appearing at the given time in an array
   */
  List<IReadableShape> getFrame(int i);

  /**
   * Gets a list of the animations that occur during the entire playthrough.
   *
   * @return a list of the animations
   */
  List<IAnimation> getAnimations();

  /**
   * Returns the frames per second of the animation.
   *
   * @return the fps as an integer
   */
  double getFPS();

  /**
   * Gets the animatable shape with the given name.
   *
   * @param name the name of the shape contained within the animatable shape
   * @return an animatable shape with the given name
   */
  IAnimatableShape getIAnimatableShape(String name);

  /**
   * Returns a stored list of list of all of the shapes that were created when animations were
   * added to the model, sorted by time.
   *
   * @return the shapes in the model.
   */
  List<List<IReadableShape>> getFrameArray();

  /**
   * Returns a linked hash map of all of the shapes in the animation organized by name.
   *
   * @return all of the shapes in the model sorted by name and time
   */
  LinkedHashMap<String, IWritableShape> getShapes();

  /**
   * Returns a hash map of the animatable shapes in the model.
   *
   * @return all of the animatable shapes accessible by their shape's id
   */
  HashMap<String, IAnimatableShape> getIAnimatableShapes();
}
