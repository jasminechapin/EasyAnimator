package cs3500.model.model;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotSame;

public class IReadableShapeTest {

  private IReadableShape rect;
  private IReadableShape oval;

  /**
   * Initializes the IReadableShapes used in the tests.
   */
  @Before
  public void initTests() {
    rect = new Rectangle("R", 1, 1, 1, 1, 2, 4, 5);
    oval = new Oval("O", 1, 1, 1, 3, 4, 6, 7);
  }

  @Test
  public void getR() {
    assertEquals(1.0, rect.getR(), .01);
    assertEquals(1.0, oval.getR(), .01);
  }

  @Test
  public void getG() {
    assertEquals(1.0, rect.getG(), .01);
    assertEquals(1.0, oval.getG(), .01);
  }

  @Test
  public void getB() {
    assertEquals(1.0, rect.getB(), .01);
    assertEquals(1.0, oval.getB(), .01);
  }

  @Test
  public void getName() {
    assertEquals("R", rect.getName());
    assertEquals("O", oval.getName());
  }

  @Test
  public void getX() {
    assertEquals(1.0, rect.getX(), .01);
    assertEquals(3.0, oval.getX(), .01);
  }

  @Test
  public void getY() {
    assertEquals(2.0, rect.getY(), .01);
    assertEquals(4.0, oval.getY(), .01);
  }

  @Test
  public void getWidth() {
    assertEquals(4.0, rect.getWidth(), .01);
    assertEquals(6.0, oval.getWidth(), .01);
  }

  @Test
  public void getHeight() {
    assertEquals(5.0, rect.getHeight(), .01);
    assertEquals(7.0, oval.getHeight(), .01);
  }

  @Test
  public void getDescription() {
    assertEquals("Name: R\nType: rect\nCorner: (1.0,2.0), Width: 4.0, Height: 5.0, Color: (1.0,"
            + "1.0,1.0)\n", rect.getDescription());
    assertEquals("Name: O\nType: ellipse\n"
            + "Center: (3.0,4.0), X radius: 6.0, Y radius: 7.0, Color: (1.0,1.0,1.0)\n", oval
            .getDescription());
  }

  @Test
  public void getType() {
    assertEquals("rect", rect.getType().name());
    assertEquals("ellipse", oval.getType().name());
  }

  @Test
  public void deepCopy() {
    IReadableShape rect_2 = rect.deepCopyR();
    assertNotSame(rect_2, rect);
  }
}