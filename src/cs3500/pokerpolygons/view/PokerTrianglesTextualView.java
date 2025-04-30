package cs3500.pokerpolygons.view;

import static java.util.Objects.nonNull;

import cs3500.pokerpolygons.model.hw02.Card;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;

/** A class representing a text-based view of a game of PokerTriangles. */
public class PokerTrianglesTextualView extends AbstractPokerPolygonsTextualView
    implements PokerPolygonsTextualView {
  private final PokerPolygons<?> model;

  /**
   * A constructor to make PokerTrianglesTextualView, with a game model.
   *
   * @param model The current game model, of type PokerPolygons.
   * @throws IllegalArgumentException if the model is null.
   */
  public PokerTrianglesTextualView(PokerPolygons<?> model) {
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
    for (int i = 0; i < model.getWidth(); i++) {
      for (int j = 0; j <= i; j++) {
        if (nonNull(model.getCardAt(i, j))) {
          s.append(space(model.getCardAt(i, j).toString()) + model.getCardAt(i, j));
          if (j != i) {
            s.append(" ");
          }
        } else {
          if (i == j) {
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
