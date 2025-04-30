package cs3500.pokerpolygons.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw02.PokerTriangles;
import cs3500.pokerpolygons.view.PokerTrianglesTextualView;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

/**
 * Public class for running tests, both integration and unit, on the PokerPolygonsController class.
 */
public class PokerPolygonsControllerTest {
  Random r = new Random(1);
  List<List<PlayingCard>> smallGrid = List.of(Collections.singletonList((PlayingCard) null));
  private StringBuilder log = new StringBuilder();
  private PokerPolygons<PlayingCard> inputmock = new GetInputsMock(log, 3);
  private PokerTrianglesTextualView mockview = new PokerTrianglesTextualView(inputmock);
  private PokerTriangles game = new PokerTriangles(5, r);
  private PokerTrianglesTextualView view = new PokerTrianglesTextualView(game);
  private PokerTriangles smallgame = new PokerTriangles(r, smallGrid);
  private PokerTrianglesTextualView smallview = new PokerTrianglesTextualView(smallgame);
  private PokerPolygons<PlayingCard> smallmock = new GetInputsMock(log, 1);
  private PokerTrianglesTextualView smallmockview = new PokerTrianglesTextualView(smallmock);

  private void mockTestRun(
      PokerPolygons<PlayingCard> model,
      PokerTrianglesTextualView view,
      Interaction... interactions) {
    StringBuilder fakeInput = new StringBuilder();
    StringBuilder fakeOutput = new StringBuilder();

    for (Interaction interaction : interactions) {
      interaction.apply(fakeInput, fakeOutput);
    }

    StringReader input = new StringReader(fakeInput.toString());
    StringBuilder output = new StringBuilder();
    PokerPolygonsController controller =
        new PokerPolygonsTextualController(input, output, new Scanner(input));
    controller.playGame(model, view, model.getNewDeck(), false, 1);
  }

  private void testRun(
      PokerPolygons<PlayingCard> model,
      PokerTrianglesTextualView view,
      Interaction... interactions) {
    StringBuilder fakeInput = new StringBuilder();
    StringBuilder fakeOutput = new StringBuilder();

    for (Interaction interaction : interactions) {
      interaction.apply(fakeInput, fakeOutput);
    }

    StringReader input = new StringReader(fakeInput.toString());
    StringBuilder output = new StringBuilder();
    PokerPolygonsController controller =
        new PokerPolygonsTextualController(input, output, new Scanner(input));
    controller.playGame(model, view, model.getNewDeck(), false, 1);

    assertEquals(fakeOutput.toString(), output.toString());
  }

  /** Sets up examples prior to their use. */
  @Before
  public void setUp() {
    log = new StringBuilder();
    inputmock = new GetInputsMock(log, 3);
    mockview = new PokerTrianglesTextualView(inputmock);
    smallmock = new GetInputsMock(log, 1);
    game = new PokerTriangles(5, r);
    view = new PokerTrianglesTextualView(game);
    List<List<PlayingCard>> smallGrid = List.of(Collections.singletonList((PlayingCard) null));
    smallgame = new PokerTriangles(r, smallGrid);
    smallview = new PokerTrianglesTextualView(smallgame);
    smallmockview = new PokerTrianglesTextualView(smallmock);
  }

  /** Tests quitting the game. */
  @Test
  public void testQuitGame() {
    assertEquals(5, game.getWidth());
    testRun(
        game,
        view,
        new InputInteraction("q"),
        new PrintInteraction(
            " __\n"
                + " __  __\n"
                + " __  __  __\n"
                + " __  __  __  __\n"
                + " __  __  __  __  __\n"
                + "Deck: 51\n"
                + "Hand: A♠\n"
                + "Score: 0\n"
                + "Game quit!\n"
                + "State of game when quit:\n"
                + " __\n"
                + " __  __\n"
                + " __  __  __\n"
                + " __  __  __  __\n"
                + " __  __  __  __  __\n"
                + "Deck: 51\n"
                + "Hand: A♠\n"
                + "Score: 0"));
  }

