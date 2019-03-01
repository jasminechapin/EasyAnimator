package cs3500.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cs3500.model.model.AbstractAnimation;
import cs3500.model.model.AbstractShape;
import cs3500.model.model.IAnimatableShape;
import cs3500.model.model.IAnimation;
import cs3500.model.model.IReadableShape;
import cs3500.view.panels.AbstractDrawingPanel;
import cs3500.view.panels.VisualDrawingPanel;

import static javax.swing.SwingConstants.CENTER;
import static javax.swing.SwingConstants.VERTICAL;

public class InteractiveView extends AbstractView implements IInteractiveView {
  private JLabel feedback;
  private JButton togglePlay;
  private JButton restart;
  private JToggleButton loopback;
  private JButton export;
  private JSlider speedSlider;
  private JSlider timeSlider;
  private boolean isPlaying;
  private boolean isRepeating;
  private File f;
  private int frame;

  /**
   * Constructs a interactive view that can be paused, looped, exported as an svg and sped up/slowed
   * down.
   *
   * @param ap               the appendable
   * @param frames           all of the frames in the animation
   * @param animations       all of the animations on each shape
   * @param fps              the speed of the animation
   * @param animatableShapes all of the animatable shapes in the animation
   */
  protected InteractiveView(Appendable ap, List<List<IReadableShape>> frames,
                            List<IAnimation> animations, double fps, HashMap<String,
          IAnimatableShape> animatableShapes) {
    super(ap, frames, animations, fps, animatableShapes);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    drawingPanel = new VisualDrawingPanel();

    JToolBar toolBarBottom = new JToolBar(VERTICAL);
    addComponentsBottom(toolBarBottom);
    this.getContentPane().add(toolBarBottom, BorderLayout.SOUTH);

    JToolBar toolBarLeft = new JToolBar(VERTICAL);
    addButtonsLeft(toolBarLeft);
    this.getContentPane().add(toolBarLeft, BorderLayout.LINE_START);

    JPanel right = new JPanel();
    right.setLayout(new GridLayout(4, 1));
    addButtonsRight(right);
    this.getContentPane().add(right, BorderLayout.LINE_END);

    feedback = new JLabel("<html><div style='text-align: center;'>Instructions: The first " +
            "button toggles between pause and resume. "
            + "The restart button resets the animation to the beginning. The loopback button "
            + "toggles repeating the animation. The export button exports the animation to a "
            + "chosen svg file.<br>The left slider adjusts the speed. The bottom one adjusts the" +
            " " + "current frame.</div></html>", SwingConstants.CENTER);
    feedback.setPreferredSize(new Dimension(1500, 30));
    JPanel top = new JPanel();
    top.add(feedback, CENTER);
    getContentPane().add(top, BorderLayout.NORTH);


    jScrollPane = new JScrollPane((AbstractDrawingPanel) drawingPanel);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.add(jScrollPane);

    this.setIsRepeating(false);
    isPlaying = true;
    f = new File("");
    this.setVisible(true);
  }

  private void addButtonsLeft(JToolBar toolBar) {
    speedSlider = new JSlider(JSlider.VERTICAL, 0, 180, (int) fps);
    speedSlider.setMajorTickSpacing(10);
    speedSlider.setMinorTickSpacing(1);
    speedSlider.setPaintTicks(true);
    speedSlider.setPaintLabels(true);
    toolBar.add(speedSlider);
  }

  private void addButtonsRight(JPanel panel) {
    togglePlay = new JButton("Pause");
    togglePlay.setActionCommand("stop");
    panel.add(togglePlay);
    restart = new JButton("Restart");
    panel.add(restart);
    loopback = new JToggleButton("Enable Loopback");
    panel.add(loopback);
    export = new JButton("Export");
    panel.add(export);
  }

  private void addComponentsBottom(JToolBar toolBar) {
    timeSlider = new JSlider(JSlider.HORIZONTAL, 0, frames.size() - 1, frame);
    timeSlider.setMajorTickSpacing(frames.size() / 50);
    timeSlider.setMinorTickSpacing((frames.size() / 500) / (int) fps);
    timeSlider.setPaintTicks(true);
    timeSlider.setPaintLabels(true);
    toolBar.add(timeSlider);
  }

  @Override
  public void drawingShapes(int frame) {
    if (Math.abs(frame - frames.size()) == 1 && !isRepeating) {
      feedback.setText("The animation is over");
    }

    this.frame = frame;
    timeSlider.setValue(frame);
    List<IReadableShape> shapesToDraw = frames.get(frame);
    drawingPanel.drawShapes(shapesToDraw);
  }

