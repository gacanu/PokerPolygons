package cs3500.pokerpolygons.model.hw02;

/** An interface representing a PlayingCard. */
public interface PlayingCardInterface extends Card {
  String getSuit();

  int getRank();

  int getID();
}
