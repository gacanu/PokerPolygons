package cs3500.pokerpolygons.model.hw02;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

/** Class for testing methods on the class PokerTriangles. */
public class PokerTrianglesTest {
  Random r = new Random(1);
  PokerTriangles ex1 = new PokerTriangles(5);
  PokerTriangles ex2 = new PokerTriangles(5);
  PokerTriangles tallEx = new PokerTriangles(10);
  List<List<PlayingCard>> smallGrid = List.of(Collections.singletonList((PlayingCard) null));
  PokerTriangles smallTri = new PokerTriangles(r, smallGrid);
  PokerTriangles exRand = new PokerTriangles(5, this.r);
  List<PlayingCard> standardDeck = ex1.getNewDeck();
  List<PlayingCard> standardDeck2 = ex1.getNewDeck();
  List<PlayingCard> smallDeck =
      new ArrayList<>(Arrays.asList(new PlayingCard(1), new PlayingCard(2), new PlayingCard(3)));
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

  /** Sets up example objects for testing prior to each test method call. */
  @Before
  public void setUp() {
    r = new Random(1);
    ex1 = new PokerTriangles(5);
    ex2 = new PokerTriangles(5);
    smallTri = new PokerTriangles(r, smallGrid);
    exRand = new PokerTriangles(5, this.r);
    standardDeck = ex1.getNewDeck();
    standardDeck2 = ex1.getNewDeck();
    smallDeck =
        new ArrayList<>(Arrays.asList(new PlayingCard(1), new PlayingCard(2), new PlayingCard(3)));
    ex2.startGame(standardDeck, false, 12);
    // exRand.startGame(standardDeck2, true, 1);
    smallTri.startGame(smallDeck, false, 2);
    playedGame.startGame(standardDeck2, false, 12);
  }

  /** Tests for the PokerTriangles constructor. */
  @Test
  public void testConstructor() {
    // Examples of valid construction.
    // assertDoesNotThrow(() -> new PokerTriangles(3));
    // assertDoesNotThrow(() -> new PokerTriangles(3, this.r));
    // Examples of invalid construction.
    // Bad side length (cannot be less than 1).
    assertThrows(IllegalArgumentException.class, () -> new PokerTriangles(0));
    assertThrows(IllegalArgumentException.class, () -> new PokerTriangles(-1));
    assertThrows(IllegalArgumentException.class, () -> new PokerTriangles(0, this.r));
    // Bad random entry (cannot be null).
    assertThrows(IllegalArgumentException.class, () -> new PokerTriangles(1, null));
  }

  /** Tests for the method placeCardInPosition on the class PokerTriangles. */
  @Test
  public void placeCardInPosition() {
    assertThrows(IllegalStateException.class, () -> ex1.placeCardInPosition(0, 1, 1));
    assertThrows(IllegalArgumentException.class, () -> ex2.placeCardInPosition(-1, 1, 1));
    assertThrows(IllegalArgumentException.class, () -> ex2.placeCardInPosition(20, 1, 1));
    assertThrows(IllegalArgumentException.class, () -> ex2.placeCardInPosition(0, 20, -1));
    // System.out.println(ex2.getHand());
    ex2.placeCardInPosition(0, 2, 2);
    assertEquals(new PlayingCard(1), ex2.getCardAt(2, 2));
  }

  /** Tests for the method discardCard on the class PokerTriangles. */
  @Test
  public void discardCard() {
    // EX1 hasn't started yet
    assertThrows(IllegalStateException.class, () -> ex1.discardCard(0));
    // checking to see that smallTri has replaced the first card with one from the deck
    assertEquals(smallTri.getHand(), Arrays.asList(new PlayingCard(1), new PlayingCard(2)));
    smallTri.discardCard(0);
    assertEquals(smallTri.getHand(), Arrays.asList(new PlayingCard(2), new PlayingCard(3)));
    // SmallTri has no cards left in deck to discard
    assertThrows(IllegalStateException.class, () -> smallTri.discardCard(0));
  }

