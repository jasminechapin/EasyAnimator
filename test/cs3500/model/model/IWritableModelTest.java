package cs3500.model.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class IWritableModelTest {
  private IWritableModel model;
  private ModelImpl model_2;

  /**
   * Initializes 3 models to be used for the tests.
   */
  @Before
  public void initTests() {
    model = new ModelImpl();
    model_2 = new ModelImpl();
    model_2.addRectangle("R", 1, 1, 1, 200.0, 200.0, 50, 100);
    ModelImpl simpleAnimModel = new ModelImpl();
    simpleAnimModel.addRectangle("R", 1, 1, 1, 200.0, 200.0, 50, 100);
    simpleAnimModel.addOval("C", 1, 1, 1, 500.0, 100.0, 60, 30);
    simpleAnimModel.setTimes("R", 1, 100);
    simpleAnimModel.setTimes("C", 6, 100);
    simpleAnimModel.addMoveAnimation("R", 10, 50, 200, 200, 300, 300);
    simpleAnimModel.addMoveAnimation("C", 20, 70, 500, 100, 500, 400);
    simpleAnimModel.addColorAnimation("C", 50, 80, 1, 1, 1, 2, 2, 2);
    simpleAnimModel.addMoveAnimation("R", 70, 100, 300, 300, 200, 200);
    simpleAnimModel.addScaleAnimation("R", 51, 70, 50, 100, 25, 100);
  }

  @Test
  public void addRectangle() {
    assertNull(model.getShapes().get("R1"));
    model.addRectangle("R1", 1, 1, 1, 200.0, 200.0, 50, 100);
    assertNotNull(model.getShapes().get("R1"));
  }

  @Test
  public void addOval() {
    assertNull(model.getShapes().get("O"));
    model.addOval("O", 1, 1, 1, 500.0, 100.0, 60, 30);
    assertNotNull(model.getShapes().get("O"));
  }

  @Test
  public void setTimes() {
    model_2.setTimes("R", 5, 10);
    assertEquals(model_2.getIAnimatableShape("R").appearanceTime(), 5, .01);
    assertEquals(model_2.getIAnimatableShape("R").disappearanceTime(), 10, .01);
  }

  @Test
  public void addMoveAnimation() {
    model.addRectangle("R", 1, 1, 1, 200.0, 200.0, 50, 100);
    model.setTimes("R", 1, 100);
    model.addMoveAnimation("R", 10, 50, 200, 200, 300, 300);
    assertEquals(1, model.getAnimations().size());
    model.addOval("C", 1, 1, 1, 500.0, 100.0, 60, 30);
    model.setTimes("C", 1, 100);
    model.addMoveAnimation("C", 2, 90, 500, 100, 600, 100);
    assertEquals(2, model.getAnimations().size());
  }

  @Test
  public void addColorAnimation() {
    assertEquals(0, model.getAnimations().size());
    model.addOval("O", 1, 1, 1, 500, 500.0, 100.0, 60);
    model.setTimes("O", 1, 100);
    model.addColorAnimation("O", 50, 80, 1, 1, 1, 2, 2, 2);
    assertEquals(1, model.getAnimations().size());
    model.addRectangle("R", 1, 1, 1, 200.0, 200.0, 50, 100);
    model.setTimes("R", 1, 100);
    model.addColorAnimation("R", 50, 80, 1, 1, 1, 2, 2, 2);
    assertEquals(2, model.getAnimations().size());
  }

  @Test
  public void addScaleAnimation() {
    assertEquals(0, model.getAnimations().size());
    model.addRectangle("R",1, 1, 1, 200.0, 200.0, 50, 100);
    model.setTimes("R", 1, 100);
    model.addScaleAnimation("R", 51, 70, 50, 100, 25, 100);
    assertEquals(1, model.getAnimations().size());
    model.addOval("O",1, 1, 1, 500.0, 100.0, 60, 30);
    model.setTimes("O", 1, 100);
    model.addScaleAnimation("O", 10, 50, 500, 100, 600, 100);
    assertEquals(2, model.getAnimations().size());
  }

  //  @Test
  //  public void readBack() {
  //    assertEquals("Shapes:\n" +
  //            "Name: R\n" +
  //            "Type: rect\n" +
  //            "Corner: (200.0,200.0), Width: 50.0, Height: 100.0, Color: (1.0,1.0,1.0)\n" +
  //            "Appears at t=1\n" +
  //            "Disappears at t=100\n" +
  //            "\n" +
  //            "Name: C\n" +
  //            "Type: ellipse\n" +
  //            "Center: (500.0,100.0), X radius: 60.0, Y radius: 30.0, Color: (1.0,1.0,1.0)\n" +
  //            "Appears at t=6\n" +
  //            "Disappears at t=100\n" +
  //            "\n" +
  //            "Shape R moves from (200.0,200.0) to (300.0,300.0) from t=10 to t=50\n" +
  //            "Shape C moves from (500.0,100.0) to (500.0,400.0) from t=20 to t=70\n" +
  //            "Shape C changes color from (1.0,1.0,1.0) to (2.0,2.0,2.0) from t=50 to t=80\n" +
  //            "Shape R moves from (300.0,300.0) to (200.0,200.0) from t=70 to t=100\n" +
  //            "Shape R scales from Width: 50.0, Height: 100.0 to Width: 25.0, Height: 100.0 from "
  //            + "t=51 to t=70\n", simpleAnimModel.readBack());
  //  }

  //  @Test
  //  public void generateFrameArray() {
  //    model.generateFrameArray(1);
  //  }

  @Test
  public void maxTime() {
    assertEquals(0, model.maxTime(), .01);
    model.addRectangle("R", 1, 1, 1, 200.0, 200.0, 50, 100);
    model.setTimes("R", 0, 10);
    assertEquals(10, model.maxTime(), .01);
    model.addOval("O", 1, 1, 1, 500.0, 100.0, 60, 30);
    model.setTimes("O", 0, 5);
    assertEquals(10, model.maxTime(), .01);
    model.addRectangle("W", 1, 1, 1, 200.0, 200.0, 50, 100);
    model.setTimes("W", 0, 100);
    assertEquals(100, model.maxTime(), .01);
  }

  @Test
  public void getFrame() {
    model.addRectangle("R", 1, 1, 1, 200.0, 200.0, 50, 100);
    model.setTimes("R", 1, 10);
    model.addMoveAnimation("R", 1, 10, 1, 1, 1, 1);
    model.addRectangle("R2", 1, 1, 1, 200.0, 200.0, 50, 100);
    model.setTimes("R2", 5, 15);
    model.addMoveAnimation("R2", 10, 15, 1, 1, 1, 1);
    model.generateFrameArray(1);
    assertEquals(0, model.getFrame(0).size());
    assertEquals(1, model.getFrame(2).size());
    assertEquals(2, model.getFrame(5).size());
  }

  //  @Test
  //  public void getAnimations() {
  //    assertEquals(5, simpleAnimModel.getAnimations().size());
  //  }

  //  @Test
  //  public void switchView() {
  //  }

  @Test
  public void getFPS() {
    assertEquals(0, model.getFPS(), .01);
    model.setFPS(10);
    assertEquals(10, model.getFPS(), .01);
  }
}