package cs3500.pokerpolygons.model.hw02;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import cs3500.pokerpolygons.model.hw04.LooseScore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

// (♣, ♠, ♡, ♢)

/** Tests methods within the class Score. */
public class ScoreTest {

  private final List<PlayingCard> repeatedStraight =
      new ArrayList<>(
          Arrays.asList(
              new PlayingCard("♡", 2),
              new PlayingCard("♡", 3),
              new PlayingCard("♡", 4),
              new PlayingCard("♡", 5),
              new PlayingCard("♣", 6),
              new PlayingCard("♣", 4)));
  private final List<PlayingCard> notStraightFlush =
      new ArrayList<>(
          Arrays.asList(
              new PlayingCard("♡", 2),
              new PlayingCard("♡", 3),
              new PlayingCard("♡", 4),
              new PlayingCard("♡", 5),
              new PlayingCard("♣", 6),
              new PlayingCard("♡", 4)));
  private final List<PlayingCard> flushWith3Kind =
      new ArrayList<>(
          Arrays.asList(
              new PlayingCard("♡", 2),
              new PlayingCard("♡", 3),
              new PlayingCard("♡", 4),
              new PlayingCard("♡", 5),
              new PlayingCard("♣", 6),
              new PlayingCard("♡", 4),
              new PlayingCard("♡", 4)));
  private final List<PlayingCard> straightAce =
      new ArrayList<>(
          Arrays.asList(
              new PlayingCard("♡", 2),
              new PlayingCard("♡", 3),
              new PlayingCard("♡", 4),
              new PlayingCard("♡", 5),
              new PlayingCard("♣", 1)));
  private final List<PlayingCard> straightAceHigh =
      new ArrayList<>(
          Arrays.asList(
              new PlayingCard("♡", 10),
              new PlayingCard("♡", 11),
              new PlayingCard("♡", 12),
              new PlayingCard("♡", 13),
              new PlayingCard("♣", 1),
              new PlayingCard(5)));
  private final List<PlayingCard> straightFlush =
      new ArrayList<>(
          Arrays.asList(
              new PlayingCard("♡", 2),
              new PlayingCard("♡", 3),
              new PlayingCard("♡", 4),
              new PlayingCard("♡", 5),
              new PlayingCard("♡", 6)));
  private final List<PlayingCard> royalFlush =
      new ArrayList<>(
          Arrays.asList(
              new PlayingCard("♣", 10),
              new PlayingCard("♡", 10),
              new PlayingCard("♡", 11),
              new PlayingCard("♡", 12),
              new PlayingCard("♡", 13),
              new PlayingCard("♡", 1)));
  private final List<PlayingCard> fullHouse =
      new ArrayList<>(
          Arrays.asList(
              new PlayingCard("♣", 10),
              new PlayingCard("♡", 10),
              new PlayingCard("♡", 12),
              new PlayingCard("♡", 12),
              new PlayingCard("♡", 12),
              new PlayingCard("♡", 1)));
  private final List<PlayingCard> twoPair =
      new ArrayList<>(
          Arrays.asList(
              new PlayingCard("♣", 10),
              new PlayingCard("♡", 11),
              new PlayingCard("♡", 10),
              new PlayingCard("♡", 11),
              new PlayingCard("♡", 12)));
  private final List<PlayingCard> fourOfAKind =
      new ArrayList<>(
          Arrays.asList(
              new PlayingCard("♣", 10),
              new PlayingCard("♡", 10),
              new PlayingCard("♡", 12),
              new PlayingCard("♡", 12),
              new PlayingCard("♡", 12),
              new PlayingCard("♡", 12),
              new PlayingCard("♡", 11)));
  private final List<PlayingCard> fiveOfAKind =
      new ArrayList<>(
          Arrays.asList(
              new PlayingCard("♣", 10),
              new PlayingCard("♡", 10),
              new PlayingCard("♡", 12),
              new PlayingCard("♡", 12),
              new PlayingCard("♡", 12),
              new PlayingCard("♡", 12),
              new PlayingCard("♡", 12),
              new PlayingCard("♡", 11)));
  private final List<PlayingCard> twoPairWNulls =
      new ArrayList<>(
          Arrays.asList(
              new PlayingCard("♣", 10),
              new PlayingCard("♡", 10),
              new PlayingCard("♡", 12),
              new PlayingCard("♡", 12),
              null,
              null,
              new PlayingCard("♡", 11)));
  private final List<PlayingCard> theDeck = new PokerTriangles(5).getNewDeck();
  private final List<PlayingCard> highCard =
      new ArrayList<>(
          Arrays.asList(
              new PlayingCard("♣", 2),
              new PlayingCard("♡", 10),
              new PlayingCard("♢", 13),
              new PlayingCard("♡", 4),
              new PlayingCard("♢", 5)));
  private final List<PlayingCard> looseFlush =
      new ArrayList<>(
          Arrays.asList(
              new PlayingCard("2H"),
              new PlayingCard("6H"),
              new PlayingCard("3H"),
              new PlayingCard("9D"),
              new PlayingCard("10D")));
  private final List<PlayingCard> looseStraight =
      new ArrayList<>(
          Arrays.asList(
              new PlayingCard("2H"),
              new PlayingCard("3H"),
              new PlayingCard("5H"),
              new PlayingCard("7S"),
              new PlayingCard("8D")));
  private final List<PlayingCard> looseStraightFlush =
      new ArrayList<>(
          Arrays.asList(
              new PlayingCard("2H"),
              new PlayingCard("3H"),
              new PlayingCard("5H"),
              new PlayingCard("7D"),
              new PlayingCard("8D")));

