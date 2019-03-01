package cs3500.view;

import org.junit.Before;
import org.junit.Test;

import cs3500.model.controller.Controller;
import cs3500.model.controller.IController;
import cs3500.model.model.IWritableModel;
import cs3500.model.model.ModelImpl;

import static org.junit.Assert.assertEquals;

public class IViewTest {
  private IWritableModel model;
  private StringBuilder output;
  private IView svgView;
  private IView textView;
  private IController controller;
  private IView interactiveView;

  /**
   * Initializes the model to be used for the tests.
   */
  @Before
  public void setUp() {
    model = new ModelImpl();
    model.addRectangle("R", 100, 30, 20, 200,
            300, 50, 30);
    model.setTimes("R", 0, 20);
    model.addColorAnimation("R", 0, 10, 100, 30, 20,
            45, 20, 4);
  }

  @Test
  public void testSVGViewBuilderType() {
    model.generateFrameArray(1);
    model.setFPS(2);
    output = new StringBuilder();
    svgView = new AbstractView.ViewFactory().makeView("svg", output, model.getFrameArray(),
            model.getAnimations(), model.getFPS(), model.getIAnimatableShapes());
    controller = new Controller(model,svgView);
    controller.run(1);
    assertEquals(svgView.getViewType(), "svg");
  }

  @Test
  public void testSVGViewBuilderOutput() {
    model.generateFrameArray(1);
    model.setFPS(2);
    output = new StringBuilder();
    svgView = new AbstractView.ViewFactory().makeView("svg", output, model.getFrameArray(),
            model.getAnimations(), model.getFPS(), model.getIAnimatableShapes());
    controller = new Controller(model,svgView);
    controller.run(model.getFPS());
    assertEquals("<svg width=\"1600\" height=\"1600\" version=\"1.1\" xmlns=\"http://" +
            "www.w3.org/2000/svg\">\n" +
            "    <rect id=\"R\" x=\"200.0\" y=\"300.0\" width=\"50.0\" height=\"30.0\" fill=\"" +
            "rgb(100.0,30.0,20.0)\" visibility=\"visible\">\n" +
            "        <animate attributeType= \"xml\" begin=\"0.0s\" dur=\"5.0s\" attributeName" +
            "=\"fill\" from=\"rgb(100.0,30.0,20.0)\" to=\"rgb(45.0,20.0,4.0)\" " +
            " fill=\"freeze\"/>\n" +
            "    </rect>\n" +
            "</svg>\n", output.toString());
  }

  //no animation on second shape
  @Test
  public void testSVGViewBuilderOuptut2() {
    output = new StringBuilder();
    model.addOval("O", 2, 10, 4, 20, 10, 10, 2);
    model.setTimes("O", 3, 9);
    model.generateFrameArray(1);
    model.setFPS(2);
    svgView = new AbstractView.ViewFactory().makeView("svg", output, model.getFrameArray(),
            model.getAnimations(), model.getFPS(), model.getIAnimatableShapes());
    controller = new Controller(model,svgView);
    controller.run(1);
    assertEquals("<svg width=\"1600\" height=\"1600\" version=\"1.1\" xmlns=\"http:" +
            "//www.w3.org/2000/svg\">\n" + "    <rect id=\"R\" x=\"200.0\" y=\"300.0\" " +
            "width=\"50.0\" height=\"30.0\" fill=\"rgb(100.0,30.0,20.0)\" visibility=\"" +
            "visible\">\n" + "        <animate attributeType= \"xml\" begin=\"0.0s\" dur=\"" +
            "5.0s\" attributeName=\"fill\" from=\"rgb(100.0,30.0,20.0)\" to=\"rgb(45.0,20.0," +
            "4.0)\"  fill=\"freeze\"/>\n" + "    </rect>\n" + "    <ellipse id=\"O\" cx=\"20" +
            ".0\" cy=\"10.0\" rx=\"10.0\" ry=\"2.0\" fill=\"rgb(2.0,10.0,4.0)\" visibilit" +
            "y=\"visible\">\n" + "    </ellipse>\n" + "</svg>\n", output.toString());
  }

