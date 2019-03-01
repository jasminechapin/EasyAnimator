package cs3500.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cs3500.model.model.IAnimatableShape;
import cs3500.model.model.IAnimation;
import cs3500.model.model.IReadableShape;

/**
 * This class exactly mimics the class InteractiveView. All display functionality is disabled, only
 * the listeners remain.
 */
public class MockView extends AbstractView implements IInteractiveView {
  private JLabel feedback;
  private JButton togglePlay;
  private JButton restart;
  private JToggleButton loopback;
  private JButton export;
  private JSlider speedSlider;
  private JSlider timeSlider;
  private boolean isPlaying;
  private boolean isRepeating;
  private List<IReadableShape> shapesToDraw;
  private int frame;

  /**
   * Constructs a dummy view which only implements firing events to the listeners. All other
   * functionality is disabled.
   * @param ap         An output location
   * @param frames     the shapes to be rendered
   * @param animations the animations to be rendered
   * @param fps        The tick rate of the animation
   */
  MockView(Appendable ap, List<List<IReadableShape>> frames,
           List<IAnimation> animations, double fps, HashMap<String,
          IAnimatableShape> animatableShapes) {
    super(ap, frames, animations, fps, animatableShapes);
    speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 99, frame);
    timeSlider = new JSlider(JSlider.HORIZONTAL, 0, 99, frame);
    addButtonsRight(new JPanel());
    feedback = new JLabel("Instructions: The first button toggles between pause and resume. "
            + "The restart button resets the animation to the beginning. The loopback button "
            + "toggles repeating the animation. The export button exports the animation to a "
            + "chosen svg file.");
    isRepeating = false;
    isPlaying = true;
    File f = new File("");
  }

  private void addButtonsLeft(JToolBar toolBar) {
    // mock method
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
    // mock method
  }

  @Override
  public void drawingShapes(int frame) {
    // mock method
  }

  @Override
  public void drawingText() {
    // mock method
  }

  /**
   * Writes a SVG description of the animation.
   *
   * @return the description of every shape and transformation in the animation.
   */
  private String setSVGDescription() {
    return "";
  }

  /**
   * Writes a description of the animations that occur on a shape during the animation.
   *
   * @param animation a transformation on a shape
   * @param shape     the shape being described
   * @return the String representing the animation
   */
  private String describeSVGAnimation(IAnimation animation, IReadableShape shape) {
    return "";
  }

  @Override
  public String getViewType() {
    return "interactive";
  }

  @Override
  public void setFPS(double fps) {
    // mock method
  }

  @Override
  public void setIsRepeating(boolean isRepeating) {
    // mock method
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
    listener.actionPerformed(new ActionEvent(this, 1, "togglePlay"));
    if (isPlaying) {
      togglePlay.setText("Play");
      feedback.setText("Animation is paused");
      isPlaying = false;
    } else {
      togglePlay.setText("Pause");
      feedback.setText("Animation is playing");
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
      }
    };
    export.addActionListener(listen);
  }

  @Override
  public void fireExportEvent(ActionListener listener) {
    listener.actionPerformed(new ActionEvent(export, 1, "export"));
  }

  @Override
  public void addSpeedChangeEventListener(ActionListener listener) {
    speedSlider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
          fireSpeedChangeEvent(listener, source.getValue());
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
      isRepeating = false;
      feedback.setText("The animation will no longer loop");
    } else {
      isRepeating = true;
      feedback.setText("The animation will now loop");
    }
  }
}