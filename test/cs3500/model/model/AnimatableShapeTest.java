package cs3500.model.model;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class AnimatableShapeTest {

  private IWritableShape rect;
  private IWritableShape oval;
  private IAnimatableShape animatableR;
  private IAnimatableShape animatableO;

  /**
   * Initializes the animatable shapes to be used in the tests.
   */
  @Before
  public void initTests() {
    rect = new Rectangle("R", 1, 1, 1, 1, 1, 1, 1);
    oval = new Oval("O", 1, 1, 1, 1, 1, 1, 1);
    animatableR = new AnimatableShape(rect, 1, 10);
    animatableO = new AnimatableShape(oval, 1, 10);
  }

  @Test
  public void description() {
    assertEquals("Name: R\nType: rect\nCorner: (1.0,1.0), Width: 1.0, Height: 1.0, Color: "
            + "(1.0,1.0,1.0)\nAppears at t=1.0\nDisappears at t=10.0", animatableR.description());
    assertEquals("Name: O\nType: ellipse\nCenter: (1.0,1.0), X radius: 1.0, Y radius: 1.0, "
            + "Color: (1.0,1.0,1.0)\nAppears at t=1.0\nDisappears at t=10.0", animatableO
            .description());
  }

  @Test
  public void getName() {
    assertEquals("R", animatableR.getName());
    assertEquals("O", animatableO.getName());
  }

  @Test
  public void getType() {
    assertEquals("rect", animatableR.getType().name());
    assertEquals("ellipse", animatableO.getType().name());
  }

  @Test
  public void appearanceTime() {
    assertEquals(1.0, animatableR.appearanceTime());
    assertEquals(1.0, animatableO.appearanceTime());
  }

  @Test
  public void disappearanceTime() {
    assertEquals(10.0, animatableR.disappearanceTime());
    assertEquals(10.0, animatableO.disappearanceTime());
  }

  @Test
  public void getIWritableShape() {
    assertEquals(rect, animatableR.getIWritableShape());
    assertEquals(oval, animatableO.getIWritableShape());
  }

  @Test
  public void getReadableShape() {
    assertEquals(rect, animatableR.getReadableShape());
    assertEquals(oval, animatableO.getReadableShape());
  }
}