package cs3500.pokerpolygons.model.hw04;

import cs3500.pokerpolygons.model.hw02.Card;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw02.PokerTriangles;

/** A class for instantiating objects of the type PokerPolygons. */
public class PokerPolygonsBuilder {
  private GameType type;
  private int sidelen;
  private int width;
  private int height;

  // private int handSize;

  /** Constructor for the class PokerPolygonsBuilder, loaded with default values. */
  public PokerPolygonsBuilder() {
    this.type = null;
    this.sidelen = 5;
    this.width = 5;
    this.height = 5;
  }

  //  public PokerPolygonsBuilder setHandSize(int handSize) {
  //    this.handSize = handSize;
  //    return this;
  //  }

  /**
   * Sets the game type of the model.
   *
   * @param type the type to be used. One of TRI, LOOSE, or RECTANGLE.
   * @return the builder with this modification.
   */
  public PokerPolygonsBuilder setType(GameType type) {
    this.type = type;
    return this;
  }

  /**
   * Sets the side length of the model. Used for TRI and LOOSE game types.
   *
   * @param sideLength the sideLength of the model.
   * @return the builder with this modification.
   */
  public PokerPolygonsBuilder setSideLength(int sideLength) {
    this.sidelen = sideLength;
    return this;
  }

  /**
   * Sets the width of the model. Used for RECTANGLE game types.
   *
   * @param width the width of the model.
   * @return the builder with this modification.
   */
  public PokerPolygonsBuilder setWidth(int width) {
    this.width = width;
    return this;
  }

  /**
   * Sets the height of the model. Used for RECTANGLE game types.
   *
   * @param height the height of the model.
   * @return the builder with this modification.
   */
  public PokerPolygonsBuilder setHeight(int height) {
    this.height = height;
    return this;
  }

  // Don't worry about this. Nothing to see here.
  //  @Test
  //  public void gaming() {
  //    new PokerPolygonsBuilder().setType(GameType.LOOSE).build();
  //  }

  /**
   * Constructs a PokerPolygons model from the described inputs.
   *
   * @param <PlayingCard> The type of card to use in the created model.
   * @return a PokerPolygons model to the current specifications.
   */
  public <PlayingCard extends Card> PokerPolygons<PlayingCard> build() {
    switch (this.type) {
      case RECT:
        PokerRectangles game = new PokerRectangles(this.width, this.height);
        return (PokerPolygons<PlayingCard>) game; // Hmm...
      case TRI:
        PokerTriangles game1 = new PokerTriangles(this.sidelen);
        return (PokerPolygons<PlayingCard>) game1; // Waiter!!
      case LOOSE:
        LoosePokerTriangles game2 = new LoosePokerTriangles(this.sidelen);
        return (PokerPolygons<PlayingCard>) game2; // Check, please.
      default:
        throw new IllegalArgumentException("To build a PokerPolygons, you need a GameType!");
    }
  }

  /**
   * An enumeration representing a game type of PokerPolygons.
   * TRI - A standard triangle game of PokerPolygons.
   * RECT - A standard rectangle game of PokerPolygons.
   * LOOSE - A loosely-scored triangle game of PokerPolygons.
   */
  public enum GameType {
    TRI,
    RECT,
    LOOSE
  }
}