  /** For testing straight hands on the method ScoreHand. */
  @Test
  public void StraightTest() {
    assertEquals(15, LooseScore.scoreHand(this.looseStraight));
    assertEquals(15, Score.scoreHand(this.straightAce));
    assertEquals(15, Score.scoreHand(this.straightAceHigh));
    assertEquals(15, Score.scoreHand(this.repeatedStraight));
  }

  /** For testing flush hands on the method ScoreHand. */
  @Test
  public void FlushTest() {
    assertEquals(20, LooseScore.scoreHand(this.looseFlush));
    assertEquals(20, Score.scoreHand(this.notStraightFlush));
    assertEquals(20, Score.scoreHand(this.flushWith3Kind));
  }

  /** For testing straight flush hands on the method ScoreHand. */
  @Test
  public void StraightFlushTest() {
    assertEquals(75, LooseScore.scoreHand(this.looseStraightFlush));
    assertEquals(75, Score.scoreHand(this.straightFlush));
    assertEquals(75, Score.scoreHand(this.royalFlush));
    assertEquals(75, Score.scoreHand(this.theDeck));
  }

  /** For testing full house hands on the method ScoreHand. */
  @Test
  public void FullHouseTest() {
    assertEquals(25, Score.scoreHand(this.fullHouse));
    // assertEquals(25, Score.scoreHand(this.fullHouse2));
  }

  /** For testing the two pair hand on the method ScoreHand. */
  @Test
  public void TwoPairTest() {
    assertEquals(5, Score.scoreHand(this.twoPair));
    assertEquals(5, Score.scoreHand(this.twoPairWNulls));
  }

  /** For testing the four of a kind hand on the method ScoreHand. */
  @Test
  public void FourOfAKindTest() {
    assertEquals(50, Score.scoreHand(this.fourOfAKind));
    assertEquals(50, Score.scoreHand(this.fiveOfAKind));
  }

  /** For testing the high card hand on the method ScoreHand. */
  @Test
  public void HighCardTest() {
    assertEquals(0, Score.scoreHand(new ArrayList<>()));
    assertEquals(0, Score.scoreHand(this.highCard));
    assertThrows(IllegalArgumentException.class, () -> Score.scoreHand(null));
  }
}
