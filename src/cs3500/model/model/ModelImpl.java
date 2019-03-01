package cs3500.model.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import cs3500.TweenModelBuilder;

/**
 * Represents an implementation of the model of an animation. A ModelImpl2 is able to store,
 * display, and add shapes/animations.
 */
public class ModelImpl implements IWritableModel {
  private LinkedHashMap<String, IWritableShape> shapes;
  private LinkedHashMap<String, IAnimatableShape> animatableShapes;
  private List<IAnimation> animations;
  private LinkedHashMap<String, ArrayList<IAnimation>> animationsMap;
  private double maxTime;
  private double fps;
  private List<List<IReadableShape>> frameArray;


  /**
   * The default constructor of this IWritableModel. All maps and lists are initially empty.
   */
  public ModelImpl() {
    shapes = new LinkedHashMap<>();
    animatableShapes = new LinkedHashMap<>();
    animations = new ArrayList<>();
    frameArray = new ArrayList<>();
    animationsMap = new LinkedHashMap<>();
    maxTime = 0;
  }

  /**
   * Constructs the model with the shapes and animations given by the builder.
   */
  public ModelImpl(LinkedHashMap<String, IWritableShape> shapes, LinkedHashMap<String,
          IAnimatableShape> animatableShapes, List<IAnimation> animations,
                   List<List<IReadableShape>> frameArray, LinkedHashMap<String,
          ArrayList<IAnimation>> animationsMap, int maxTime) {
    this.shapes = shapes;
    this.animatableShapes = animatableShapes;
    this.animations = animations;
    this.frameArray = frameArray;
    this.animationsMap = animationsMap;
    this.maxTime = maxTime;
  }

  /**
   * Checks that all times are positive. Throws exception if not.
   *
   * @param times the times to be checked
   * @throws IllegalArgumentException when one of the inputs is neg.
   */
  private void ensureValidTimes(int... times) {
    for (int i : times) {
      if (i < 0) {
        throw new IllegalArgumentException("Times must be 0 or greater");
      }
    }
  }

  /**
   * Checks that all inputs are valid RGB values (1 - 255).
   *
   * @param inputs the inputs to be checked
   */
  private void ensureValidRGB(double... inputs) {
    for (double i : inputs) {
      if (i < 0 || i > 255) {
        throw new IllegalArgumentException("RGB values must be between 0 and 255");
      }
    }
  }

  /**
   * Checks that all double inputs are positive and > 0. Throws exception if not.
   *
   * @param dimensions the dimensions to be checked
   * @throws IllegalArgumentException when one of the inputs is <= 0.
   */
  private void ensureValidDimensions(double... dimensions) {
    for (double i : dimensions) {
      if (i <= 0) {
        throw new IllegalArgumentException("Dimensions must be greater than 0");
      }
    }
  }

  private void addAnimationToShape(String id, IAnimation animation) {
    if (animationsMap.get(id) == null) {
      ArrayList<IAnimation> list = new ArrayList<IAnimation>();
      list.add(animation);
      animationsMap.put(id, list);
    } else {
      List<IAnimation> list = animationsMap.get(id);
      list.add(animation);
    }
  }

  /**
   * Creates a rectangle IReadableShape and adds it in the IReadableShape hashmap with its name as
   * the key.
   *
   * @param r          R of rGB value (0 - 255)
   * @param g          G of rGB value (0 - 255)
   * @param b          B of rGB value (0 - 255g
   * @param id         name of shape
   * @param cornerXPos x position of rectangle's corner
   * @param cornerYPos y position of rectangle's corner
   * @param width      width of the rectangle
   * @param height     height of the rectangle
   */
  @Override
  public void addRectangle(String id, int r, int g, int b, double radians, double cornerXPos, double
          cornerYPos, double width, double height) {
    ensureValidRGB(r, g, b);
    ensureValidDimensions(width, height);
    IWritableShape rect = new Rectangle(id, r, g, b, cornerXPos, cornerYPos, width, height, radians);
    shapes.put(id, rect);
  }

