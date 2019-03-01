package cs3500.model.model;

public interface IWritableModel extends IReadableModel {
  /**
   * Creates a Rectangle IShape and adds it to the model.
   *
   * @param r          R of RGB value (0 - 255)
   * @param g          G of RGB value (0 - 255)
   * @param b          B of RGB value (0 - 255)
   * @param id         name of shape
   * @param cornerXPos x position of rectangle's corner
   * @param cornerYPos y position of rectangle's corner
   * @param width      width of the rectangle
   * @param height     height of the rectangle
   */
  void addRectangle(String id, int r, int g, int b, double radians, double cornerXPos, double
          cornerYPos, double width, double height);

  /**
   * Creates a Oval IShape and adds it to the model.
   *
   * @param r       R of RGB value (0 - 255)
   * @param g       G of RGB value (0 - 255)
   * @param b       B of RGB value (0 - 255)
   * @param id      name of shape
   * @param centerX x pos of shape's center
   * @param centerY y pos of shape's center
   * @param xRadius x radius of shape
   * @param yRadius y radius of shape
   */
  void addOval(String id, int r, int g, int b, double radians, double centerX, double
          centerY, double xRadius, double yRadius);

  /**
   * Creates and adds an animatable shape with appear and disappear times to the model.
   *
   * @param id        the name of the shape
   * @param timeStart the time of the shape's appearance
   * @param timeEnd   the time of the shapes' disappearance
   */
  void setTimes(String id, int timeStart, int timeEnd);

  /**
   * Creates and adds a move animation to the model.
   *
   * @param id        the name of the shape to be added
   * @param timeStart the time the animation begins
   * @param timeEnd   the time the animation ends
   * @param startX    the x pos the animation starts from
   * @param startY    the y pos the animation starts from
   * @param targetX   the x pos the animation ends at
   * @param targetY   the y pos the animation ends at
   */
  void addMoveAnimation(String id, int timeStart, int timeEnd, double startX, double startY,
                        double targetX, double targetY);

  /**
   * Creates and adds a color animation to the model.
   *
   * @param id        the name of the shape to be added
   * @param timeStart the time the animation begins
   * @param timeEnd   the time the animation ends
   * @param startR    the starting R value of the shape
   * @param startG    the starting G value of the shape
   * @param startB    the starting B value of the shape
   * @param endR      the final R value of the shape
   * @param endG      the final G value of the shape
   * @param endB      the final B value of the shape
   */
  void addColorAnimation(String id, int timeStart, int timeEnd, int startR, int startG,
                         int startB, int endR, int endG, int endB);

  /**
   * Creates and adds a scale animation to the model.
   *
   * @param id        the name of the shape to be added
   * @param timeStart the time the animation begins
   * @param timeEnd   the time the animation ends
   * @param dimStartX the initial x dimension of the shape
   * @param dimStartY the initial y dimension of the shape
   * @param dimEndX   the final x dimension of the shape
   * @param dimEndY   the final y dimension of the shape
   */
  void addScaleAnimation(String id, int timeStart, int timeEnd, double dimStartX, double
          dimStartY, double dimEndX, double dimEndY);

  /**
   * Creates and adds a move animation to the model.
   *
   * @param id        the name of the shape to be added
   * @param timeStart the time the animation begins
   * @param timeEnd   the time the animation ends
   * @param startRad  the angle of rotation the animation starts from
   * @param targetRad  the angle of rotation the animation ends at
   */
  void addRotationAnimation(String id, int timeStart, int timeEnd, double startRad, double
          targetRad);

  /**
   * Creates an array of all of the shapes in the animation.
   *
   * @param fps the number of frames per tick
   */
  void generateFrameArray(int fps);

  /**
   * Sets the model's ticks per second.
   *
   * @param fps the rate at which frames are shown per second
   */
  void setFPS(int fps);
}
