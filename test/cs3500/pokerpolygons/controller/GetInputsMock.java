package cs3500.pokerpolygons.controller;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import java.util.List;

/** Mock class for testing the class PokerPolygonsController. */
public class GetInputsMock implements PokerPolygons<PlayingCard> {
  private final StringBuilder log;
  private final int totalSize;
  private int placedSoFar;
  private boolean won;
  private Type type;

  /**
   * Constructor for the class GetInputsMock.
   *
   * @param log the log which to append to.
   * @param totalSize total amount of card slots in the triangle.
   */
  public GetInputsMock(StringBuilder log, int totalSize) {
    if (log == null || totalSize < 1) {
      throw new IllegalArgumentException("Invalid inputs, goober! >:-(");
    }
    this.log = log;
    this.totalSize = totalSize;
    this.placedSoFar = 0;
    this.won = false;
  }

  /**
   * Displays a log of inputs corresponding with what would happen in a normal game.
   *
   * @param cardIdx index of the card in hand to place (0-index based)
   * @param row row to place the card in (0-index based)
   * @param col column to place the card in (0-index based)
   */
  @Override
  public void placeCardInPosition(int cardIdx, int row, int col) {
    // System.out.println(cardIdx + ", " + row + ", " + col);
    //    if (row < -1 || col < -1 || cardIdx < -1) { This never gets called...
    //      log.append(
    //              "Tried to grab card from cardIdx "
    //                      + cardIdx
    //                      + " and put it in row "
    //                      + row
    //                      + ", col "
    //                      + col
    //                      + ", but the inputs were invalid. Reading for input again.\n");
    //      return;
    //    }
    if (row <= -1 || col <= -1) {
      throw new IllegalStateException();
    }
    this.placedSoFar += 1;
    log.append(
        "Grabbed card from index "
            + cardIdx
            + " and put it in row "
            + row
            + ", col "
            + col
            + ".\n");
  }

  /**
   * Displays a log of inputs corresponding with what would happen in a normal game.
   *
   * @param cardIdx index of the card in hand to discard (0-index based)
   */
  @Override
  public void discardCard(int cardIdx) {
    log.append("Discarded card in position " + cardIdx + ".\n");
  }

  /**
   * Displays a log of inputs corresponding with what would happen in a normal game.
   *
   * @param deck list of cards to play the game with
   * @param shuffle whether the deck should be shuffled
   * @param handSize maximum hand size for the game
   */
  @Override
  public void startGame(List deck, boolean shuffle, int handSize) {
    if (shuffle) {
      log.append(
          "Started a game with the given deck, shuffled, with a hand size of " + handSize + ".\n");
    } else {
      log.append(
          "Started a game with the given deck, unshuffled, with a hand size of "
              + handSize
              + ".\n");
    }
  }

  /**
   * Does nothing. This method is unnecessary for mock purposes.
   *
   * @return nothing; this is a stub.
   */
  @Override
  public int getWidth() {
    // log.append("Grabbed the width (" + this.side + ") of the current triangle.\n");
    return 0;
  }

  /**
   * Does nothing. This method is unnecessary for mock purposes.
   *
   * @return nothing; this is a stub.
   */
  @Override
  public int getHeight() {
    // log.append("Grabbed the height (" + this.side + ") of the current triangle.\n");
    return 0;
  }

  /**
   * Displays a log of inputs corresponding with what would happen in a normal game.
   *
   * @return nothing; this is a stub.
   */
  @Override
  public List getNewDeck() {
    log.append("Grabbed the deck of the current game.\n");
    return List.of(1);
  }

  /**
   * Does nothing. This method is unnecessary for mock purposes.
   *
   * @param row the row to access
   * @param col the column to access
   * @return nothing; this is a stub.
   */
  @Override
  public PlayingCard getCardAt(int row, int col) {
    // log.append("Grabbed the card at row " + row + ", col " + col + ".\n");
    return null;
  }

  /**
   * Does nothing. This method is unnecessary for mock purposes.
   *
   * @return nothing; this is a stub.
   */
  @Override
  public List getHand() {
    // log.append("Grabbed the hand.\n");
    return List.of();
  }

  /**
   * Does nothing. This method is unnecessary for mock purposes.
   *
   * @return nothing; this is a stub.
   */
  @Override
  public int getScore() {
    // log.append("Grabbed the score.\n");
    return 0;
  }

  /**
   * Does nothing. This method is unnecessary for mock purposes.
   *
   * @return nothing; this is a stub.
   */
  @Override
  public int getRemainingDeckSize() {
    // log.append("Grabbed the remaining deck size.\n");
    return 0;
  }

  /**
   * Displays a log of inputs corresponding with what would happen in a normal game.
   *
   * @return if the game has ended.
   */
  @Override
  public boolean isGameOver() {
    if (this.placedSoFar >= this.totalSize) {
      if (!this.won) {
        log.append("And the game ends!\n");
        this.won = true;
      }
      return true;
    }
    return false;
  }

  private enum Type {
    TRI,
    LOOSE,
    RECT
  }
}