  /**
   * Creates a Oval IReadableShape and adds it in the IReadableShape hashmap with its name as the
   * key.
   *
   * @param r       R of rGB value (0 - 255)
   * @param g       G of rGB value (0 - 255)
   * @param b       B of rGB value (0 - 255)
   * @param id      name of shape
   * @param centerX x pos of shape's center
   * @param centerY y pos of shape's center
   * @param xRadius x radius of shape
   * @param yRadius y radius of shape
   */
  @Override
  public void addOval(String id, int r, int g, int b, double radians, double centerX, double
          centerY, double xRadius, double yRadius) {
    ensureValidRGB(r, g, b);
    ensureValidDimensions(xRadius, yRadius);
    IWritableShape oval = new Oval(id, r, g, b, centerX, centerY, xRadius, yRadius, radians);
    shapes.put(id, oval);
  }

  /**
   * Creates an animatable shape with appear and disappear times and adds it to the AnimatableShapes
   * hashmap with the shape's id as the key. This method must be called before creating an animation
   * as it allows shapes to be animated.
   *
   * @param id        the name of the shape
   * @param timeStart the time of the shape's appearance
   * @param timeEnd   the time of the shapes' disappearance
   */
  @Override
  public void setTimes(String id, int timeStart, int timeEnd) {
    ensureValidTimes(timeStart, timeEnd);
    IWritableShape shape = shapes.get(id);
    animatableShapes.put(id, new AnimatableShape(shape, timeStart, timeEnd));
    if (timeEnd > maxTime) {
      maxTime = timeEnd;
    }
  }

  /**
   * Creates and adds a move animation to the list of animations.
   *
   * @param id        the name of the shape to be added
   * @param timeStart the time the animation begins
   * @param timeEnd   the time the animation ends
   * @param startX    the x pos the animation starts from
   * @param startY    the y pos the animation starts from
   * @param targetX   the x pos the animation ends at
   * @param targetY   the y pos the animation ends at
   */
  @Override
  public void addMoveAnimation(String id, int timeStart, int timeEnd, double startX, double startY,
                               double targetX, double targetY) {
    ensureValidTimes(timeStart, timeEnd);
    IAnimatableShape animateShape = animatableShapes.get(id);
    if (timeStart < animateShape.appearanceTime() || timeEnd > animateShape.disappearanceTime()) {
      throw new IllegalArgumentException();
    }
    for (IAnimation a : animations) {
      if (id.equals(a.shapeName())) {
        if (!a.isValidMoveA(timeStart, timeEnd)) {
          throw new IllegalArgumentException("This move animation is not valid.");
        }
      }
    }
    IAnimation moveAnimation = new MoveAnimation(animateShape, timeStart, timeEnd, startX,
            startY, targetX, targetY);
    animations.add(moveAnimation);
    addAnimationToShape(id, moveAnimation);
  }

  /**
   * Creates and adds a color animation to the list of animations.
   *
   * @param id        the name of the shape to be added
   * @param timeStart the time the animation begins
   * @param timeEnd   the time the animation ends
   * @param startR    the starting r value of the shape
   * @param startG    the starting G value of the shape
   * @param startB    the starting B value of the shape
   * @param endR      the final r value of the shape
   * @param endG      the final G value of the shape
   * @param endB      the final B value of the shape
   */
  @Override
  public void addColorAnimation(String id, int timeStart, int timeEnd, int startR, int startG,
                                int startB, int endR, int endG, int endB) {
    ensureValidTimes(timeStart, timeEnd);
    ensureValidRGB(startR, startG, startB, endR, endG, endB);
    IAnimatableShape animateShape = animatableShapes.get(id);
    if (timeStart < animateShape.appearanceTime() || timeEnd > animateShape.disappearanceTime()) {
      throw new IllegalArgumentException();
    }
    for (IAnimation a : animations) {
      if (id.equals(a.shapeName())) {
        if (!a.isValidColorA(timeStart, timeEnd)) {
          throw new IllegalArgumentException("This color animation is not valid.");
        }
      }
    }
    IAnimation colorAnimation = new ColorAnimation(animateShape, timeStart, timeEnd, startR,
            startG, startB, endR, endG, endB);
    animations.add(colorAnimation);
    addAnimationToShape(id, colorAnimation);
  }