  @Override
  public void drawingText() {
    String description = setSVGDescription();
    try {
      if (ap instanceof StringBuilder) {
        ap.append(description);
        ap.append("\n");
      } else {
        ap = new BufferedWriter(new FileWriter(f.getName()));
        ap.append(description);
        ap.append("\n");
        ((BufferedWriter) ap).close();
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Enter an appendable location");
    }
  }

  private String setSVGDescription() {
    String finalDescription = "";
    IAnimation iAnimation = null;
    List<AbstractAnimation.Type> animationsToUndo = new ArrayList<>();
    finalDescription += "<svg width=\"" + super.getPreferredSize().width + "\" height=\""
            + super.getPreferredSize().height + "\" " + "version=\"" + "1.1\""
            + " xmlns=\"http://www.w3.org/2000/svg\">" + "\n";

    if (isRepeating) {
      finalDescription = finalDescription + " <rect>\n" +
              "        <animate id=\"base\" begin=\"0;base.end\" dur=\"" + (frames.size() / fps)
              + "s\" " + "attributeName=\"visibility\" from=\"hide\"\n" + "                 "
              + "to=\"hide\"/>\n" + "    </rect>" + "\n";
    }

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
              iAnimation = animation;
              animationsToUndo.add(animation.getType());
            }
          }
          if (isRepeating) {
            finalDescription += undoAnimations(animationsToUndo, iAnimation, shape);
          }
          finalDescription += "    </" + shape.getType() + ">\n";
        }
      }
    }
    return finalDescription + "</svg>";
  }

  private String undoAnimations(List<AbstractAnimation.Type> animationsToUndo, IAnimation
          animation, IReadableShape shape) {
    String undo = "";
    for (AbstractAnimation.Type type : animationsToUndo) {
      switch (type) {
        case color:
          undo += "        <animate attributeType=\"xml\" " +
                  "begin=\"base.begin\" dur=\"" + animation.getAnimatableShape().appearanceTime() +
                  "\" " +
                  "attributeName=\"fill\" from=\"rgb(" + shape.getR() + "," + shape.getG()
                  + "," + shape.getB() + ")\" to=\"rgb(" + shape.getR() + "," + shape.getG()
                  + "," + shape.getB() + ")\" " + "fill=\"freeze\"/>\n";
          break;
        case scale:
          if (shape.getType().equals(AbstractShape.Type.ellipse)) {
            undo += "        <animate attributeType=\"xml\" " +
                    "begin=\"base.begin\" dur=\"" + animation.getAnimatableShape().appearanceTime()
                    + "\" " +
                    "attributeName=\"rx\"" + " from=\"" + shape.getWidth() + "\" to=\"" +
                    shape.getWidth() + "\" fill=\"freeze\"/>" + "\n" +
                    "        <animate attributeType=\"xml\" " +
                    "begin=\"base.begin\" dur=\"" + (animation.timeStart() / fps) + "\" " +
                    "attributeName=\"ry\"" + " from=\"" + shape.getHeight() + "\" to=\"" +
                    shape.getHeight() + "\" fill=\"freeze\"/>\n";
          } else if (shape.getType().equals(AbstractShape.Type.rect)) {
            undo += "        <animate attributeType=\"xml\" " +
                    "begin=\"base.begin\" dur=\"" + animation.getAnimatableShape().appearanceTime()
                    + "\" " +
                    "attributeName=\"width\"" + " from=\"" + shape.getWidth() + "\" to=\"" +
                    shape.getWidth() + "\" fill=\"freeze\"/>" + "\n" +
                    "        <animate attributeType=\"xml\" " +
                    "begin=\"base.begin\" dur=\"" + animation.getAnimatableShape().appearanceTime()
                    + "\" " +
                    "attributeName=\"height\"" + " from=\"" + shape.getHeight() + "\" to=\"" +
                    shape.getHeight() + "\" fill=\"freeze\"/>\n";
          }
          break;
        case move:
          if (shape.getType().equals(AbstractShape.Type.ellipse)) {
            undo += "        <animate attributeType=\"xml\" " +
                    "begin=\"base.begin\" dur=\"" + animation.getAnimatableShape().appearanceTime()
                    + "\" " +
                    "attributeName=\"cx\"" + " from=\"" + shape.getX() + "\" to=\"" +
                    shape.getX() + "\" fill=\"freeze\"/>" + "\n" +
                    "        <animate attributeType=\"xml\" " +
                    "begin=\"base.begin\" dur=\"" + animation.getAnimatableShape().appearanceTime()
                    + "\" " +
                    "attributeName=\"cy\"" + " from=\"" + shape.getY() + "\" to=\"" +
                    shape.getY() + "\" fill=\"freeze\"/>\n";
          } else if (shape.getType().equals(AbstractShape.Type.rect)) {
            undo += "        <animate attributeType=\"xml\" " +
                    "begin=\"base.begin\" dur=\"" + animation.getAnimatableShape().appearanceTime()
                    + "\" " +
                    "attributeName=\"x\"" + " from=\"" + shape.getX() + "\" to=\"" +
                    shape.getX() + "\" fill=\"freeze\"/>" + "\n" +
                    "        <animate attributeType=\"xml\" " +
                    "begin=\"base.begin\" dur=\"" + animation.getAnimatableShape().appearanceTime()
                    + "\" " +
                    "attributeName=\"y\"" + " from=\"" + shape.getY() + "\" to=\"" +
                    shape.getY() + "\" fill=\"freeze\"/>\n";
          }
          break;
        default:
          break;
      }
    }
    return undo;
  }

  /**
   * Writes a SVG description of the animation. Includes looping.
   *
   * @return the description of every shape and transformation in the animation.
   */
  private String describeSVGAnimation(IAnimation animation, IReadableShape shape) {
    String fill = "";
    String finalDescription = "";
    String repeatingAnimation = ";base.end+" + (animation.timeStart() / fps);
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
      if (!isRepeating) {
        repeatingAnimation = "s";
      }

      finalDescription += "        <animate attributeType= \"xml\" begin=\""
              + ((animation.timeStart()) / fps) + repeatingAnimation + "\" "
              + "dur=\"" + ((animation.timeEnd() - animation.timeStart()) / fps) + "s\" "
              + "attributeName=\"" + conditions.get(type).get(i) + fill + "\n";
    }
    return finalDescription;
  }

  @Override
  public String getViewType() {
    return "interactive";
  }

  @Override
  public void setFPS(double fps) {
    this.fps = fps;
  }

  @Override
  public void addTogglePlayEventListener(ActionListener listener) {
    ActionListener listen = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        fireTogglePlayEvent(listener);
      }
    };
    togglePlay.addActionListener(listen);
  }

  @Override
  public void fireTogglePlayEvent(ActionListener listener) {
    listener.actionPerformed(new ActionEvent(togglePlay, 1, "togglePlay"));
    if (isPlaying) {
      togglePlay.setText("Play");
      if (!feedback.getText().equals("The animation is over")) {
        feedback.setText("Animation is paused");
      }
      isPlaying = false;
    } else {
      togglePlay.setText("Pause");
      if (!feedback.getText().equals("The animation is over")) {
        feedback.setText("Animation is playing");
      }
      isPlaying = true;
    }
  }

  @Override
  public void addRestartEventListener(ActionListener listener) {
    ActionListener listen = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        fireRestartEvent(listener);
      }
    };
    restart.addActionListener(listen);
  }

  @Override
  public void fireRestartEvent(ActionListener listener) {
    listener.actionPerformed(new ActionEvent(restart, 1, "restart"));
    togglePlay.setText("Pause");
    feedback.setText("Restarted Animation");
    isPlaying = true;
  }

  @Override
  public void addExportEventListener(ActionListener listener) {
    ActionListener listen = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        fireExportEvent(listener);
        System.out.println(fps);
      }
    };
    export.addActionListener(listen);
  }

  @Override
  public void fireExportEvent(ActionListener listener) {
    if (isPlaying) {
      listener.actionPerformed(new ActionEvent(export, 1, "togglePlay"));
      isPlaying = false;
      togglePlay.setText("Play");
    }
    JFileChooser chooser = new JFileChooser(".");
    int retvalue = chooser.showSaveDialog(feedback);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      f = chooser.getSelectedFile();
      feedback.setText("The svg has been saved to: " + f.getAbsolutePath());
      listener.actionPerformed(new ActionEvent(export, 1, "export"));
    }
  }

  @Override
  public void addSpeedChangeEventListener(ActionListener listener) {
    speedSlider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
          if (source.getValue() != 0) {
            fireSpeedChangeEvent(listener, source.getValue());
          } else {
            fireSpeedChangeEvent(listener, 1);
            feedback.setText("Don't set the speed to 0!    Speed is now 1.");
          }
        }
      }
    });
  }

  @Override
  public void fireSpeedChangeEvent(ActionListener listener, int newSpeed) {
    listener.actionPerformed(new ActionEvent(speedSlider, newSpeed, "speed change"));
    feedback.setText("The speed is now: " + newSpeed);
  }

  @Override
  public void addTimeChangeEventListener(ActionListener listener) {
    timeSlider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (source.getValueIsAdjusting()) {
          fireTimeChangeEvent(listener, source.getValue());
        }
      }
    });
  }

  @Override
  public void fireTimeChangeEvent(ActionListener listener, int newTime) {
    listener.actionPerformed(new ActionEvent(timeSlider, newTime, "scrub"));
    isPlaying = false;
    togglePlay.setText("Play");
    feedback.setText("The current frame is now: " + newTime);
  }

  @Override
  public void addLoopBackToggleEventListener(ActionListener listener) {
    ActionListener listen = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        fireLoopBackToggleEvent(listener);
      }
    };
    loopback.addActionListener(listen);
  }

  @Override
  public void fireLoopBackToggleEvent(ActionListener listener) {
    listener.actionPerformed(new ActionEvent(loopback, 1, "toggleLoopback"));
    if (isRepeating) {
      this.setIsRepeating(false);
      feedback.setText("The animation will no longer loop");
    } else {
      this.setIsRepeating(true);
      feedback.setText("The animation will now loop");
    }
  }

  @Override
  public void setIsRepeating(boolean isRepeating) {
    this.isRepeating = isRepeating;
  }
}