  //two shapes, both animated
  @Test
  public void testSVGViewBuilderOutput3() {
    output = new StringBuilder();
    model.addOval("O", 2, 10, 4, 20, 10, 10, 2);
    model.setTimes("O", 3, 9);
    model.addMoveAnimation("O", 4, 6, 20, 10, 40, 8);
    model.generateFrameArray(1);
    model.setFPS(2);
    svgView = new AbstractView.ViewFactory().makeView("svg", output, model.getFrameArray(),
            model.getAnimations(), model.getFPS(), model.getIAnimatableShapes());
    controller = new Controller(model,svgView);
    controller.run(1);
    assertEquals("<svg width=\"1600\" height=\"1600\" version=\"1.1\" xmlns=\"http:" +
            "//www.w3.org/2000/svg\">\n" + "    <rect id=\"R\" x=\"200.0\" y=\"300.0\" width=" +
            "\"50.0\" height=\"30.0\" fill=\"rgb(100.0,30.0,20.0)\" visibility=\"visible\">\n" +
            "        <animate attributeType= \"xml\" begin=\"0.0s\" dur=\"5.0s\" attributeName=" +
            "\"fill\" from=\"rgb(100.0,30.0,20.0)\" to=\"rgb(45.0,20.0,4.0)\"  fill=\"freeze\"/>\n"
            + "    </rect>\n" + "    <ellipse id=\"O\" cx=\"20.0\" cy=\"10.0\" rx=\"10.0\"" +
            " ry=\"2.0\" fill=\"rgb(2.0,10.0,4.0)\" visibility=\"visible\">\n" + "        " +
            "<animate attributeType= \"xml\" begin=\"2.0s\" dur=\"1.0s\" attributeName=" +
            "\"cx\" from=\"20.0\" to=\"40.0\" fill=\"freeze\"/>\n" + "        " +
            "<animate attributeType= \"xml\" begin=\"2.0s\" dur=\"1.0s\" attributeName=" +
            "\"cy\" from=\"10.0\" to=\"8.0\" fill=\"freeze\"/>\n" + "    </ellipse>\n" +
            "</svg>\n", output.toString());
  }

  @Test
  public void testTextViewBuilderType() {
    output = new StringBuilder();
    textView = new AbstractView.ViewFactory().makeView("text", output, model.getFrameArray(),
            model.getAnimations(), model.getFPS(), model.getIAnimatableShapes());
    controller = new Controller(model,textView);
    controller.run(1);
    assertEquals(textView.getViewType(), "text");
  }

  //one shape, one animation
  @Test
  public void testTextViewBuilderOutput() {
    output = new StringBuilder();
    model.generateFrameArray(1);
    model.setFPS(2);
    textView = new AbstractView.ViewFactory().makeView("text", output, model.getFrameArray(),
            model.getAnimations(), model.getFPS(), model.getIAnimatableShapes());
    controller = new Controller(model,textView);
    controller.run(1);
    assertEquals("Name: R\n" +
            "Type: rect\n" +
            "Corner: (200.0,300.0), Width: 50.0, Height: 30.0, Color: (100.0,30.0,20.0)\n" +
            "Appears at t=0.0s\n" +
            "Disappears at t=10.0s\n" +
            "\n" +
            "Shape R changes color from (100.0,30.0,20.0) to (45.0,20.0,4.0) from t=0.0s to " +
            "t=5.0s\n" + "\n", output.toString());
  }

  //no animation on second shape
  @Test
  public void testTextViewBuilderOuptut2() {
    output = new StringBuilder();
    model.addOval("O", 2, 10, 4, 20, 10, 10, 2);
    model.setTimes("O", 3, 9);
    model.generateFrameArray(1);
    model.setFPS(2);
    textView = new AbstractView.ViewFactory().makeView("text", output, model.getFrameArray(),
            model.getAnimations(), model.getFPS(), model.getIAnimatableShapes());
    controller = new Controller(model,textView);
    controller.run(1);
    assertEquals("Name: R\n" +
            "Type: rect\n" +
            "Corner: (200.0,300.0), Width: 50.0, Height: 30.0, Color: (100.0,30.0,20.0)\n" +
            "Appears at t=0.0s\n" +
            "Disappears at t=10.0s\n" +
            "\n" +
            "Name: O\n" +
            "Type: ellipse\n" +
            "Center: (20.0,10.0), X radius: 10.0, Y radius: 2.0, Color: (2.0,10.0,4.0)\n" +
            "Appears at t=1.5s\n" +
            "Disappears at t=4.5s\n" +
            "\n" +
            "Shape R changes color from (100.0,30.0,20.0) to (45.0,20.0,4.0) from t=0.0s to " +
            "t=5.0s\n" + "\n", output.toString());
  }

