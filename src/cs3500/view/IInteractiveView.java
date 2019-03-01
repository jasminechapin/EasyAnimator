package cs3500.view;

import java.awt.event.ActionListener;

public interface IInteractiveView extends IView {

  /**
   * Adds an event listener to the toggle play/pause button.
   *
   * @param listener an action listener that listens for button clicks
   */
  void addTogglePlayEventListener(ActionListener listener);

  /**
   * Adds an event listener to the restart button.
   *
   * @param listener an action listener that listens for button clicks
   */
  void addRestartEventListener(ActionListener listener);

  /**
   * Adds an event listener to the export button.
   *
   * @param listener an action listener that listens for button clicks
   */
  void addExportEventListener(ActionListener listener);

  /**
   * Adds a change listener to the speed change slider.
   *
   * @param listener a change listener that listens for changes to the slider's value
   */
  void addSpeedChangeEventListener(ActionListener listener);

  /**
   * Adds a change listener to the timeline slider.
   *
   * @param listener a change listener that listens for changes to the slider's value
   */
  void addTimeChangeEventListener(ActionListener listener);

  /**
   * Adds an event listener to the toggle loopback button.
   *
   * @param listener an action listener that listens for button clicks
   */
  void addLoopBackToggleEventListener(ActionListener listener);

  /**
   * Fires a play/pause event if the toggle button is clicked.
   *
   * @param listener an action listener that listens for button clicks
   */
  void fireTogglePlayEvent(ActionListener listener);

  /**
   * Fires a restart event if the restart button is clicked.
   *
   * @param listener an action listener that listens for button clicks
   */
  void fireRestartEvent(ActionListener listener);

  /**
   * Fires a export event if the export button is clicked.
   *
   * @param listener an action listener that listens for button clicks
   */
  void fireExportEvent(ActionListener listener);

  /**
   * Fires a speed change event if the speed slider is moved.
   *
   * @param listener an change listener that listens for slides.
   */
  void fireSpeedChangeEvent(ActionListener listener, int newSpeed);

  /**
   * Fires a time change event if the time slider is moved.
   *
   * @param listener an change listener that listens for slides.
   */
  void fireTimeChangeEvent(ActionListener listener, int newTime);

  /**
   * Fires a loopback event if the export button is clicked/unclicked.
   *
   * @param listener an action listener that listens for button clicks
   */
  void fireLoopBackToggleEvent(ActionListener listener);
}