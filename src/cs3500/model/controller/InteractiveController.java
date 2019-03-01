package cs3500.model.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import cs3500.model.model.IWritableModel;
import cs3500.view.IInteractiveView;

public class InteractiveController implements IController, ActionListener {
  private IWritableModel model;
  private IInteractiveView view;
  private Timer timer;
  private double fps;
  private int frameCount;
  private boolean isPlaying;
  private boolean loopBackEnabled;

  /**
   * Constructs an InteractiveController that will communicate between the model and view for each
   * action.
   *
   * @param model The model to be used by this controller.
   * @param view  The view to be used by this controller.
   */
  public InteractiveController(IWritableModel model, IInteractiveView view) {
    this.model = model;
    this.view = view;
    this.frameCount = 0;
    this.fps = model.getFPS();
    view.addTogglePlayEventListener(this);
    view.addRestartEventListener(this);
    view.addExportEventListener(this);
    view.addSpeedChangeEventListener(this);
    view.addLoopBackToggleEventListener(this);
    view.addTimeChangeEventListener(this);
    timer = null;
    isPlaying = true;
    loopBackEnabled = false;
  }

  @Override
  public void run(double fps) {
    setTimerAction(0);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "togglePlay":
        if (isPlaying) {
          isPlaying = false;
        } else {
          setTimerAction(frameCount);
        }
        break;
      case "restart":
        isPlaying = false;
        setTimerAction(0);
        break;
      case "export":
        model.setFPS((int) fps);
        view.setFPS(fps);
        view.drawingText();
        break;
      case "toggleLoopback":
        if (loopBackEnabled) {
          loopBackEnabled = false;
        } else {
          loopBackEnabled = true;
        }
        break;
      case "scrub":
        isPlaying = false;
        view.drawingShapes(e.getID());
        frameCount = e.getID();
        break;
      case "speed change":
        fps = e.getID();
        timer.setDelay(1000 / (int) fps);
        break;
      default:
        break;
    }
  }

  /**
   * Starts the animation's timer.
   *
   * @param startFrame the frame number from which the playthrough of the animation will start
   */
  private void setTimerAction(int startFrame) {
    isPlaying = true;
    frameCount = startFrame;
    int delay = (int) (1000 / this.fps);
    ActionListener taskPerformer = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (loopBackEnabled && frameCount == model.maxTime()) {
          setTimerAction(0);
        }
        if (frameCount >= model.maxTime() || !isPlaying) {
          timer.stop();
          return;
        }
        view.drawingShapes(frameCount++);
      }
    };
    if (timer != null) {
      timer.stop();
    }
    timer = new Timer(delay, taskPerformer);
    timer.start();
  }
}