  //two shapes, both animated
  @Test
  public void testTextViewBuilderOutput3() {
    output = new StringBuilder();
    model.addOval("O", 2, 10, 4, 20, 10, 10, 2);
    model.setTimes("O", 3, 9);
    model.addMoveAnimation("O", 4, 6, 20, 10, 40, 8);
    model.generateFrameArray(1);
    model.setFPS(2);
    textView = new AbstractView.ViewFactory().makeView("text", output, model.getFrameArray(),
            model.getAnimations(), model.getFPS(), model.getIAnimatableShapes());
    controller = new Controller(model,textView);
    controller.run(1);
    assertEquals("Name: R\n" +
            "Type: rect\n" +
            "Corner: (200.0,300.0), Width: 50.0, Height: 30.0, Color: (100.0,30.0,20.0)\n" +
            "Appears at t=0.0s\n" +
            "Disappears at t=10.0s\n" +
            "\n" +
            "Name: O\n" +
            "Type: ellipse\n" +
            "Center: (20.0,10.0), X radius: 10.0, Y radius: 2.0, Color: (2.0,10.0,4.0)\n" +
            "Appears at t=1.5s\n" +
            "Disappears at t=4.5s\n" +
            "\n" +
            "Shape R changes color from (100.0,30.0,20.0) to (45.0,20.0,4.0) from t=0.0s to " +
            "t=5.0s\n" + "Shape O moves from (20.0,10.0) to (40.0,8.0) from t=2.0s to t=3.0s\n" +
            "\n", output.toString());
  }

  //two shapes, one animated twice
  @Test
  public void testTextAnimatedTwice() {
    output = new StringBuilder();
    model.addOval("O", 2, 10, 4, 20, 10, 10, 2);
    model.setTimes("O", 3, 9);
    model.addMoveAnimation("O", 4, 9, 20, 10, 40, 8);
    model.addScaleAnimation("O", 7, 9, 10, 2, 10,
            4);
    model.generateFrameArray(1);
    model.setFPS(2);
    textView = new AbstractView.ViewFactory().makeView("text", output, model.getFrameArray(),
            model.getAnimations(), model.getFPS(), model.getIAnimatableShapes());
    controller = new Controller(model,textView);
    controller.run(1);
    assertEquals("Name: R\n" +
            "Type: rect\n" +
            "Corner: (200.0,300.0), Width: 50.0, Height: 30.0, Color: (100.0,30.0,20.0)\n" +
            "Appears at t=0.0s\n" +
            "Disappears at t=10.0s\n" +
            "\n" +
            "Name: O\n" +
            "Type: ellipse\n" +
            "Center: (20.0,10.0), X radius: 10.0, Y radius: 2.0, Color: (2.0,10.0,4.0)\n" +
            "Appears at t=1.5s\n" +
            "Disappears at t=4.5s\n" +
            "\n" +
            "Shape R changes color from (100.0,30.0,20.0) to (45.0,20.0,4.0) from t=0.0s to " +
            "t=5.0s\n" +
            "Shape O moves from (20.0,10.0) to (40.0,8.0) from t=2.0s to t=4.5s\n" +
            "Shape O scales from X radius: 10.0, Y radius: 2.0 to X radius: 10.0, Y radius: 4.0 " +
            "from t=3.5s to t=4.5s\n" + "\n", output.toString());
  }

  @Test
  public void testVisualViewBuilderType() {
    output = new StringBuilder();
    IView visualView = new AbstractView.ViewFactory().makeView("visual", output, model
                    .getFrameArray(), model.getAnimations(), model.getFPS(), model
            .getIAnimatableShapes());
    controller = new Controller(model,visualView);
    controller.run(1);
    assertEquals(visualView.getViewType(), "visual");
  }

