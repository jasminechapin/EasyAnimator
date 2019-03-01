package cs3500.view;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InteractiveViewListenerTests {
  private MockView testView;
  private MockController testController;

  /**
   * Initializes the dummy controller and view for the listener tests.
   */
  @Before
  public void setUp() {
    testView = new MockView(null, null, null, 0, null);
    testController = new MockController(null, testView);
  }

  @Test
  public void testFireTogglePlayEvent() {
    assertEquals("", testController.getStatus());
    testView.fireTogglePlayEvent(testController);
    assertEquals("The animation is paused", testController.getStatus());
    testView.fireTogglePlayEvent(testController);
    assertEquals("The animation is playing", testController.getStatus());
    testView.fireTogglePlayEvent(testController);
    assertEquals("The animation is paused", testController.getStatus());
  }

  @Test
  public void testRestartEvent() {
    assertEquals("", testController.getStatus());
    testView.fireRestartEvent(testController);
    assertEquals("Reset timer and play from 0", testController.getStatus());
    testView.fireTogglePlayEvent(testController);
  }

  @Test
  public void testExportEvent() {
    assertEquals("", testController.getStatus());
    testView.fireExportEvent(testController);
    assertEquals("Exported Animation", testController.getStatus());
  }

  @Test
  public void testSpeedChangeEvent() {
    assertEquals("", testController.getStatus());
    testView.fireSpeedChangeEvent(testController, 10);
    assertEquals("Speed change event received. The fps is now: 10.0", testController.getStatus());
    testView.fireSpeedChangeEvent(testController, 30);
    assertEquals("Speed change event received. The fps is now: 30.0", testController.getStatus());
  }

  @Test
  public void fireTimeChangeEvent() {
    assertEquals("", testController.getStatus());
    testView.fireTimeChangeEvent(testController, 1);
    assertEquals("Scrub event received", testController.getStatus());
    testView.fireTimeChangeEvent(testController, 100);
    assertEquals("Scrub event received", testController.getStatus());
  }

  @Test
  public void fireLoopBackToggleEvent() {
    assertEquals("", testController.getStatus());
    testView.fireLoopBackToggleEvent(testController);
    assertEquals("Loopback enabled: true", testController.getStatus());
    testView.fireLoopBackToggleEvent(testController);
    assertEquals("Loopback enabled: false", testController.getStatus());
    testView.fireLoopBackToggleEvent(testController);
    assertEquals("Loopback enabled: true", testController.getStatus());
  }
}