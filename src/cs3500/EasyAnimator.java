package cs3500;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.JOptionPane;

import cs3500.model.controller.Controller;
import cs3500.model.controller.IController;
import cs3500.model.controller.InteractiveController;
import cs3500.model.model.IWritableModel;
import cs3500.model.model.ModelImpl;
import cs3500.view.AbstractView;
import cs3500.view.IInteractiveView;
import cs3500.view.IView;

public final class EasyAnimator {

  /**
   * Parses the command line input and performs the appropriate action (Visual, SVG, Text, or
   * Interactive).
   * @param args the arguments to be parsed for the controller
   */
  public static void main(String[] args) {
    IView view = null;
    IWritableModel model = new ModelImpl();
    String inputFileName = "";
    String viewType = "";
    String outPutFileName = "";
    String speed = "";
    int tps = 0;

    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-if")) {
        inputFileName = args[i + 1];
      } else if (args[i].equals("-iv")) {
        viewType = args[i + 1];
      } else if (args[i].equals("-o")) {
        outPutFileName = args[i + 1];
      } else if (args[i].equals("-speed")) {
        speed = args[i + 1];
      }
    }

    if (inputFileName.equals("")) {
      createErrorMsg("Valid input file required");
    }

    if (speed.equals("")) {
      tps = 1;
    } else {
      try {
        tps = Integer.parseInt(speed);
        if (tps == 0) {
          createErrorMsg("Tick rate cannot be 0");
        }
      }
      catch (NumberFormatException e) {
        createErrorMsg("Invalid tick rate");
      }
    }

    try {
      model = new AnimationFileReader().readFile("resources/input/" + inputFileName, ModelImpl
              .Builder.makeBuilder());
      model.setFPS(Integer.parseInt(speed));
      model.generateFrameArray(1);
    } catch (FileNotFoundException e) {
      createErrorMsg("File not found");
    }

    try {
      Appendable ap;

      if (viewType.equals("text") || viewType.equals("svg") || viewType.equals("visual")
              || viewType.equals("interactive")) {
        if (viewType.equals("visual")) {
          ap = new StringBuilder();
          view = AbstractView.ViewFactory.makeView(viewType, ap, model.getFrameArray(), model
                  .getAnimations(), model.getFPS(), model.getIAnimatableShapes());
          IController controller = new Controller(model, view);
          controller.run(tps);
        }
        else if (viewType.equals("interactive")) {
          ap = null;
          view = AbstractView.ViewFactory.makeView(viewType, ap, model.getFrameArray(), model
                  .getAnimations(), model.getFPS(), model.getIAnimatableShapes());
          InteractiveController controller = new InteractiveController(model, (IInteractiveView)
                  view);
          controller.run(tps);
        }
        else if (outPutFileName.equals("out") || outPutFileName.equals("")) {
          ap = new BufferedWriter(new OutputStreamWriter(System.out));
          view = AbstractView.ViewFactory.makeView(viewType, ap, model.getFrameArray(), model
                  .getAnimations(), model.getFPS(), model.getIAnimatableShapes());
          IController controller = new Controller(model, view);
          controller.run(tps);
        }
        else {
          File output = new File(outPutFileName);
          ap = new BufferedWriter(new FileWriter(output));
          view = AbstractView.ViewFactory.makeView(viewType, ap, model.getFrameArray(), model
                  .getAnimations(), model.getFPS(), model.getIAnimatableShapes());
          IController controller = new Controller(model, view);
          controller.run(tps);
        }
      } else {
        createErrorMsg("Valid view type required");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Creates an error message if there was a problem executing the given command.
   * @param msg the error message to be displayed
   */
  private static void createErrorMsg(String msg) {
    JOptionPane.showMessageDialog(null, msg,"Error", JOptionPane.ERROR_MESSAGE);
    System.exit(1);
  }
}