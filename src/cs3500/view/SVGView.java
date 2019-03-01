package cs3500.view;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.model.model.AbstractAnimation;
import cs3500.model.model.AbstractShape;
import cs3500.model.model.IAnimatableShape;
import cs3500.model.model.IAnimation;
import cs3500.model.model.IReadableShape;

public class SVGView extends AbstractView {

  /**
   * Constructs a view that displays a SVG textual representation of the animation.
   *
   * @param ap               the appendable
   * @param frames           all of the frames in the animation
   * @param animations       all of the animations on each shape
   * @param fps              the speed of the animation
   * @param animatableShapes all of the animatable shapes in the animation
   */
  protected SVGView(Appendable ap, List<List<IReadableShape>> frames,
                    List<IAnimation> animations, double fps, HashMap<String,
          IAnimatableShape> animatableShapes) {
    super(ap, frames, animations, fps, animatableShapes);
  }


  @Override
  public void drawingShapes(int frame) {
    throw new UnsupportedOperationException("This view cannot draw the animation");
  }

  @Override
  public void drawingText() {
    String description = setSVGDescription();
    try {
      ap.append(description);
      ap.append("\n");
      if (ap instanceof BufferedWriter) {
        ((BufferedWriter) ap).close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getViewType() {
    return "svg";
  }

  @Override
  public void setIsRepeating(boolean isRepeating) {
    throw new UnsupportedOperationException("Cannot loop this type of svg animation");
  }

  /**
   * Writes a SVG description of the animation.
   *
   * @return the description of every shape and transformation in the animation.
   */
  private String setSVGDescription() {
    String finalDescription = "";
    finalDescription += "<svg width=\"" + super.getPreferredSize().width + "\" height=\""
            + super.getPreferredSize().height + "\" " + "version=\"" + "1.1\""
            + " xmlns=\"http://www.w3.org/2000/svg\">" + "\n";

    for (int i = 0; i < super.frames.size(); i++) {
      for (IReadableShape shape : super.frames.get(i)) {
        if (!finalDescription.contains(shape.getName())) {
          if (shape.getType().equals(AbstractShape.Type.rect)) {
            finalDescription += "    <" + "rect "
                    + "id=\"" + shape.getName() + "\" x=\"" + shape.getX() + "\" " + "y=\""
                    + shape.getY() + "\"" + " " + "width=\"" + shape.getWidth()
                    + "\" height=" + "\"" + shape.getHeight() + "\" fill=\"rgb" + "("
                    + shape.getR() + "," + shape.getG() + "," + shape.getB() + ")" + "\" "
                    + "visibility=\"visible\">" + "\n";
          } else if (shape.getType().equals(AbstractShape.Type.ellipse)) {
            finalDescription += "    <" + "ellipse "
                    + "id=\"" + shape.getName() + "\" cx=\"" + shape.getX() + "\" " + "cy=\""
                    + shape.getY() + "\"" + " " + "rx=\"" + shape.getWidth()
                    + "\" ry=" + "\"" + shape.getHeight() + "\" fill=\"rgb" + "(" + shape.getR()
                    + "," + shape.getG() + "," + shape.getB() + ")" + "\" "
                    + "visibility=\"visible\">" + "\n";
          }
          for (IAnimation animation : animations) {
            if (animation.shapeName().equals(shape.getName())) {
              finalDescription += describeSVGAnimation(animation, shape);
            }
          }
          finalDescription += "    </" + shape.getType() + ">\n";
        }
      }
    }
    return finalDescription + "</svg>";
  }

  /**
   * Writes a description of the animations that occur on a shape during the animation.
   *
   * @param animation a transformation on a shape
   * @param shape     the shape being described
   * @return the String representing the animation
   */
  private String describeSVGAnimation(IAnimation animation, IReadableShape shape) {
    String fill = "";
    String finalDescription = "";
    Map<AbstractAnimation.Type, List<String>> conditions = new HashMap<>();
    double previousX = 0.0;
    double previousY = 0.0;
    double previousR = 0.0;
    double previousG = 0.0;
    double previousB = 0.0;
    double previousWidth = 0.0;
    double previousHeight = 0.0;

    AbstractAnimation.Type type = animation.getType();
    for (IReadableShape s : super.frames.get((int) animation.timeStart())) {
      if (s.getName().equals(shape.getName())) {
        previousX = s.getX();
        previousY = s.getY();
        previousR = s.getR();
        previousG = s.getG();
        previousB = s.getB();
        previousWidth = s.getWidth();
        previousHeight = s.getHeight();
      }
    }

    switch (type) {
      case move:
        String xConditions = "";
        String yConditions = "";

        if (shape.getType().equals(AbstractShape.Type.ellipse)) {
          xConditions = "cx\" from=\"" + previousX + "\" to=\""
                  + animation.getEndProperties().get(0) + "\"";
          yConditions = "cy\" from=\"" + previousY + "\" to=\""
                  + animation.getEndProperties().get(1) + "\"";
        } else {
          xConditions = "x\" from=\"" + previousX + "\" to=\""
                  + animation.getEndProperties().get(0) + "\"";
          yConditions = "y\" from=\"" + previousY + "\" to=\""
                  + animation.getEndProperties().get(1) + "\"";
        }
        List<String> moveConditions = new ArrayList<>();
        moveConditions.add(xConditions);
        moveConditions.add(yConditions);
        conditions.putIfAbsent(type, moveConditions);
        break;
      case color:
        String rgbConditions = "fill\" from=\"" + "rgb(" + previousR + ","
                + previousG + "," + previousB + ")\" to=\"" + "rgb("
                + animation.getEndProperties().get(0) + "," + animation.getEndProperties().get(1)
                + "," + animation.getEndProperties().get(2) + ")\" ";
        List<String> colorConditions = new ArrayList<>();
        colorConditions.add(rgbConditions);
        conditions.putIfAbsent(type, colorConditions);
        break;
      case scale:
        String dimXConditions = "";
        String dimYConditions = "";

        if (shape.getType().equals(AbstractShape.Type.ellipse)) {
          dimXConditions = "rx\" from=\"" + previousWidth + "\" to=\""
                  + animation.getEndProperties().get(0) + "\"";
          dimYConditions = "ry\" from=\"" + previousHeight + "\" to=\""
                  + animation.getEndProperties().get(1) + "\"";
        } else if (shape.getType().equals(AbstractShape.Type.rect)) {
          dimXConditions = "width\" from=\"" + previousWidth + "\" to=\""
                  + animation.getEndProperties().get(0) + "\"";
          dimYConditions = "height\" from=\"" + previousHeight + "\" to=\""
                  + animation.getEndProperties().get(1) + "\"";
        }
        List<String> scaleConditions = new ArrayList<>();
        scaleConditions.add(dimXConditions);
        scaleConditions.add(dimYConditions);
        conditions.putIfAbsent(type, scaleConditions);
        break;
      default:
        break;
    }

    fill = " fill=\"freeze\"/>";

    for (int i = 0; i < conditions.get(type).size(); i++) {
      finalDescription += "        <animate attributeType= \"xml\" begin=\"" + ((animation
              .timeStart()) / fps) + "s\" dur=\"" + ((animation.timeEnd() - animation.timeStart())
              / fps) + "s\" " + "attributeName=\"" + conditions.get(type).get(i) + fill + "\n";
    }
    return finalDescription;
  }
}