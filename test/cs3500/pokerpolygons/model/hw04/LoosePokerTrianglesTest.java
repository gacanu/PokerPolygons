package cs3500.pokerpolygons.model.hw04;

import static org.junit.Assert.assertEquals;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.PokerTriangles;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.Test;

/** Class to test methods in the class LoosePokerTriangles. */
public class LoosePokerTrianglesTest {

  /** Testing the method scoreBoard() in the class LoosePokerTriangles. */
  @Test
  public void scoreBoard() {
    List<List<PlayingCard>> inProgressTriangle =
        Arrays.asList(
            Arrays.asList(
                new PlayingCard("2D"),
                new PlayingCard("3S"),
                new PlayingCard("4H"),
                new PlayingCard("7C"),
                new PlayingCard("6S"),
                new PlayingCard("KC"),
                new PlayingCard("QS")),
            Arrays.asList(
                new PlayingCard("8D"),
                new PlayingCard("3H"),
                new PlayingCard("4H"),
                new PlayingCard("8H"),
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
    List<List<PlayingCard>> inProgressTriangle2 =
        Arrays.asList(
            Arrays.asList(
                new PlayingCard("2D"),
                new PlayingCard("3S"),
                new PlayingCard("4H"),
                new PlayingCard("7C"),
                new PlayingCard("6S"),
                new PlayingCard("KC"),
                new PlayingCard("QS")),
            Arrays.asList(
                new PlayingCard("8D"),
                new PlayingCard("3H"),
                new PlayingCard("4H"),
                new PlayingCard("8H"),
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

    LoosePokerTriangles loosePlayedGame =
        new LoosePokerTriangles(new Random(1), inProgressTriangle);
    PokerTriangles playedGame = new PokerTriangles(new Random(1), inProgressTriangle2);
    playedGame.startGame(playedGame.getNewDeck(), false, 1);
    loosePlayedGame.startGame(playedGame.getNewDeck(), false, 1);
    playedGame.placeCardInPosition(0, 6, 6);
    loosePlayedGame.placeCardInPosition(0, 6, 6);
    assertEquals(175, loosePlayedGame.getScore());
    assertEquals(
        44,
        playedGame
            .getScore()); // Showing the score difference between two otherwise equal games of loose
    // and not loose PokerTriangles.
  }
}