  @Test
  public void testInteractiveGViewBuilderType() {
    model.generateFrameArray(1);
    model.setFPS(2);
    output = new StringBuilder();
    interactiveView = new AbstractView.ViewFactory().makeView("interactive", output, model
                    .getFrameArray(),
            model.getAnimations(), model.getFPS(), model.getIAnimatableShapes());
    controller = new Controller(model,interactiveView); controller.run(model.getFPS());
    controller.run(1);
    assertEquals(interactiveView.getViewType(), "interactive");
  }

  @Test
  public void testInteractiveViewBuilderOutput() {
    output = new StringBuilder();
    model.generateFrameArray(1);
    model.setFPS(2);
    interactiveView = new AbstractView.ViewFactory().makeView("interactive", output, model
                    .getFrameArray(),
            model.getAnimations(), model.getFPS(), model.getIAnimatableShapes());
    controller = new Controller(model,interactiveView);
    controller.run(model.getFPS());
    assertEquals("<svg width=\"1600\" height=\"1600\" version=\"1.1\" xmlns=\"http://" +
            "www.w3.org/2000/svg\">\n" +
            "    <rect id=\"R\" x=\"200.0\" y=\"300.0\" width=\"50.0\" height=\"30.0\" fill=\"" +
            "rgb(100.0,30.0,20.0)\" visibility=\"visible\">\n" +
            "        <animate attributeType= \"xml\" begin=\"0.0s\" dur=\"5.0s\" attributeName" +
            "=\"fill\" from=\"rgb(100.0,30.0,20.0)\" to=\"rgb(45.0,20.0,4.0)\" " +
            " fill=\"freeze\"/>\n" +
            "    </rect>\n" +
            "</svg>\n", output.toString());
  }

  //no animation on second shape
  @Test
  public void testInteractiveViewBuilderOuptut2() {
    output = new StringBuilder();
    model.addOval("O", 2, 10, 4, 20, 10, 10, 2);
    model.setTimes("O", 3, 9);
    model.generateFrameArray(1);
    model.setFPS(2);
    interactiveView = new AbstractView.ViewFactory().makeView("interactive", output, model
                    .getFrameArray(),
            model.getAnimations(), model.getFPS(), model.getIAnimatableShapes());
    controller = new Controller(model,interactiveView);
    controller.run(1);
    assertEquals("<svg width=\"1600\" height=\"1600\" version=\"1.1\" xmlns=\"http:" +
            "//www.w3.org/2000/svg\">\n" + "    <rect id=\"R\" x=\"200.0\" y=\"300.0\" " +
            "width=\"50.0\" height=\"30.0\" fill=\"rgb(100.0,30.0,20.0)\" visibility=\"" +
            "visible\">\n" + "        <animate attributeType= \"xml\" begin=\"0.0s\" dur=\"" +
            "5.0s\" attributeName=\"fill\" from=\"rgb(100.0,30.0,20.0)\" to=\"rgb(45.0,20.0," +
            "4.0)\"  fill=\"freeze\"/>\n" + "    </rect>\n" + "    <ellipse id=\"O\" cx=\"20" +
            ".0\" cy=\"10.0\" rx=\"10.0\" ry=\"2.0\" fill=\"rgb(2.0,10.0,4.0)\" visibilit" +
            "y=\"visible\">\n" + "    </ellipse>\n" + "</svg>\n", output.toString());
  }