  /** Tests quitting the game, mid-command. */
  @Test
  public void testQuitGame2() {
    assertEquals(5, game.getWidth());
    testRun(
        game,
        view,
        new InputInteraction("place\n"),
        new InputInteraction("1\n"), // Commands can be interrupted to quit!
        new InputInteraction("q"),
        new PrintInteraction(
            " __\n"
                + " __  __\n"
                + " __  __  __\n"
                + " __  __  __  __\n"
                + " __  __  __  __  __\n"
                + "Deck: 51\n"
                + "Hand: A♠\n"
                + "Score: 0\n"
                + "Game quit!\n"
                + "State of game when quit:\n"
                + " __\n"
                + " __  __\n"
                + " __  __  __\n"
                + " __  __  __  __\n"
                + " __  __  __  __  __\n"
                + "Deck: 51\n"
                + "Hand: A♠\n"
                + "Score: 0"));
  }

  /** Tests quitting the game, mid-command. */
  @Test
  public void testQuitGame3() {
    assertEquals(5, game.getWidth());
    testRun(
        game,
        view,
        new InputInteraction("discard\n"),
        new InputInteraction("q"),
        new PrintInteraction(
            " __\n"
                + " __  __\n"
                + " __  __  __\n"
                + " __  __  __  __\n"
                + " __  __  __  __  __\n"
                + "Deck: 51\n"
                + "Hand: A♠\n"
                + "Score: 0\n"
                + "Game quit!\n"
                + "State of game when quit:\n"
                + " __\n"
                + " __  __\n"
                + " __  __  __\n"
                + " __  __  __  __\n"
                + " __  __  __  __  __\n"
                + "Deck: 51\n"
                + "Hand: A♠\n"
                + "Score: 0"));
  }

  /** Tests finishing the game. */
  @Test
  public void testFinishGame() {
    assertEquals(5, game.getWidth());
    testRun(
        smallgame,
        smallview,
        new InputInteraction("place 1 1 1\n"),
        new PrintInteraction(
            " __\n"
                + "Deck: 51\n"
                + "Hand: A♠\n"
                + "Score: 0\n"
                + " A♠\n"
                + "Deck: 50\n"
                + "Hand: 2♠\n"
                + "\n"
                + "Game over. Score: 0"));
  }

  /** Tests quitting the game. */
  @Test
  public void testImmediatelyQuit() {

    mockTestRun(inputmock, mockview, new InputInteraction("q"));
    assertEquals(
        "Grabbed the deck of the current game.\n"
            + "Started a game with the given deck, unshuffled, with a hand size of 1.\n",
        log.toString());
  }

  /** Tests quitting the game, mid-command. */
  @Test
  public void testQuit1() {

    mockTestRun(
        inputmock, mockview, new InputInteraction("place\n 1\n"), new InputInteraction("q"));
    assertEquals(
        "Grabbed the deck of the current game.\n"
            + "Started a game with the given deck, unshuffled, with a hand size of 1.\n",
        log.toString());
  }

  /** Tests quitting the game, mid-command. */
  @Test
  public void testQuit2() {

    mockTestRun(inputmock, mockview, new InputInteraction("discard\n"), new InputInteraction("q"));
    assertEquals(
        "Grabbed the deck of the current game.\n"
            + "Started a game with the given deck, unshuffled, with a hand size of 1.\n",
        log.toString());
  }

  /** Tests when the controller runs out of input. */
  @Test
  public void testOutOfInput() {
    assertThrows(
        IllegalStateException.class,
        () -> mockTestRun(inputmock, mockview, new InputInteraction("place 1 1 1\n")));
  }

  /** Tests placing a card. */
  @Test
  public void testPlace() {
    mockTestRun(
        inputmock, mockview, new InputInteraction("place 1 1 1\n"), new InputInteraction("q"));
    assertEquals(
        "Grabbed the deck of the current game.\n"
            + "Started a game with the given deck, unshuffled, with a hand size of 1.\n"
            + "Grabbed card from index 0 and put it in row 0, col 0.\n",
        log.toString());
  }

  /** Tests placing a card. Fails when using 0-based inputs. */
  @Test
  public void testPlace1() {
    assertThrows(
        IllegalStateException.class,
        () ->
            mockTestRun(
                inputmock,
                mockview,
                new InputInteraction("place 1 0 1\n"))); // Fails when using 0-based inputs
  }

  /** Tests placing a card. Fails when using 0-based inputs. */
  @Test
  public void testPlace2() {
    assertThrows(
        IllegalStateException.class,
        () ->
            mockTestRun(
                inputmock,
                mockview,
                new InputInteraction("place 0 1 1\n"))); // Fails when using 0-based inputs
  }

