package cs3500.pokerpolygons.controller;

import cs3500.pokerpolygons.model.hw02.Card;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.view.PokerPolygonsTextualView;
import java.util.List;

/** An interface representing a controller for a game of PokerPolygons. */
public interface PokerPolygonsController {
  /**
   * Plays a game with the given model and deck, and shows that to the given view.
   *
   * @param model what game model to play.
   * @param view where to export visual info.
   * @param deck which deck to use for a source of cards.
   * @param shuffle whether to shuffle the deck.
   * @param handSize size of your hand.
   * @param <C> Which kind of card to use.
   */
  <C extends Card> void playGame(
      PokerPolygons<C> model,
      PokerPolygonsTextualView view,
      List<C> deck,
      boolean shuffle,
      int handSize);
}
