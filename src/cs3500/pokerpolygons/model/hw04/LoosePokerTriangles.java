package cs3500.pokerpolygons.model.hw04;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw02.PokerTriangles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class representing a game of PokerTriangles played 'loosely'. This means that flushes can be
 * represented by either all red suits or black suits. This also means that straights can be created
 * with gaps of 1 (2, 4, 5, 7, 9).
 */
public class LoosePokerTriangles extends PokerTriangles implements PokerPolygons<PlayingCard> {

  /**
   * Creates a LoosePokerTriangles board of a designated side length.
   *
   * @param side The length of the side.
   * @param rand a random object used in the game for random card drawing.
   * @throws IllegalArgumentException if the random object is null.
   * @throws IllegalArgumentException if the side length is less than 1.
   */
  public LoosePokerTriangles(int side, Random rand) {
    super(side, rand);
  }

  /**
   * Creates a LoosePokerTriangles board of a designated side length.
   *
   * @param side The length of the side.
   * @throws IllegalArgumentException if the random object is null.
   * @throws IllegalArgumentException if the side length is less than 1.
   */
  public LoosePokerTriangles(int side) {
    super(side);
  }

  /**
   * Creates a LoosePokerTriangles board of a designated side length. Comes with a triangles input
   * for testing.
   *
   * @param rand a random object used in the game for random card drawing.
   * @throws IllegalArgumentException if the random object is null.
   * @throws IllegalArgumentException if the side length is less than 1.
   */
  public LoosePokerTriangles(Random rand, List<List<PlayingCard>> triangle) {
    super(rand, triangle);
  }

  /**
   * Gets the score of this board. Notably, this is used by the LoosePokerTriangles subclass.
   *
   * @return the score of the current board.
   */
  @Override
  protected int scoreBoard() {
    int i = 0;
    for (List<PlayingCard> l : this.triangle) {
      // System.out.println(l);
      i += LooseScore.scoreHand(l);
    }
    List<PlayingCard> diagonal = new ArrayList<>();
    for (int k = 0; k < this.getHeight(); k++) {
      diagonal.add(getCardAt(k, k));
    }
    i += LooseScore.scoreHand(diagonal);
    for (int k = 0; k < this.getHeight(); k++) {
      i += LooseScore.scoreHand(grabRows(k));
    }
    return i;
  }
}
