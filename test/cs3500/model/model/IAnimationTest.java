package cs3500.model.model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class IAnimationTest {

  private IWritableShape rect;
  private IWritableShape oval;
  private IAnimatableShape animatableR;
  private IAnimatableShape animatableO;
  private IAnimation moveA;
  private IAnimation colorA;
  private IAnimation scaleA;

  /**
   * Initializes the shapes, animatable shapes, and IAnimations to be used in the tests.
   */
  @Before
  public void initTests() {
    rect = new Rectangle("R", 1, 1, 1, 1, 1, 1, 1);
    oval = new Oval("O", 1, 1, 1, 1, 1, 1, 1);
    animatableR = new AnimatableShape(rect, 1, 10);
    animatableO = new AnimatableShape(oval, 1, 10);
    moveA = new MoveAnimation(animatableR, 2, 5, 0, 0, 10, 10);
    colorA = new ColorAnimation(animatableR, 2, 5, 1, 1, 1, 5, 5, 5);
    scaleA = new ScaleAnimation(animatableO, 2, 5, 1, 1, 7, 7);
  }

  @Test
  public void describeAnimation() {
    assertEquals("Shape R moves from (0.0,0.0) to (10.0,10.0) ", moveA.describeAnimation());
    assertEquals("Shape R changes color from (1.0,1.0,1.0) to (5.0,5.0,5.0) ", colorA
            .describeAnimation());
    assertEquals("Shape O scales from X radius: 1.0, Y radius: 1.0 to X radius: 7.0, Y radius: "
            + "7.0 ", scaleA.describeAnimation());
  }

  @Test
  public void isValidMoveA() {
    // testing move animation times on a moveAnimation
    assertTrue(moveA.isValidMoveA(5, 8));
    assertTrue(moveA.isValidMoveA(1, 2));
    assertFalse(moveA.isValidMoveA(3, 4));
    assertFalse(moveA.isValidMoveA(3, 7));
    assertFalse(moveA.isValidMoveA(1, 3));
    // testing colorAnimation times on a moveAnimation
    assertTrue(moveA.isValidColorA(5, 8));
    assertTrue(moveA.isValidColorA(1, 2));
    assertTrue(moveA.isValidColorA(3, 4));
    assertTrue(moveA.isValidColorA(3, 7));
    assertTrue(moveA.isValidColorA(1, 3));
    // testing scaleAnimation times on a moveAnimation
    assertTrue(moveA.isValidScaleA(5, 8));
    assertTrue(moveA.isValidScaleA(1, 2));
    assertTrue(moveA.isValidScaleA(3, 4));
    assertTrue(moveA.isValidScaleA(3, 7));
    assertTrue(moveA.isValidScaleA(1, 3));

  }

  @Test
  public void isValidColorA() {
    // testing color animation times on a colorAnimation
    assertTrue(colorA.isValidColorA(5, 8));
    assertTrue(colorA.isValidColorA(1, 2));
    assertFalse(colorA.isValidColorA(3, 4));
    assertFalse(colorA.isValidColorA(3, 7));
    assertFalse(colorA.isValidColorA(1, 3));
    // testing color animation times on a moveAnimation
    assertTrue(colorA.isValidMoveA(5, 8));
    assertTrue(colorA.isValidMoveA(1, 2));
    assertTrue(colorA.isValidMoveA(3, 4));
    assertTrue(colorA.isValidMoveA(3, 7));
    assertTrue(colorA.isValidMoveA(1, 3));
    // testing color animation times on a scaleAnimation
    assertTrue(colorA.isValidScaleA(5, 8));
    assertTrue(colorA.isValidScaleA(1, 2));
    assertTrue(colorA.isValidScaleA(3, 4));
    assertTrue(colorA.isValidScaleA(3, 7));
    assertTrue(colorA.isValidScaleA(1, 3));
  }

  @Test
  public void isValidScaleA() {
    // testing scale animation times on a scaleAnimation
    assertTrue(scaleA.isValidScaleA(5, 8));
    assertTrue(scaleA.isValidScaleA(1, 2));
    assertFalse(scaleA.isValidScaleA(3, 4));
    assertFalse(scaleA.isValidScaleA(3, 7));
    assertFalse(scaleA.isValidScaleA(1, 3));
    // testing scale animation times on a colorAnimation
    assertTrue(scaleA.isValidColorA(5, 8));
    assertTrue(scaleA.isValidColorA(1, 2));
    assertTrue(scaleA.isValidColorA(3, 4));
    assertTrue(scaleA.isValidColorA(3, 7));
    assertTrue(scaleA.isValidColorA(1, 3));
    // testing scale animation times on a moveAnimation
    assertTrue(scaleA.isValidMoveA(5, 8));
    assertTrue(scaleA.isValidMoveA(1, 2));
    assertTrue(scaleA.isValidMoveA(3, 4));
    assertTrue(scaleA.isValidMoveA(3, 7));
    assertTrue(scaleA.isValidMoveA(1, 3));
  }

  @Test
  public void shapeName() {
    assertEquals("R", moveA.shapeName());
    assertEquals("R", colorA.shapeName());
    assertEquals("O", scaleA.shapeName());
  }

  @Test
  public void tween() {
    // testing tween for a moveAnimation
    assertEquals(1.0, rect.getX());
    assertEquals(1.0, rect.getY());
    moveA.tween(5, 1);
    assertEquals(10.0, rect.getX());
    assertEquals(10.0, rect.getY());
    // testing tween for a colorAnimation
    assertEquals(1.0, rect.getR());
    assertEquals(1.0, rect.getG());
    assertEquals(1.0, rect.getB());
    colorA.tween(5, 1);
    assertEquals(5.0, rect.getR());
    assertEquals(5.0, rect.getG());
    assertEquals(5.0, rect.getB());
    // testing tween for a scaleAnimation
    assertEquals(1.0, oval.getWidth());
    assertEquals(1.0, oval.getHeight());
    scaleA.tween(5, 1);
    assertEquals(7.0, oval.getWidth());
    assertEquals(7.0, oval.getHeight());
  }

  @Test
  public void timeStart() {
    assertEquals(2.0, moveA.timeStart());
    assertEquals(2.0, colorA.timeStart());
    assertEquals(2.0, scaleA.timeStart());
  }

  @Test
  public void timeEnd() {
    assertEquals(5.0, moveA.timeEnd());
    assertEquals(5.0, colorA.timeEnd());
    assertEquals(5.0, scaleA.timeEnd());
  }

  @Test
  public void getType() {
    assertEquals("move", moveA.getType().name());
    assertEquals("color", colorA.getType().name());
    assertEquals("scale", scaleA.getType().name());
  }

  @Test
  public void getAnimatableShape() {
    assertEquals(animatableR, moveA.getAnimatableShape());
    assertEquals(animatableR, colorA.getAnimatableShape());
    assertEquals(animatableO, scaleA.getAnimatableShape());
  }

  @Test
  public void getEndProperties() {
    // testing end properties method of a moveAnimation
    List<String> prop1 = moveA.getEndProperties();
    List<String> prop2 = colorA.getEndProperties();
    List<String> prop3 = scaleA.getEndProperties();
    assertEquals("10.0", prop1.get(0));
    assertEquals("10.0", prop1.get(1));
    // testing end properties method of a colorAnimation
    assertEquals("5.0", prop2.get(0));
    assertEquals("5.0", prop2.get(1));
    assertEquals("5.0", prop2.get(2));
    // testing end properties method of a scaleAnimation
    assertEquals("7.0", prop3.get(0));
    assertEquals("7.0", prop3.get(1));
  }
}