package cs3500.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cs3500.model.controller.IController;
import cs3500.model.model.IWritableModel;

/**
 * This class exactly imitates class InteractiveController for testing purposes. It implements the
 * same methods. However, functionality is disabled and any events received are reported to a status
 * field.
 */
public class MockController implements IController, ActionListener {

  private IWritableModel model;
  private IInteractiveView view;
  private int frameCount;
  private boolean isPlaying;
  private boolean loopBackEnabled;
  private String status;

  MockController(IWritableModel model, MockView view) {
    view.addTimeChangeEventListener(this);
    isPlaying = true;
    loopBackEnabled = false;
    status = "";
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "togglePlay":
        if (isPlaying) {
          isPlaying = false;
          status = "The animation is paused";
        } else {
          setTimerAction(frameCount);
          status = "The animation is playing";
        }
        break;
      case "restart":
        isPlaying = false;
        setTimerAction(0);
        status = "Reset timer and play from 0";
        break;
      case "export":
        status = "Exported Animation";
        break;
      case "toggleLoopback":
        loopBackEnabled = !loopBackEnabled;
        status = "Loopback enabled: " + loopBackEnabled;
        break;
      case "scrub":
        isPlaying = false;
        frameCount = e.getID();
        status = "Scrub event received";
        break;
      case "speed change":
        double fps = e.getID();
        status = "Speed change event received. The fps is now: " + fps;
        break;
      default:
        break;
    }
  }

  /**
   * Return the mock status of the animation.
   *
   * @return a description of what would occur if an event had actually been fired
   */
  public String getStatus() {
    return status;
  }

  /**
   * Sets the animation to play at the given frame.
   *
   * @param startFrame the frame the animation from which the animation will begin
   */
  private void setTimerAction(int startFrame) {
    isPlaying = true;
    frameCount = startFrame;
  }

  @Override
  public void run(double fps) {
    // run method not required for testing listeners
  }
}