  /**
   * Creates and adds a scale animation to the list of animations.
   *
   * @param id        the name of the shape to be added
   * @param timeStart the time the animation begins
   * @param timeEnd   the time the animation ends
   * @param dimStartX the initial x dimension of the shape
   * @param dimStartY the initial y dimension of the shape
   * @param dimEndX   the final x dimension of the shape
   * @param dimEndY   the final y dimension of the shape
   */
  @Override
  public void addScaleAnimation(String id, int timeStart, int timeEnd, double dimStartX, double
          dimStartY, double dimEndX, double dimEndY) {
    ensureValidTimes(timeStart, timeEnd);
    ensureValidDimensions(dimStartX, dimStartY, dimEndX, dimEndY);
    IAnimatableShape animateShape = animatableShapes.get(id);
    if (timeStart < animateShape.appearanceTime() || timeEnd > animateShape.disappearanceTime()) {
      throw new IllegalArgumentException();
    }
    for (IAnimation a : animations) {
      if (id.equals(a.shapeName())) {
        if (!a.isValidScaleA(timeStart, timeEnd)) {
          throw new IllegalArgumentException("This scale animation is not valid.");
        }
      }
    }
    IAnimation scaleAnimation = new ScaleAnimation(animateShape, timeStart, timeEnd, dimStartX,
            dimStartY, dimEndX, dimEndY);
    animations.add(scaleAnimation);
    addAnimationToShape(id, scaleAnimation);
  }

  @Override
  public void addRotationAnimation(String id, int timeStart, int timeEnd, double startRad, double targetRad) {
    ensureValidTimes(timeStart, timeEnd);
    IAnimatableShape animateShape = animatableShapes.get(id);
    if (timeStart < animateShape.appearanceTime() || timeEnd > animateShape.disappearanceTime()) {
      throw new IllegalArgumentException();
    }
    for (IAnimation a : animations) {
      if (id.equals(a.shapeName())) {
        if (!a.isValidRotationA(timeStart, timeEnd)) {
          throw new IllegalArgumentException("This rotation animation is not valid.");
        }
      }
    }
    IAnimation moveAnimation = new RotationAnimation(animateShape, timeStart, timeEnd, startRad,
            targetRad);
    animations.add(moveAnimation);
    addAnimationToShape(id, moveAnimation);
  }

  /**
   * Creates a list of shapes representing the position of the shapes at each tick of the animation.
   * For each tick, adds the associated list to an arrayList at the same index.
   *
   * @param fps The amount of frames to be rendered for each tick.
   */
  public void generateFrameArray(int fps) {
    for (int i = 0; i < maxTime * fps; i++) {
      frameArray.add(new ArrayList<>());
    }
    int frameCount = 0;
    while (frameCount < maxTime * fps) {
      for (String key : animatableShapes.keySet()) {
        IAnimatableShape aShape = animatableShapes.get(key);
        if (frameCount >= aShape.appearanceTime() * fps && frameCount <= aShape.disappearanceTime()
                * fps) {

          if (animationsMap.get(key) == null) {
            frameArray.get(frameCount).add(shapes.get(key).deepCopyR());
          } else {
            ArrayList<IAnimation> shapeAnimations = animationsMap.get(key);
            for (IAnimation a : shapeAnimations) {
              if (frameCount >= a.timeStart() * fps && frameCount <= a.timeEnd() * fps) {
                a.tween(frameCount, fps);
              }
            }
            frameArray.get(frameCount).add(shapes.get(key).deepCopyR());
          }
        }
      }
      frameCount++;
    }
  }

  @Override
  public double getFPS() {
    return fps;
  }

  @Override
  public IAnimatableShape getIAnimatableShape(String name) {
    return this.animatableShapes.get(name).deepCopy();
  }

  @Override
  public List<List<IReadableShape>> getFrameArray() {
    return this.frameArray;
  }

  @Override
  public LinkedHashMap<String, IWritableShape> getShapes() {
    LinkedHashMap<String, IWritableShape> copyMap = new LinkedHashMap<>();
    for (String s : shapes.keySet()) {
      copyMap.put(s, shapes.get(s).deepCopyW());
    }
    return copyMap;
  }