  //two shapes, both animated
  @Test
  public void testInteractiveViewBuilderOutput3() {
    output = new StringBuilder();
    model.addOval("O", 2, 10, 4, 20, 10, 10, 2);
    model.setTimes("O", 3, 9);
    model.addMoveAnimation("O", 4, 6, 20, 10, 40, 8);
    model.generateFrameArray(1);
    model.setFPS(2);
    interactiveView = new AbstractView.ViewFactory().makeView("interactive", output, model
                    .getFrameArray(),
            model.getAnimations(), model.getFPS(), model.getIAnimatableShapes());
    controller = new Controller(model,interactiveView);
    controller.run(1);
    assertEquals("<svg width=\"1600\" height=\"1600\" version=\"1.1\" xmlns=\"http:" +
            "//www.w3.org/2000/svg\">\n" + "    <rect id=\"R\" x=\"200.0\" y=\"300.0\" width=" +
            "\"50.0\" height=\"30.0\" fill=\"rgb(100.0,30.0,20.0)\" visibility=\"visible\">\n" +
            "        <animate attributeType= \"xml\" begin=\"0.0s\" dur=\"5.0s\" attributeName=" +
            "\"fill\" from=\"rgb(100.0,30.0,20.0)\" to=\"rgb(45.0,20.0,4.0)\"  fill=\"freeze\"/>\n"
            + "    </rect>\n" + "    <ellipse id=\"O\" cx=\"20.0\" cy=\"10.0\" rx=\"10.0\"" +
            " ry=\"2.0\" fill=\"rgb(2.0,10.0,4.0)\" visibility=\"visible\">\n" + "        " +
            "<animate attributeType= \"xml\" begin=\"2.0s\" dur=\"1.0s\" attributeName=" +
            "\"cx\" from=\"20.0\" to=\"40.0\" fill=\"freeze\"/>\n" + "        " +
            "<animate attributeType= \"xml\" begin=\"2.0s\" dur=\"1.0s\" attributeName=" +
            "\"cy\" from=\"10.0\" to=\"8.0\" fill=\"freeze\"/>\n" + "    </ellipse>\n" +
            "</svg>\n", output.toString());
  }

  @Test
  public void testInteractiveViewBuilderOutputLooping() {
    output = new StringBuilder();
    model.generateFrameArray(1);
    model.setFPS(2);
    interactiveView = new AbstractView.ViewFactory().makeView("interactive", output, model
                    .getFrameArray(),
            model.getAnimations(), model.getFPS(), model.getIAnimatableShapes());
    interactiveView.setIsRepeating(true);
    controller = new Controller(model,interactiveView);
    controller.run(model.getFPS());
    assertEquals("<svg width=\"1600\" height=\"1600\" version=\"1.1\" xmlns=\"http://ww" +
            "w.w3.org/2000/svg\">\n" +
            " <rect>\n" +
            "        <animate id=\"base\" begin=\"0;base.end\" dur=\"10.0s\" attributeName=\"" +
            "visibility\" from=\"hide\"\n" +
            "                 to=\"hide\"/>\n" +
            "    </rect>\n" +
            "    <rect id=\"R\" x=\"200.0\" y=\"300.0\" width=\"50.0\" height=\"30.0\" fill=\"r" +
            "gb(100.0,30.0,20.0)\" visibility=\"visible\">\n" +
            "        <animate attributeType= \"xml\" begin=\"0.0;base.end+0.0\" dur=\"5.0s\" " +
            "attributeName=\"fill\" from=\"rgb(100.0,30.0,20.0)\" to=\"rgb(45.0,20.0,4.0)\"  " +
            "fill=\"freeze\"/>\n" +
            "        <animate attributeType=\"xml\" begin=\"base.begin\" dur=\"0.0\" attribute" +
            "Name=\"fill\" from=\"rgb(100.0,30.0,20.0)\" to=\"rgb(100.0,30.0,20.0)\" fill=\"f" +
            "reeze\"/>\n" +
            "    </rect>\n" +
            "</svg>\n", output.toString());
  }

