package cs3500.pokerpolygons.model.hw04;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/** Class for testing methods on the class PokerRectangles. */
public class PokerRectanglesTest {

  private final Random r = new Random(1);
  private PokerRectangles ex1 = new PokerRectangles(5, 5);
  private PokerRectangles ex2 = new PokerRectangles(6, 7);
  private PokerRectangles exRand = new PokerRectangles(5, 5, r);
  private PokerRectangles justEnoughEx = new PokerRectangles(5, 5);
  private List<PlayingCard> deck = ex1.getNewDeck();
  private final List<PlayingCard> justEnoughDeck = new ArrayList<>();
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
  private PokerRectangles exScore = new PokerRectangles(playedGrid, r);

  /** 'Sets up' testing values prior to calling test methods. */
  @Before
  public void setUp() {
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
    ex1 = new PokerRectangles(5, 5);
    ex2 = new PokerRectangles(6, 7);
    justEnoughEx = new PokerRectangles(5, 5);
    exRand = new PokerRectangles(5, 5, r);
    deck = ex1.getNewDeck();
    ex2.startGame(new ArrayList<>(this.deck), false, 1);
    justEnoughEx.startGame(new ArrayList<>(this.justEnoughDeck), false, 1);
    exScore = new PokerRectangles(playedGrid, r);
    exScore.startGame(this.deck, false, 1);
  }

  @Test
  public void testConstructor() {
    assertThrows(IllegalArgumentException.class, () -> new PokerRectangles(-1, 5));
    assertThrows(IllegalArgumentException.class, () -> new PokerRectangles(2, 5));
    assertThrows(IllegalArgumentException.class, () -> new PokerRectangles(5, 0));
    assertThrows(IllegalArgumentException.class, () -> new PokerRectangles(5, 2));
    assertThrows(IllegalArgumentException.class, () -> new PokerRectangles(5, 5, null));
  }

  /** Tests for the method PlaceCardInPosition() for the class PokerRectangles. */
  @Test
  public void placeCardInPosition() {
    // On an unstarted game
    assertThrows(IllegalStateException.class, () -> ex1.placeCardInPosition(1, 1, 1));
    // On a started game
    ex2.placeCardInPosition(0, 1, 1);
    assertEquals(new PlayingCard("AS"), ex2.getCardAt(1, 1));
    // Trying to place a card where one has already been placed
    assertThrows(IllegalArgumentException.class, () -> ex2.placeCardInPosition(0, 1, 1));
    // Illegal index input
    assertThrows(IllegalArgumentException.class, () -> ex2.placeCardInPosition(-10, 2, 1));
    assertThrows(IllegalArgumentException.class, () -> ex2.placeCardInPosition(0, 20, 1));
    assertThrows(IllegalArgumentException.class, () -> ex2.placeCardInPosition(0, 2, -1));
  }

  /** Tests for the method discardCard() for the class PokerRectangles. */
  @Test
  public void discardCard() {
    // On an unstarted game
    assertThrows(IllegalStateException.class, () -> ex1.discardCard(0));
    // On a started game
    assertEquals(List.of(new PlayingCard("AS")), ex2.getHand());
    ex2.discardCard(0);
    assertEquals(List.of(new PlayingCard("2S")), ex2.getHand());
    // On a game with no cards left in the deck
    justEnoughEx.discardCard(0);
    assertThrows(IllegalStateException.class, () -> justEnoughEx.discardCard(0));
    // Illegal index input
    assertThrows(IllegalArgumentException.class, () -> ex2.discardCard(7));
    assertThrows(IllegalArgumentException.class, () -> ex2.discardCard(-7));
  }

