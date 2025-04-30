package cs3500.pokerpolygons.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw04.PokerRectangles;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

/** Class to test methods in the class PokerRectanglesTextualView. */
public class PokerRectanglesTextualViewTest {

  private PokerRectangles ex2 = new PokerRectangles(6, 7);
  private PokerRectanglesTextualView newView = new PokerRectanglesTextualView(ex2);
  private PlayingCard[][] playedGrid =
      new PlayingCard[][] { // grody.
        {
          new PlayingCard("2H"),
          new PlayingCard("KC"),
          new PlayingCard("QS"),
          new PlayingCard("JH"),
          new PlayingCard("3C")
        },
        {
          new PlayingCard("3S"),
          new PlayingCard("JD"),
          new PlayingCard("10D"),
          new PlayingCard("3C"),
          new PlayingCard("2H")
        },
        {
          new PlayingCard("4D"),
          new PlayingCard("10S"),
          new PlayingCard("8H"),
          new PlayingCard("2H"),
          new PlayingCard("5H")
        },
        {
          new PlayingCard("5C"),
          new PlayingCard("9C"),
          new PlayingCard("7C"),
          new PlayingCard("AS"),
          new PlayingCard("9D")
        },
        {
          new PlayingCard("AH"),
          new PlayingCard("QH"),
          new PlayingCard("5H"),
          null,
          new PlayingCard("6H")
        }
      };

  /** 'Sets up' testing values prior to calling test methods. */
  @Before
  public void setUp() {
    List<PlayingCard> justEnoughDeck = new ArrayList<>();
    for (int i = 0; i <= 25; i++) {
      justEnoughDeck.add(new PlayingCard("AS"));
    }
    playedGrid =
        new PlayingCard[][] { // grody.
          {
            new PlayingCard("2H"),
            new PlayingCard("KC"),
            new PlayingCard("QS"),
            new PlayingCard("JH"),
            new PlayingCard("3C")
          },
          {
            new PlayingCard("3S"),
            new PlayingCard("JD"),
            new PlayingCard("10D"),
            new PlayingCard("3C"),
            new PlayingCard("2H")
          },
          {
            new PlayingCard("4D"),
            new PlayingCard("10S"),
            new PlayingCard("8H"),
            new PlayingCard("2H"),
            new PlayingCard("5H")
          },
          {
            new PlayingCard("5C"),
            new PlayingCard("9C"),
            new PlayingCard("7C"),
            new PlayingCard("AS"),
            new PlayingCard("9D")
          },
          {
            new PlayingCard("AH"),
            new PlayingCard("QH"),
            new PlayingCard("5H"),
            null,
            new PlayingCard("6H")
          }
        };
    ex2 = new PokerRectangles(6, 7);
    // private PokerRectangles exRand = new PokerRectangles(5, 5, r);
    // exRand = new PokerRectangles(5, 5, r);
    // deck = ex1.getNewDeck();
    // tinyDeck = new ArrayList<PlayingCard>(Arrays.asList(new PlayingCard("3H")));
    ex2.startGame(new ArrayList<>(ex2.getNewDeck()), false, 1);

    newView = new PokerRectanglesTextualView(ex2);
  }

  /** Tests the constructor in the class PokerRectanglesTextualView. */
  @Test
  public void testConstructor() {
    assertThrows(IllegalArgumentException.class, () -> new PokerRectanglesTextualView(null));
  }

  /** Tests the method toString() in the class PokerRectanglesTextualView. */
  @Test
  public void testToString() {
    PokerRectangles exScore = new PokerRectangles(playedGrid, new Random(1));
    exScore.startGame(exScore.getNewDeck(), false, 1);
    PokerRectanglesTextualView fullView = new PokerRectanglesTextualView(exScore);
    assertEquals(
        " 2♡  3♠  4♢  5♣  A♡\n"
            + " K♣  J♢ 10♠  9♣  Q♡\n"
            + " Q♠ 10♢  8♡  7♣  5♡\n"
            + " J♡  3♣  2♡  A♠  __\n"
            + " 3♣  2♡  5♡  9♢  6♡\n"
            + "Deck: 51\n"
            + "Hand: A♠",
        fullView.toString());
    ex2.placeCardInPosition(0, 0, 0);
    assertEquals(
        " A♠  __  __  __  __  __\n"
            + " __  __  __  __  __  __\n"
            + " __  __  __  __  __  __\n"
            + " __  __  __  __  __  __\n"
            + " __  __  __  __  __  __\n"
            + " __  __  __  __  __  __\n"
            + " __  __  __  __  __  __\n"
            + "Deck: 50\n"
            + "Hand: 2♠",
        newView.toString());
  }
}
