package cs3500.view;

import java.io.FileNotFoundException;

import cs3500.AnimationFileReader;
import cs3500.model.controller.InteractiveController;
import cs3500.model.model.IAnimatableShape;
import cs3500.model.model.IAnimation;
import cs3500.model.model.IReadableShape;
import cs3500.model.model.IWritableModel;
import cs3500.model.model.ModelImpl;
import cs3500.view.AbstractView;
import cs3500.view.IInteractiveView;
import cs3500.view.InteractiveView;

public class RotationTester {
  public static void main(String[] args) {
    IWritableModel model = new ModelImpl();
    model.addRectangle("test", 1, 1, 1, 60, 200, 200, 50, 50);
    model.setTimes("test", 0, 10);
    model.addRotationAnimation("test", 0, 10, 30, 120);
    model.addRectangle("test1", 1, 1, 1, 60, 200, 200, 50, 50);
    model.setTimes("test1", 0, 10);
    model.addRotationAnimation("test1", 0, 10, 30, 120);
    model.generateFrameArray(1);
//    IInteractiveView view = new InteractiveView(null, model.getFrameArray(), model
//                    .getAnimations(), 2, model.getIAnimatableShapes());
    try {
      model = new AnimationFileReader().readFile("resources/input/rotation.txt", ModelImpl
              .Builder.makeBuilder());
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    model.setFPS(10);
    model.generateFrameArray(1);
    IInteractiveView view = (InteractiveView) AbstractView.ViewFactory.makeView("interactive",
            null,
            model.getFrameArray(), model.getAnimations(), model.getFPS(), model
            .getIAnimatableShapes());
//           null,
//            model" +
//            ".getFrameArray" +
//            "(), " +
//            "model)
//            .getAnimations(), 20, model.getIAnimatableShapes());

    InteractiveController controller = new InteractiveController(model, view);
    controller.run(1);
  }
}