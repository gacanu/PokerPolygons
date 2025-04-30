package cs3500.pokerpolygons.controller;

import cs3500.pokerpolygons.model.hw02.Card;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.view.PokerPolygonsTextualView;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/** A class representing a controller for a game of PokerPolygons. */
public class PokerPolygonsTextualController implements PokerPolygonsController {
  private final Appendable out;
  private final Scanner s;
  private boolean quit;
  private boolean stop;

  /**
   * A text-based controller for a game of PokerPolygons.
   *
   * @param in the text based object to read from.
   * @param out the text based object to append to for output.
   * @param <C> the card object to use.
   * @throws IllegalArgumentException if in or out are null.
   */
  public <C extends Card> PokerPolygonsTextualController(Readable in, Appendable out)
      throws IllegalArgumentException {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Appendable and Readable cannot be null!");
    }
    this.out = out;
    this.s = new Scanner(in);
    this.quit = false;
    this.stop = false;
  }

  /**
   * A text-based controller for a game of PokerPolygons.
   *
   * @param in the text based object to read from.
   * @param out the text based object to append to for output.
   * @param s the scanner used to scan input.
   * @param <C> the card object to use.
   * @throws IllegalArgumentException if in or out are null.
   */
  public <C extends Card> PokerPolygonsTextualController(Readable in, Appendable out, Scanner s)
      throws IllegalArgumentException {
    if (in == null || out == null || s == null) {
      throw new IllegalArgumentException("Appendable, Readable, and Scanner cannot be null!");
    }
    this.out = out;
    this.s = s;
    this.quit = false;
  }

  //  /**
  //   * Runs the game with a standard ruleset.
  //   *
  //   * @param args arguments to give the main function.
  //   */
  //  public static void main(String[] args) {
  //    PokerRectangles game = new PokerRectangles(5, 5);
  //    new PokerPolygonsTextualController(new InputStreamReader(System.in), System.out)
  //        .playGame(game, new PokerRectanglesTextualView(game), game.getNewDeck(), true, 5);
  //  }

  /**
   * Plays a game with the given model and deck, and shows that to the given view.
   *
   * @param model what game model to play.
   * @param view where to export visual info.
   * @param deck which deck to use for a source of cards.
   * @param shuffle whether to shuffle the deck.
   * @param handSize size of your hand.
   * @param <C> Which kind of card to use.
   * @throws IllegalStateException if input cannot be read.
   */
  @Override
  public <C extends Card> void playGame(
      PokerPolygons<C> model,
      PokerPolygonsTextualView view,
      List<C> deck,
      boolean shuffle,
      int handSize) {
    if (deck == null) {
      throw new IllegalStateException("Deck cannot be null or empty!");
    }
    if (model == null || view == null) {
      throw new IllegalArgumentException("Model and view cannot be null!");
    }
    // Set up the game
    try {
      setUp(model, deck, shuffle, handSize);
    } catch (IllegalArgumentException e) {
      throw new IllegalStateException("Game could not be started!");
    }
    try {
      while (!model.isGameOver() && !quit && !stop) {
        // Transmit game state
        printGameState(view);
        // Transmit score
        printScore(model);
        // Get user input
        handleUserInput(model);
        if (model.isGameOver()) {
          printGameOverMessage(model, view);
        }
        if (quit) {
          printQuitMessage(model, view);
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException("Bad I/0!");
    }

    // If the game is over, print "Game over. Score: N"

  }

  private <C extends Card> void setUp(
      PokerPolygons<C> model, List<C> deck, boolean shuffle, int handSize) {
    model.startGame(deck, shuffle, handSize);
  }

  private <C extends Card> void printGameState(PokerPolygonsTextualView view) throws IOException {
    view.render(out);
    out.append("\n");
  }

  private <C extends Card> void printScore(PokerPolygons<C> model) throws IOException {
    out.append("Score: " + model.getScore() + "\n");
  }

  private <C extends Card> void printGameOverMessage(
      PokerPolygons<C> model, PokerPolygonsTextualView view) throws IOException {
    printGameState(view);
    out.append("\nGame over. Score: " + model.getScore() + "\n");
  }

  private <C extends Card> void printQuitMessage(
      PokerPolygons<C> model, PokerPolygonsTextualView view) throws IOException {
    out.append("Game quit!\n");
    out.append("State of game when quit:\n");
    printGameState(view);
    printScore(model);
  }

  //  private <C extends Card> int grabNextCol(PokerPolygons<C> model, int row, int col)
  //      throws IOException {
  //    try {
  //      model.getCardAt(row, col);
  //    } catch (IllegalArgumentException e) {
  //      out.append("Invalid move. Play again. " + e.getMessage() + "\n");
  //      return grabNextCol(model, row, s.nextInt() - 1);
  //    }
  //    return col;
  //  }

  //  private <C extends Card> int grabNextRow(PokerPolygons<C> model, int row) throws IOException {
  //    try {
  //      model.getCardAt(row, row); // If a normal row is given, this will never throw.
  //    } catch (IllegalArgumentException e) {
  //      out.append("Invalid move. Play again. " + e.getMessage() + "\n");
  //      return grabNextRow(model, s.nextInt() - 1);
  //    }
  //    return row;
  //  }

  //  private <C extends Card> int grabCardFromHand(PokerPolygons<C> model, int handIdx)
  //      throws IOException {
  //    try {
  //      model.getHand().get(handIdx);
  //    } catch (IndexOutOfBoundsException e) {
  //      out.append(
  //          "Invalid move. Play again. Hand index "
  //              + handIdx
  //              + " is out of bounds (0, "
  //              + model.getHand().size()
  //              + ").\n");
  //      return grabCardFromHand(model, s.nextInt() - 1);
  //    }
  //    return handIdx;
  //  }

  // Is this the best way to do this? unlikely.
  private int getNextInt() throws QuitException {
    try {
      int i = s.nextInt();
      if (i >= 0) {
        return i;
      }
    } catch (InputMismatchException e) {
      if (s.next().equalsIgnoreCase("q")) {
        quit = true;
        throw new QuitException();
      }
    }
    return getNextInt();
  }

  private <C extends Card> void placeCardHelper(
      PokerPolygons<C> model, int handIdx, int row, int col) throws IOException {
    try {
      model.placeCardInPosition(handIdx, row, col);
    } catch (IllegalArgumentException ie) {
      // System.out.println("gaming");
      out.append("Invalid move. Play again. " + ie.getMessage() + "\n");
    }
  }

  private <C extends Card> void discardHelper(PokerPolygons<C> model, int handIdx)
      throws IOException {
    try {
      model.discardCard(handIdx);
    } catch (IllegalArgumentException ie) {
      out.append("Invalid move. Play again. " + ie.getMessage() + "\n");
    }
  }

  private <C extends Card> void handleUserInput(PokerPolygons<C> model) throws IOException {
    if (s.hasNext()) {
      String cmd = s.next();
      switch (cmd) {
        case "place":
          int handIdx = 0;
          int row = 0;
          int col = 0;
          try {
            handIdx = getNextInt() - 1;
            row = getNextInt() - 1;
            col = getNextInt() - 1;
          } catch (QuitException e) {
            return;
          }
          placeCardHelper(model, handIdx, row, col);
          return;
        case "discard":
          int discardHandIdx = 0;
          try {
            discardHandIdx = getNextInt() - 1;
          } catch (QuitException e) {
            return;
          }
          discardHelper(model, discardHandIdx);
          return;
        case "q":
        case "Q":
          quit = true;
          return;
        default:
          out.append("Invalid move. Play again. Try 'place', 'discard', or 'q'.\n");
          handleUserInput(model);
      }
    } else {
      throw new IllegalStateException("No more input!");
    }
  }

  // Hmm...
  private static class QuitException extends Exception {
    private QuitException() {}
  }
}
