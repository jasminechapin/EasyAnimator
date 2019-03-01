Model:
Interfaces
IAnimation: each IAnimation is able to output a String describing the animation.
IAnimatableShape: each IAnimatableShape can describe the shape animated start and end time of all animations involved.
IModel: each IModel is able to store, add, and display a representation of all animations used.
IShape: each shape is able to describe its fields

Classes
AbstractShape implements IShape: abstracts common behaviors of IShapes
Oval extends AbstractShape: represents an oval and implements oval only fields/behaviors
Rectangle extends AbstractShape: represents a rectangle and implements rectangle only
fields/behaviors
AnimatableShape implements IAnimatableShape: each AnimatableShape is a shape + animation duration
that can describe itself.
MoveAnimation implements IAnimation: represents a moving animation and uses an IAnimatableShape
ColorAnimation implements IAnimation: represents a color change animation and uses an IAnimatableShape
ScaleAnimation implements IAnimation: represents a dimension changing animation and uses an
IAnimatableShape

ModelImpl implements IModel:
-stores base shapes in a id-IShape hashmap
-stores shapes that can be animated in a id-AnimatableShape hashmap
-stores animations in a list of animations
-can add shapes and animatable shapes to the map by taking in fields. Fields were taken in instead
of an IShape because of decoupling. It   helps with modularity since the IShape is created inside
of the method. This prevents the controller from having to know how a shape
 is represented.
-outputs a string describing each shape that is animated and what animations occur

View:
Interfaces
IView: every type of view has a method for creating a visual drawing panel, text output, returning
its type, setting its fps and if it's repeating. However, not every view supports every operation.
InteractiveView: all interactive views have the ability to set action/change listeners and fire
events. Extends IView.
DrawingPanel: every implementing drawing panel must take in a list of shapes to paint the panel


Classes
AbstractView: Stores in all of the necessary information that subviews can use: the list of
animation, shapes, animatable shapes, the appendable for text output and speed of the animation.
IMPROVEMENT: Less tighly coupled to the model because it no longer needs a reference to one.
Contains the static class ViewFactory for creating subviews.
SVGView: Extends AbstractView. Outputs the code necessary to create a SVG animation***
TextView: Extends AbstractView. Outputs a formatted text document onto the appendable
VisualView: Extends AbstractView. Generates a jpanel that displays the animation visually.
InteractiveView: Implements InteractiveView and extends AbstractView. Like the VisualView, it
generates a visual animation, but within the added components for interactivity. Also exports a SVG
file when prompted.
AbstractDrawingPanel: extends JPanel. Sets the size and layout of a generic drawing
panel
VisualDrawingPanel: extends AbstractDrawingPanel. Draws the shapes given to it by the visual view

Controller:
Interfaces
IController: every implementing class can run the animation

Classes
Controller: taking in a view and a model, starts the timer and relays the information from the model
 to the view. Implements IController
InteractiveController: Implements ActionListener and IController. Listens for the action command
fired from the view buttons/sliders and changes the model, frame count and displayed views
accordingly.

***SVGView improved.
Fixed fill tag. Added "freeze" to maintain the position of a completed animation.
Correctly keeps track of the previous conditions between each tween.