  /** Testing the method startGame() in the class PokerTriangles. */
  @Test
  public void startGame() {
    // Nonsensical hand size test.
    assertThrows(IllegalArgumentException.class, () -> ex1.startGame(standardDeck, false, -3));
    // Testing the method getHand() after starting a game with startGame(). (Unshuffled)
    List<PlayingCard> exDeck = ex1.getNewDeck();
    assertThrows(IllegalStateException.class, () -> ex1.getHand());
    ex1.startGame(exDeck, false, 3);
    // assertDoesNotThrow(() -> ex1.getHand());
    assertEquals(
        ex1.getHand(), Arrays.asList(new PlayingCard(1), new PlayingCard(2), new PlayingCard(3)));
    // Testing the method getHand() after starting a game with startGame(). (Shuffled)
    List<PlayingCard> exDeck2 = ex1.getNewDeck();
    exRand.startGame(exDeck2, true, 1);
    assertEquals(exRand.getHand(), List.of(new PlayingCard("♢", 6)));
  }

  /** Testing the method getWidth() in the class PokerTriangles. */
  @Test
  public void getWidth() {
    // Unstarted game
    assertEquals(10, tallEx.getWidth());
    // Started game
    assertEquals(5, ex2.getWidth());
  }

  /** Testing the method getHeight() in the class PokerTriangles. */
  @Test
  public void getHeight() {
    // Unstarted game
    assertEquals(10, tallEx.getHeight());
    // Started game
    assertEquals(5, ex2.getHeight());
  }

  /** Testing the method getNewDeck() in the class PokerTriangles. */
  @Test
  public void getNewDeck() {
    List<PlayingCard> manualDeck = new ArrayList<>();
    for (int i = 1; i <= 52; i++) {
      manualDeck.add(new PlayingCard(i));
    } // My willingness to create this deck by each individual card is low
    assertEquals(manualDeck, ex1.getNewDeck());
    // Any PokerTriangle will return the same deck
    assertEquals(ex1.getNewDeck(), ex2.getNewDeck());
  }

  /** Testing the method getCardAt() in the class PokerTriangles. */
  @Test
  public void getCardAt() {
    // Trying to get a card in an unstarted game
    assertThrows(IllegalStateException.class, () -> ex1.getCardAt(0, 0));
    // Placing a card in a previously empty slot with no reference
    assertNull(ex2.getCardAt(2, 2));
    ex2.placeCardInPosition(0, 2, 2);
    assertEquals(new PlayingCard(1), ex2.getCardAt(2, 2));
    // Trying to get a card in a bad position
    assertThrows(IllegalStateException.class, () -> ex1.getCardAt(0, -3));
    assertEquals(new PlayingCard("9H"), playedGame.getCardAt(4, 3));
  }

  /** Testing the method getHand() in the class PokerTriangles. */
  @Test
  public void getHand() {
    // Trying to get a hand in an unstarted game
    assertThrows(IllegalStateException.class, () -> ex1.getHand());
    // Trying to get a normal hand
    assertEquals(List.of(new PlayingCard(1), new PlayingCard(2)), smallTri.getHand());
  }

  /** Testing the method getScore() in the class PokerTriangles. */
  @Test
  public void getScore() {
    // Testing on an unstarted game
    assertEquals(0, ex1.getScore());
    assertEquals(0, ex2.getScore());
    // Testing on a started game
    assertEquals(0, smallTri.getScore());
    // the score count will not update until you play a card!
    assertEquals(0, playedGame.getScore());
    playedGame.placeCardInPosition(0, 6, 6);
    assertEquals(59, playedGame.getScore());
  }

  /** Testing the method getRemainingDeckSize() on the class PokerTriangles. */
  @Test
  public void getRemainingDeckSize() {
    // Testing on an unstarted game
    assertThrows(IllegalStateException.class, () -> ex1.getRemainingDeckSize());
    // Testing on a started game, before and after a discard
    assertEquals(40, ex2.getRemainingDeckSize());
    ex2.discardCard(0);
    assertEquals(39, ex2.getRemainingDeckSize());
  }

//  /** Testing the method isGameOver() on the class PokerTriangles. */
//  @Test
//  public void isGameOver() {
//    // Testing on an unstarted game
//    assertThrows(IllegalStateException.class, () -> ex1.isGameOver());
//    // Testing on an unfinished, started game
//    assertFalse(smallTri.isGameOver());
//    // Finishing the game, then testing it
//    smallTri.placeCardInPosition(0, 0, 0);
//    assertTrue(smallTri.isGameOver());
//  }
}
