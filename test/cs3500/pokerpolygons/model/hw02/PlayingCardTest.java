package cs3500.pokerpolygons.model.hw02;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/** Class for testing methods on the class PlayingCard. */
public class PlayingCardTest {
  PlayingCard queenOfDiamonds = new PlayingCard("♢", 12);
  PlayingCard threeOfClubs = new PlayingCard("♣", 3);

  /** Tests the method getSuit() on the PlayingCard class. */
  @Test
  public void getSuit() {
    assertEquals("♣", this.threeOfClubs.getSuit());
    assertEquals("♢", this.queenOfDiamonds.getSuit());
  }

  /** Tests the method getRank() on the PlayingCard class. */
  @Test
  public void getRank() {
    assertEquals(3, this.threeOfClubs.getRank());
    assertEquals(12, this.queenOfDiamonds.getRank());
  }

  /** Tests the method getID() on the PlayingCard class. */
  @Test
  public void getID() {
    assertEquals(16, this.threeOfClubs.getID());
    assertEquals(51, this.queenOfDiamonds.getID());
  }

  /** Tests the method toString() on the PlayingCard class. */
  @Test
  public void testToString() {
    assertEquals("3♠", new PlayingCard("3S").toString());
    assertEquals("3♣", this.threeOfClubs.toString());
    assertEquals("Q♢", this.queenOfDiamonds.toString());
  }

  /** Tests the method Equals() on the PlayingCard class. */
  @Test
  public void testEquals() {
    PlayingCard tocClone = new PlayingCard("♣", 3);
    PlayingCard queenOfSpades = new PlayingCard("♠", 12);
    // Testing on two different objects that equals() deem equal
    assertEquals(this.threeOfClubs, tocClone);
    // Testing on non-equal objects
    assertNotEquals(this.threeOfClubs, this.queenOfDiamonds);
    assertNotEquals(this.queenOfDiamonds, queenOfSpades);
  }

  /** Tests the method hashCode() on the PlayingCard class. */
  @Test
  public void testHashCode() {
    assertEquals(16, this.threeOfClubs.hashCode());
    assertEquals(51, this.queenOfDiamonds.hashCode());
  }
}