  /** Tests for the method startGame() for the class PokerRectangles. */
  @Test
  public void startGame() {
    // Illegal argument inputs.
    assertThrows(IllegalArgumentException.class, () -> ex1.startGame(null, false, 1));
    assertThrows(IllegalArgumentException.class, () -> ex1.startGame(new ArrayList<>(), true, 1));
    assertThrows(IllegalArgumentException.class, () -> ex1.startGame(this.deck, false, 0));
    assertThrows(IllegalArgumentException.class, () -> ex1.startGame(this.deck, false, 200));
    // Testing on an already started game.
    assertThrows(IllegalStateException.class, () -> ex2.startGame(this.deck, false, 2));
    // Testing on an unstarted, then started game. Verified by getHand().
    assertThrows(IllegalStateException.class, () -> ex1.getHand());
    ex1.startGame(this.deck, false, 2);
    assertEquals(List.of(new PlayingCard("AS"), new PlayingCard("2S")), ex1.getHand());
    // Testing on a game with shuffle enabled. Also verified by getHand(). Note that ex1 and exRand
    // are otherwise identical except for their shuffle and random values.
    exRand.startGame(new ArrayList<>(this.deck), true, 2);
    assertEquals(List.of(new PlayingCard("6D"), new PlayingCard("AH")), exRand.getHand());
  }

  /** Tests for the method getWidth() for the class PokerRectangles. */
  @Test
  public void getWidth() {
    assertEquals(5, ex1.getWidth());
    assertEquals(6, ex2.getWidth());
  }

  /** Tests for the method getHeight() for the class PokerRectangles. */
  @Test
  public void getHeight() {
    assertEquals(5, ex1.getHeight());
    assertEquals(7, ex2.getHeight());
  }

  /** Testing the method getNewDeck() in the class PokerRectangles. */
  @Test
  public void getNewDeck() {
    List<PlayingCard> manualDeck = new ArrayList<>();
    for (int i = 1; i <= 52; i++) {
      manualDeck.add(new PlayingCard(i));
    } // My willingness to create this deck by each individual card is low. Sorry!
    assertEquals(manualDeck, ex1.getNewDeck());
    // Any PokerRectangles will return the same deck
    assertEquals(ex1.getNewDeck(), ex2.getNewDeck());
  }

  /** Tests for the method getCardAt() for the class PokerRectangles. */
  @Test
  public void getCardAt() {
    // On an unstarted game
    assertThrows(IllegalStateException.class, () -> ex1.getCardAt(1, 1));
    // On a started game
    ex2.placeCardInPosition(0, 1, 1);
    assertEquals(new PlayingCard("AS"), ex2.getCardAt(1, 1));
    assertNull(ex2.getCardAt(2, 1));
    // Illegal inputs
    assertThrows(IllegalArgumentException.class, () -> ex2.getCardAt(20, 1));
    assertThrows(IllegalArgumentException.class, () -> ex2.getCardAt(1, 20));
    assertThrows(IllegalArgumentException.class, () -> ex2.getCardAt(1, -1));
  }

  /** Tests for the method getHand() for the class PokerRectangles. */
  @Test
  public void getHand() {
    // Testing on an unstarted, then started game
    assertThrows(IllegalStateException.class, () -> ex1.getHand());
    ex1.startGame(this.deck, false, 1);
    assertEquals(List.of(new PlayingCard("AS")), ex1.getHand());
    // Testing on a game with shuffle enabled.
    exRand.startGame(new ArrayList<>(this.deck), true, 2);
    assertEquals(List.of(new PlayingCard("6D"), new PlayingCard("AH")), exRand.getHand());
  }

  /** Tests for the method getScore() for the class PokerRectangles. */
  @Test
  public void getScore() {
    assertEquals(0, ex1.getScore());
    assertEquals(0, exScore.getScore());
    exScore.placeCardInPosition(0, 3, 4);
    assertEquals(38, exScore.getScore()); // Score is only updated after you've played!
  }

  /** Tests for the method getRemainingDeckSize() for the class PokerRectangles. */
  @Test
  public void getRemainingDeckSize() {
    // Testing on an unstarted game
    assertThrows(IllegalStateException.class, () -> ex1.getRemainingDeckSize());
    // Testing on a started game, before and after a discard
    assertEquals(51, ex2.getRemainingDeckSize());
    ex2.discardCard(0);
    assertEquals(50, ex2.getRemainingDeckSize()); // huzzah!
  }

  /** Tests for the method isGameOver() for the class PokerRectangles. */
  @Test
  public void isGameOver() {
    // Testing on an unstarted game
    assertThrows(IllegalStateException.class, () -> ex1.isGameOver());
    // Testing on a started game, before and after finishing it
    assertFalse(exScore.isGameOver());
    exScore.placeCardInPosition(0, 3, 4);
    assertTrue(exScore.isGameOver());
  }
}
