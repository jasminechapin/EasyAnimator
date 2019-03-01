package cs3500.model.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import cs3500.model.model.IWritableModel;
import cs3500.view.IView;

public class Controller implements IController {
  private IWritableModel model;
  private IView view;
  private Timer timer;
  private int frameCount = 0;

  /**
   * Constructs an IController that will communicate between the model and view for each action.
   *
   * @param model The model to be used by this controller.
   * @param view  The view to be used by this controller.
   */
  public Controller(IWritableModel model, IView view) {
    this.model = model;
    this.view = view;
    timer = new Timer(1, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // initializes the timer
      }
    });
  }

  @Override
  public void run(double fps) {
    int delay = ((int) (1000 / fps));

    if (view.getViewType().equals("svg") || view.getViewType().equals("text") ||
            view.getViewType().equals("interactive")) {
      view.drawingText();
    }

    if (view.getViewType().equals("interactive") || view.getViewType().equals("visual")) {
      ActionListener taskPerformer = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          if (frameCount >= model.maxTime()) {
            timer.stop();
            return;
          }
          view.drawingShapes(frameCount++);
        }
      };
      new Timer(delay, taskPerformer).start();
    }
  }
}