  @Override
  public HashMap<String, IAnimatableShape> getIAnimatableShapes() {
    HashMap<String, IAnimatableShape> ishapes = new HashMap<>();
    Set<String> keys = animatableShapes.keySet();
    for (String name : keys) {
      IAnimatableShape ishape = animatableShapes.get(name).deepCopy();
      ishapes.put(ishape.getName(), ishape);
    }
    return ishapes;
  }

  @Override
  public void setFPS(int fps) {
    this.fps = fps;
  }

  public List<IReadableShape> getFrame(int frame) {
    return frameArray.get(frame);
  }

  @Override
  public List<IAnimation> getAnimations() {
    List<IAnimation> aCopyList = new ArrayList<>();
    for (IAnimation a : animations) {
      aCopyList.add(a.deepCopy());
    }
    return aCopyList;
  }

  @Override
  public double maxTime() {
    return maxTime;
  }


  public static final class Builder implements TweenModelBuilder<IWritableModel> {

    LinkedHashMap<String, IWritableShape> shapes;
    LinkedHashMap<String, IAnimatableShape> animatableShapes;
    List<IAnimation> animations;
    List<List<IReadableShape>> frameArray;
    LinkedHashMap<String, ArrayList<IAnimation>> animationsMap;
    int maxTime;

    public static Builder makeBuilder() {
      return new Builder();
    }

    private Builder() {
      shapes = new LinkedHashMap<>();
      animatableShapes = new LinkedHashMap<>();
      animations = new ArrayList<>();
      frameArray = new ArrayList<>();
      animationsMap = new LinkedHashMap<>();
      maxTime = 0;
    }

    private void ensureValidTimes(int... times) {
      for (int i : times) {
        if (i < 0) {
          throw new IllegalArgumentException("Times must be 0 or greater");
        }
      }
    }

    private void ensureValidRGB(double... inputs) {
      for (double i : inputs) {
        if (i < 0 || i > 255) {
          throw new IllegalArgumentException("RGB values must be between 0 and 255");
        }
      }
    }

    private void ensureValidDimensions(double... dimensions) {
      for (double i : dimensions) {
        if (i <= 0) {
          throw new IllegalArgumentException("Dimensions must be greater than 0");
        }
      }
    }

    private void addAnimationToShape(String id, IAnimation animation) {
      if (animationsMap.get(id) == null) {
        ArrayList<IAnimation> list = new ArrayList<IAnimation>();
        list.add(animation);
        animationsMap.put(id, list);
      } else {
        List<IAnimation> list = animationsMap.get(id);
        list.add(animation);
      }
    }

    @Override
    public TweenModelBuilder<IWritableModel> addOval(String name, float cx, float cy, float
            xRadius, float yRadius, float red, float green, float blue, float radians, int
                                                     startOfLife, int
                                                             endOfLife) {
      ensureValidRGB(red * 255, green * 255, blue * 255);
      ensureValidDimensions(xRadius, yRadius);
      ensureValidTimes(startOfLife, endOfLife);
      IWritableShape oval = new Oval(name, red * 255, green * 255, blue * 255, cx,
              cy, xRadius, yRadius, radians);
      shapes.put(name, oval);
      animatableShapes.put(name, new AnimatableShape(oval, startOfLife, endOfLife));
      if (endOfLife > maxTime) {
        maxTime = endOfLife;
      }
      return this;
    }

    @Override
    public TweenModelBuilder<IWritableModel> addRectangle(String name, float lx, float ly, float
            width, float height, float red, float green, float blue, float radians, int startOfLife,
                                                          int
                                                                  endOfLife) {
      ensureValidRGB(red * 255, green * 255, blue * 255);
      ensureValidDimensions(width, height);
      ensureValidTimes(startOfLife, endOfLife);
      IWritableShape rect = new Rectangle(name, red * 255, green * 255, blue * 255, lx,
              ly, width, height, radians);
      shapes.put(name, rect);
      animatableShapes.put(name, new AnimatableShape(rect, startOfLife, endOfLife));
      if (endOfLife > maxTime) {
        maxTime = endOfLife;
      }
      return this;
    }