  //no animation on second shape
  @Test
  public void testInteractiveViewBuilderOuptutLooping2() {
    output = new StringBuilder();
    model.addOval("O", 2, 10, 4, 20, 10, 10, 2);
    model.setTimes("O", 3, 9);
    model.generateFrameArray(1);
    model.setFPS(2);
    interactiveView = new AbstractView.ViewFactory().makeView("interactive", output, model
                    .getFrameArray(),
            model.getAnimations(), model.getFPS(), model.getIAnimatableShapes());
    interactiveView.setIsRepeating(true);
    controller = new Controller(model,interactiveView);
    controller.run(1);
    assertEquals("<svg width=\"1600\" height=\"1600\" version=\"1.1\" xmlns=\"htt" +
            "p://www.w3.org/2000/svg\">\n" +
            " <rect>\n" +
            "        <animate id=\"base\" begin=\"0;base.end\" dur=\"10.0s\" attributeName" +
            "=\"visibility\" from=\"hide\"\n" +
            "                 to=\"hide\"/>\n" +
            "    </rect>\n" +
            "    <rect id=\"R\" x=\"200.0\" y=\"300.0\" width=\"50.0\" height=\"30.0\" " +
            "fill=\"rgb(100.0,30.0,20.0)\" visibility=\"visible\">\n" +
            "        <animate attributeType= \"xml\" begin=\"0.0;base.end+0.0\" dur=\"5.0s\" " +
            "attributeName=\"fill\" from=\"rgb(100.0,30.0,20.0)\" to=\"rgb(45.0,20.0,4.0)\"  " +
            "fill=\"freeze\"/>\n" +
            "        <animate attributeType=\"xml\" begin=\"base.begin\" dur=\"0.0\" " +
            "attributeName=\"fill\" from=\"rgb(100.0,30.0,20.0)\" to=\"rgb(100.0,30.0,20.0)\" " +
            "fill=\"freeze\"/>\n" +
            "    </rect>\n" +
            "    <ellipse id=\"O\" cx=\"20.0\" cy=\"10.0\" rx=\"10.0\" ry=\"2.0\" fill=\"rgb" +
            "(2.0,10.0,4.0)\" visibility=\"visible\">\n" +
            "        <animate attributeType=\"xml\" begin=\"base.begin\" dur=\"0.0\" " +
            "attributeName=\"fill\" from=\"rgb(2.0,10.0,4.0)\" to=\"rgb(2.0,10.0,4.0)\" " +
            "fill=\"freeze\"/>\n" +
            "    </ellipse>\n" +
            "</svg>\n", output.toString());
  }

  //two shapes, both animated
  @Test
  public void testInteractiveViewBuilderOutputLooping3() {
    output = new StringBuilder();
    model.addOval("O", 2, 10, 4, 20, 10, 10, 2);
    model.setTimes("O", 3, 9);
    model.addMoveAnimation("O", 4, 6, 20, 10, 40, 8);
    model.generateFrameArray(1);
    model.setFPS(2);
    interactiveView = new AbstractView.ViewFactory().makeView("interactive", output, model
                    .getFrameArray(),
            model.getAnimations(), model.getFPS(), model.getIAnimatableShapes());
    interactiveView.setIsRepeating(true);
    controller = new Controller(model,interactiveView);
    controller.run(1);
    assertEquals("<svg width=\"1600\" height=\"1600\" version=\"1.1\" xmlns=\"http://" +
            "www.w3.org/2000/svg\">\n" +
            " <rect>\n" +
            "        <animate id=\"base\" begin=\"0;base.end\" dur=\"10.0s\" attribute" +
            "Name=\"visibility\" from=\"hide\"\n" +
            "                 to=\"hide\"/>\n" +
            "    </rect>\n" +
            "    <rect id=\"R\" x=\"200.0\" y=\"300.0\" width=\"50.0\" height=\"30.0\" fill=\"" +
            "rgb(100.0,30.0,20.0)\" visibility=\"visible\">\n" +
            "        <animate attributeType= \"xml\" begin=\"0.0;base.end+0.0\" dur=\"5.0s\"" +
            " attributeName=\"fill\" from=\"rgb(100.0,30.0,20.0)\" to=\"rgb(45.0,20.0,4." +
            "0)\"  fill=\"freeze\"/>\n" +
            "        <animate attributeType=\"xml\" begin=\"base.begin\" dur=\"0.0\" att" +
            "ributeName=\"fill\" from=\"rgb(100.0,30.0,20.0)\" to=\"rgb(100.0,30.0,20.0)\"" +
            " fill=\"freeze\"/>\n" +
            "    </rect>\n" +
            "    <ellipse id=\"O\" cx=\"20.0\" cy=\"10.0\" rx=\"10.0\" ry=\"2.0\" fil" +
            "l=\"rgb(2.0,10.0,4.0)\" visibility=\"visible\">\n" +
            "        <animate attributeType= \"xml\" begin=\"2.0;base.end+2.0\" dur=" +
            "\"1.0s\" attributeName=\"cx\" from=\"20.0\" to=\"40.0\" fill=\"freeze\"/>\n" +
            "        <animate attributeType= \"xml\" begin=\"2.0;base.end+2.0\" dur=\"" +
            "1.0s\" attributeName=\"cy\" from=\"10.0\" to=\"8.0\" fill=\"freeze\"/>\n" +
            "        <animate attributeType=\"xml\" begin=\"base.begin\" dur=\"3.0\" " +
            "attributeName=\"fill\" from=\"rgb(2.0,10.0,4.0)\" to=\"rgb(2.0,10.0,4.0)\" " +
            "fill=\"freeze\"/>\n" +
            "        <animate attributeType=\"xml\" begin=\"base.begin\" dur=\"3.0\" " +
            "attributeName=\"cx\" from=\"20.0\" to=\"20.0\" fill=\"freeze\"/>\n" +
            "        <animate attributeType=\"xml\" begin=\"base.begin\" dur=\"3.0\" " +
            "attributeName=\"cy\" from=\"10.0\" to=\"10.0\" fill=\"freeze\"/>\n" +
            "    </ellipse>\n" +
            "</svg>\n", output.toString());
  }