  /** Tests placing a card. Keeps going after negative inputs are given. */
  @Test
  public void testPlace3() {
    mockTestRun(
        inputmock,
        mockview,
        new InputInteraction("place 1 -3 -2\n"), // Keeps going after negative inputs are given
        new InputInteraction("place 1 1 1\n"),
        new InputInteraction("q"));
    assertEquals(
        "Grabbed the deck of the current game.\n"
            + "Started a game with the given deck, unshuffled, with a hand size of 1.\n"
            + "Grabbed card from index 0 and put it in row 0, col 0.\n",
        log.toString());
  }

  /** Tests placing a card. Keeps going after negative inputs are given. */
  @Test
  public void testPlace4() {
    mockTestRun(
        inputmock,
        mockview,
        new InputInteraction("place -1 1 1\n"), // This also applies to index
        new InputInteraction("place 1 1 1\n"),
        new InputInteraction("q"));
    assertEquals(
        "Grabbed the deck of the current game.\n"
            + "Started a game with the given deck, unshuffled, with a hand size of 1.\n"
            + "Grabbed card from index 0 and put it in row 0, col 0.\n",
        log.toString());
  }

  /** Tests discarding. */
  @Test
  public void testDiscard() {
    mockTestRun(
        inputmock, mockview, new InputInteraction("discard 1\n"), new InputInteraction("q"));
    assertEquals(
        "Grabbed the deck of the current game.\n"
            + "Started a game with the given deck, unshuffled, with a hand size of 1.\n"
            + "Discarded card in position 0.\n",
        log.toString());
  }

  /** Tests discarding. Keeps going after a negative/bad input. */
  @Test
  public void testDiscard1() {
    mockTestRun(
        inputmock,
        mockview,
        new InputInteraction("discard -3\n"),
        new InputInteraction("discard 1\n"), // keeps going after a bad input
        new InputInteraction("q"));
    assertEquals(
        "Grabbed the deck of the current game.\n"
            + "Started a game with the given deck, unshuffled, with a hand size of 1.\n"
            + "Discarded card in position 0.\n",
        log.toString());
  }

  /** Tests discarding. Keeps going after a negative/bad input. */
  @Test
  public void testDiscard4() {
    mockTestRun(
        inputmock,
        mockview,
        new InputInteraction("discard a\n"),
        new InputInteraction("discard 1\n"), // keeps going after a bad input
        new InputInteraction("q"));
    assertEquals(
        "Grabbed the deck of the current game.\n"
            + "Started a game with the given deck, unshuffled, with a hand size of 1.\n"
            + "Discarded card in position 0.\n",
        log.toString());
  }

  /** Tests discarding. Fails if 0 is given. */
  @Test
  public void testDiscard2() {
    assertThrows(
        IllegalStateException
            .class, // I would like to throw IllArgExc, But i think something is catching it. I'll
        // worry about it later.
        () ->
            mockTestRun(
                inputmock,
                mockview,
                new InputInteraction("discard 0\n"))); // Fails when using 0-based inputs
  }

  /** Tests game end. */
  @Test
  public void testWin() {
    mockTestRun(smallmock, smallmockview, new InputInteraction("place 1 1 1\n"));
    assertEquals(
        "Grabbed the deck of the current game.\n"
            + "Started a game with the given deck, unshuffled, with a hand size of 1.\n"
            + "Grabbed card from index 0 and put it in row 0, col 0.\n"
            + "And the game ends!\n",
        log.toString());
  }

  private interface Interaction {
    void apply(StringBuilder in, StringBuilder out);
  }

  private class PrintInteraction implements Interaction {
    String[] lines;

    PrintInteraction(String... lines) {
      this.lines = lines;
    }

    /**
     * Appends all given strings to the given StringBuilder.
     *
     * @param in strings to append
     * @param out the builder to append to
     */
    @Override
    public void apply(StringBuilder in, StringBuilder out) {
      for (String line : lines) {
        out.append(line).append("\n");
      }
    }
  }

  private class InputInteraction implements Interaction {
    String input;

    InputInteraction(String input) {
      this.input = input;
    }

    /**
     * Appends the input to the given StringBuilder.
     *
     * @param in strings to append
     * @param out the builder to append to
     */
    public void apply(StringBuilder in, StringBuilder out) {
      in.append(input);
    }
  }
}
