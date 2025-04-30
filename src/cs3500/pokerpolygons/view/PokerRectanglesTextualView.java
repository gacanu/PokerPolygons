package cs3500.pokerpolygons.view;

import static java.util.Objects.nonNull;

import cs3500.pokerpolygons.model.hw02.Card;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;

/** A class for a visual representation of the class PokerRectanglesTextualView through text. */
public class PokerRectanglesTextualView extends AbstractPokerPolygonsTextualView
    implements PokerPolygonsTextualView {
  private final PokerPolygons<?> model;

  /**
   * Creates a PokerRectanglesTextualView given a PokerPolygonsModel.
   *
   * @param model the model to be represented visually.
   */
  public PokerRectanglesTextualView(PokerPolygons<?> model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }
    this.model = model;
  }

  /**
   * Returns a string representation of this textual view.
   *
   * @return a string representation of this textual view.
   */
  public String toString() {
    StringBuilder s = new StringBuilder();
    boolean first = true;
    for (int i = 0; i < model.getHeight(); i++) {
      for (int j = 0; j < model.getWidth(); j++) {
        if (nonNull(model.getCardAt(i, j))) {
          s.append(space(model.getCardAt(i, j).toString()) + model.getCardAt(i, j));
          if (j != model.getWidth() - 1) {
            s.append(" ");
          }
        } else {
          if (j == model.getWidth() - 1) {
            s.append(" __");
          } else {
            s.append(" __ ");
          }
        }
      }
      s.append("\n");
    }
    s.append("Deck: " + model.getRemainingDeckSize() + "\n");
    s.append("Hand: ");
    for (Card c : model.getHand()) {
      if (first) {
        s.append(c.toString());
        first = false;
      } else {
        s.append(", " + c.toString());
      }
    }
    // System.out.println(s);
    return s.toString();
  }
}