    @Override
    public TweenModelBuilder<IWritableModel> addMove(String name, float moveFromX, float
            moveFromY, float moveToX, float moveToY, int startTime, int endTime) {
      ensureValidTimes(startTime, endTime);
      IAnimatableShape animateShape = animatableShapes.get(name);
      if (startTime < animateShape.appearanceTime() || endTime > animateShape.disappearanceTime()) {
        throw new IllegalArgumentException("Animation duration outside of shape lifespan");
      }
      for (IAnimation a : animations) {
        if (name.equals(a.shapeName())) {
          if (!a.isValidMoveA(startTime, endTime)) {
            throw new IllegalArgumentException("This move animation is not valid.");
          }
        }
      }
      IAnimation moveAnimation = new MoveAnimation(animateShape, startTime, endTime, moveFromX,
              moveFromY, moveToX, moveToY);
      animations.add(moveAnimation);
      addAnimationToShape(name, moveAnimation);
      return this;
    }

    @Override
    public TweenModelBuilder<IWritableModel> addColorChange(String name, float oldR, float oldG,
                                                            float oldB, float newR, float newG,
                                                            float newB, int startTime, int
                                                                    endTime) {
      ensureValidTimes(startTime, endTime);
      ensureValidRGB(oldR * 255, oldG * 255, oldB * 255, newR * 255, newG * 255,
              newB * 255);
      IAnimatableShape animateShape = animatableShapes.get(name);
      if (startTime < animateShape.appearanceTime() || endTime > animateShape.disappearanceTime()) {
        throw new IllegalArgumentException("Animation duration outside of shape lifespan");
      }
      for (IAnimation a : animations) {
        if (name.equals(a.shapeName())) {
          if (!a.isValidColorA(startTime, endTime)) {
            throw new IllegalArgumentException("This color animation is not valid.");
          }
        }
      }
      IAnimation colorAnimation = new ColorAnimation(animateShape, startTime, endTime,
              oldR * 255,
              oldG * 255, oldB * 255, newR * 255, newG * 255,
              newB * 255);
      animations.add(colorAnimation);
      addAnimationToShape(name, colorAnimation);
      return this;
    }

    @Override
    public TweenModelBuilder<IWritableModel> addScaleToChange(String name, float fromSx, float
            fromSy, float toSx, float toSy, int startTime, int endTime) {
      ensureValidTimes(startTime, endTime);
      ensureValidDimensions(fromSx, fromSy, toSx, toSy);
      IAnimatableShape animateShape = animatableShapes.get(name);
      if (startTime < animateShape.appearanceTime() || endTime > animateShape.disappearanceTime()) {
        throw new IllegalArgumentException("Animation duration outside of shape lifespan");
      }
      for (IAnimation a : animations) {
        if (name.equals(a.shapeName())) {
          if (!a.isValidScaleA(startTime, endTime)) {
            throw new IllegalArgumentException("This scale animation is not valid.");
          }
        }
      }
      IAnimation scaleAnimation = new ScaleAnimation(animateShape, startTime, endTime, fromSx,
              fromSy, toSx, toSy);
      animations.add(scaleAnimation);
      addAnimationToShape(name, scaleAnimation);
      return this;
    }

    @Override
    public TweenModelBuilder<IWritableModel> addRotationToChange(String name, float fromRad, float toRad, int startTime, int endTime) {
      ensureValidTimes(startTime, endTime);
      IAnimatableShape animateShape = animatableShapes.get(name);
      if (startTime < animateShape.appearanceTime() || endTime > animateShape.disappearanceTime()) {
        throw new IllegalArgumentException("Animation duration outside of shape lifespan");
      }
      for (IAnimation a : animations) {
        if (name.equals(a.shapeName())) {
          if (!a.isValidScaleA(startTime, endTime)) {
            throw new IllegalArgumentException("This scale animation is not valid.");
          }
        }
      }
      IAnimation rotationAnimation = new RotationAnimation(animateShape, startTime, endTime,
              fromRad, toRad);
      animations.add(rotationAnimation);
      addAnimationToShape(name, rotationAnimation);
      return this;
    }

    @Override
    public IWritableModel build() {
      return new ModelImpl(shapes, animatableShapes, animations, frameArray,
              animationsMap, maxTime);
    }
  }
}