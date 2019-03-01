package cs3500.model.model;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class IWritableShapeTest {

  private IWritableShape rect;
  private IWritableShape oval;

  /**
   * Initializes the IWritableShapes used in the tests.
   */
  @Before
  public void initTest() {
    rect = new Rectangle("R", 1, 1, 1, 1, 2, 4, 5);
    oval = new Oval("O", 1, 1, 1, 3, 4, 6, 7);
  }

  @Test
  public void mutatePos() {
    // testing mutating position on a rectangle
    assertEquals(1.0, rect.getX(), .01);
    assertEquals(2.0, rect.getY(), .01);
    rect.mutatePos(3, 4);
    assertEquals(3.0, rect.getX(), .01);
    assertEquals(4.0, rect.getY(), .01);
    // testing mutating position on an oval
    assertEquals(3.0, oval.getX(), .01);
    assertEquals(4.0, oval.getY(), .01);
    oval.mutatePos(8, 9);
    assertEquals(8.0, oval.getX(), .01);
    assertEquals(9.0, oval.getY(), .01);
  }

  @Test
  public void mutateDim() {
    // testing mutating dimension on a rectangle
    assertEquals(4.0, rect.getWidth(), .01);
    assertEquals(5.0, rect.getHeight(), .01);
    rect.mutateDim(6, 7);
    assertEquals(6.0, rect.getWidth(), .01);
    assertEquals(7.0, rect.getHeight(), .01);
    // testing mutating dimensions on an oval
    assertEquals(6.0, oval.getWidth(), .01);
    assertEquals(7.0, oval.getHeight(), .01);
    oval.mutateDim(2, 4);
    assertEquals(2.0, oval.getWidth(), .01);
    assertEquals(4.0, oval.getHeight(), .01);
  }

  @Test
  public void mutateColors() {
    // testing mutating color on a rectangle
    assertEquals(1.0, rect.getR(), .01);
    assertEquals(1.0, rect.getG(), .01);
    assertEquals(1.0, rect.getB(), .01);
    rect.mutateColors(3, 3, 3);
    assertEquals(3.0, rect.getR(), .01);
    assertEquals(3.0, rect.getG(), .01);
    assertEquals(3.0, rect.getB(), .01);
    // testing mutating color on an oval
    assertEquals(1.0, oval.getR(), .01);
    assertEquals(1.0, oval.getG(), .01);
    assertEquals(1.0, oval.getB(), .01);
    oval.mutateColors(3, 3, 3);
    assertEquals(3.0, oval.getR(), .01);
    assertEquals(3.0, oval.getG(), .01);
    assertEquals(3.0, oval.getB(), .01);
  }
}