  @Test
  public void testInteractiveViewBuilderOutputLooping4() {
    output = new StringBuilder();
    model.addOval("O", 2, 10, 4, 20, 10, 10, 2);
    model.setTimes("O", 3, 9);
    model.addMoveAnimation("O", 4, 6, 20, 10, 40, 8);
    model.setFPS(4);
    model.generateFrameArray(1);
    interactiveView = new AbstractView.ViewFactory().makeView("interactive", output, model
                    .getFrameArray(),
            model.getAnimations(), model.getFPS(), model.getIAnimatableShapes());
    interactiveView.setIsRepeating(true);
    controller = new Controller(model,interactiveView);
    controller.run(1);
    assertEquals("<svg width=\"1600\" height=\"1600\" version=\"1.1\" xmlns=\"http" +
            "://www.w3.org/2000/svg\">\n" +
            " <rect>\n" +
            "        <animate id=\"base\" begin=\"0;base.end\" dur=\"5.0s\" attributeName=" +
            "\"visibility\" from=\"hide\"\n" +
            "                 to=\"hide\"/>\n" +
            "    </rect>\n" +
            "    <rect id=\"R\" x=\"200.0\" y=\"300.0\" width=\"50.0\" height=\"30.0\" fill" +
            "=\"rgb(100.0,30.0,20.0)\" visibility=\"visible\">\n" +
            "        <animate attributeType= \"xml\" begin=\"0.0;base.end+0.0\" dur=\"2.5s\"" +
            " attributeName=\"fill\" from=\"rgb(100.0,30.0,20.0)\" to=\"rgb(45.0,20.0,4.0)\" " +
            " fill=\"freeze\"/>\n" +
            "        <animate attributeType=\"xml\" begin=\"base.begin\" dur=\"0.0\" attr" +
            "ibuteName=\"fill\" from=\"rgb(100.0,30.0,20.0)\" to=\"rgb(100.0,30.0,20.0)\" " +
            "fill=\"freeze\"/>\n" +
            "    </rect>\n" +
            "    <ellipse id=\"O\" cx=\"20.0\" cy=\"10.0\" rx=\"10.0\" ry=\"2.0\" fill" +
            "=\"rgb(2.0,10.0,4.0)\" visibility=\"visible\">\n" +
            "        <animate attributeType= \"xml\" begin=\"1.0;base.end+1.0\" dur=\"" +
            "0.5s\" attributeName=\"cx\" from=\"20.0\" to=\"40.0\" fill=\"freeze\"/>\n" +
            "        <animate attributeType= \"xml\" begin=\"1.0;base.end+1.0\" dur=\"" +
            "0.5s\" attributeName=\"cy\" from=\"10.0\" to=\"8.0\" fill=\"freeze\"/>\n" +
            "        " +
            "<animate attributeType=\"xml\" begin=\"base.begin\" dur=\"3.0\" " +
            "attributeName=\"fill\" from=\"rgb(2.0,10.0,4.0)\" to=\"rgb(2.0,10.0,4." +
            "0)\" fill=\"freeze\"/>\n" +
            "        <animate attributeType=\"xml\" begin=\"base.begin\" dur=\"3.0\"" +
            " attributeName=\"cx\" from=\"20.0\" to=\"20.0\" fill=\"freeze\"/>\n" +
            "        <animate attributeType=\"xml\" begin=\"base.begin\" dur=\"3.0\" " +
            "attributeName=\"cy\" from=\"10.0\" to=\"10.0\" fill=\"freeze\"/>\n" +
            "    </ellipse>\n" +
            "</svg>\n", output.toString());
  }
}