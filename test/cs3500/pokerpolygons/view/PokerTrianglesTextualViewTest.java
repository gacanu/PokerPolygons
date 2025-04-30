package cs3500.pokerpolygons.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.PokerTriangles;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

/** Class for testing methods on the class PokerTrianglesTextualView. */
public class PokerTrianglesTextualViewTest {

  List<List<PlayingCard>> inProgressTriangle =
      Arrays.asList(
          Arrays.asList(
              new PlayingCard("2D"),
              new PlayingCard("3S"),
              new PlayingCard("4H"),
              new PlayingCard("5C"),
              new PlayingCard("6S"),
              new PlayingCard("KC"),
              new PlayingCard("QS")),
          Arrays.asList(
              new PlayingCard("8D"),
              new PlayingCard("3H"),
              new PlayingCard("4C"),
              new PlayingCard("8S"),
              new PlayingCard("9D"),
              new PlayingCard("JS")),
          Arrays.asList(
              new PlayingCard("5D"),
              new PlayingCard("10D"),
              new PlayingCard("5H"),
              new PlayingCard("JH"),
              new PlayingCard("AH")),
          Arrays.asList(
              new PlayingCard("3C"),
              new PlayingCard("9H"),
              new PlayingCard("6C"),
              new PlayingCard("2C")),
          Arrays.asList(new PlayingCard("7H"), new PlayingCard("KS"), new PlayingCard("8S")),
          Arrays.asList(new PlayingCard("JD"), new PlayingCard("9S")),
              Collections.singletonList((PlayingCard) null));
  private Random r = new Random(1);
  PokerTriangles playedGame = new PokerTriangles(r, this.inProgressTriangle);
  PokerTrianglesTextualView playedGameString = new PokerTrianglesTextualView(this.playedGame);
  private PokerTriangles ex1 = new PokerTriangles(5);
  private final List<PlayingCard> standardDeck = ex1.getNewDeck();
  private PokerTriangles ex2 = new PokerTriangles(5);
  private PokerTrianglesTextualView triString1 = new PokerTrianglesTextualView(this.ex2);

  /** Sets up example objects for testing prior to each test method call. */
  @Before
  public void setUp() {
    r = new Random(1);
    ex1 = new PokerTriangles(5);
    ex2 = new PokerTriangles(5);
    ex2.startGame(standardDeck, false, 12);
    // exRand.startGame(standardDeck2, true, 1);
    triString1 = new PokerTrianglesTextualView(this.ex2);
    playedGame.startGame(standardDeck, false, 12);
    List<List<PlayingCard>> inProgressTriangle =
        Arrays.asList(
            Arrays.asList(
                new PlayingCard("2D"),
                new PlayingCard("3S"),
                new PlayingCard("4H"),
                new PlayingCard("5C"),
                new PlayingCard("6S"),
                new PlayingCard("KC"),
                new PlayingCard("QS")),
            Arrays.asList(
                new PlayingCard("8D"),
                new PlayingCard("3H"),
                new PlayingCard("4C"),
                new PlayingCard("8S"),
                new PlayingCard("9D"),
                new PlayingCard("JS")),
            Arrays.asList(
                new PlayingCard("5D"),
                new PlayingCard("10D"),
                new PlayingCard("5H"),
                new PlayingCard("JH"),
                new PlayingCard("AH")),
            Arrays.asList(
                new PlayingCard("3C"),
                new PlayingCard("9H"),
                new PlayingCard("6C"),
                new PlayingCard("2C")),
            Arrays.asList(new PlayingCard("7H"), new PlayingCard("KS"), new PlayingCard("8S")),
            Arrays.asList(new PlayingCard("JD"), new PlayingCard("9S")),
                Collections.singletonList((PlayingCard) null));
    PokerTriangles playedGame = new PokerTriangles(r, this.inProgressTriangle);
    PokerTrianglesTextualView playedGameString = new PokerTrianglesTextualView(this.playedGame);
  }

  /** Tests the method ToString() on the class PokerTrianglesTextualView. */
  @Test
  public void testToString() {
    assertThrows(IllegalArgumentException.class, () -> new PokerTrianglesTextualView(null));
    assertEquals(
        " 2♢\n"
            + " 3♠  8♢\n"
            + " 4♡  3♡  5♢\n"
            + " 5♣  4♣ 10♢  3♣\n"
            + " 6♠  8♠  5♡  9♡  7♡\n"
            + " K♣  9♢  J♡  6♣  K♠  J♢\n"
            + " Q♠  J♠  A♡  2♣  8♠  9♠  __\n"
            + "Deck: 40\n"
            + "Hand: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠",
        this.playedGameString.toString());
    assertEquals(
        " __\n"
            + " __  __\n"
            + " __  __  __\n"
            + " __  __  __  __\n"
            + " __  __  __  __  __\n"
            + "Deck: 40\n"
            + "Hand: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠",
        this.triString1.toString());
    ex2.placeCardInPosition(0, 1, 1);
    assertEquals(
        " __\n"
            + " __  A♠\n"
            + " __  __  __\n"
            + " __  __  __  __\n"
            + " __  __  __  __  __\n"
            + "Deck: 39\n"
            + "Hand: 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠",
        this.triString1.toString());
  }
}
