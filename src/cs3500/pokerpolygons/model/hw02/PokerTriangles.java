package cs3500.pokerpolygons.model.hw02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/** A class representing a PokerTriangles game - a PokerPolygon game in the shape of a triangle. */
public class PokerTriangles implements PokerPolygons<PlayingCard> {
  private final Random r;
  private final Score Score = new Score();
  protected List<List<PlayingCard>> triangle;
  private int score;
  private List<PlayingCard> deck;
  private List<PlayingCard> hand;
  private boolean started;

  /**
   * Creates a PokerTriangles board of a designated side length.
   *
   * @param side The length of the side.
   * @param rand a random object used in the game for random card drawing.
   * @throws IllegalArgumentException if the random object is null.
   * @throws IllegalArgumentException if the side length is less than 1.
   */
  public PokerTriangles(int side, Random rand) {
    if (side < 5) {
      throw new IllegalArgumentException("Side length must be at least 5!");
    }
    this.triangle = createEmptyBoard(side);
    if (rand == null) {
      throw new IllegalArgumentException("The random object cannot be null!");
    }
    this.deck = null;
    this.hand = null;
    this.r = rand;
    this.score = 0;
    this.started = false;
  }

  /**
   * Creates a PokerTriangles board of a designated side length. Comes with a triangles input for
   * testing.
   *
   * @param rand a random object used in the game for random card drawing.
   * @throws IllegalArgumentException if the random object is null.
   * @throws IllegalArgumentException if the side length is less than 1.
   */
  public PokerTriangles(Random rand, List<List<PlayingCard>> triangle) {
    if (triangle == null) {
      throw new IllegalArgumentException("The triangle object cannot be null!");
    }
    this.triangle = triangle;
    if (rand == null) {
      throw new IllegalArgumentException("The random object cannot be null!");
    }
    this.deck = null;
    this.hand = null;
    this.r = rand;
    this.score = 0;
    this.started = false;
  }

  /**
   * Creates a PokerTriangles board of a designated side length.
   *
   * @param side The length of the side.
   * @throws IllegalArgumentException if the side length is less than 1.
   */
  public PokerTriangles(int side) {
    if (side < 5) {
      throw new IllegalArgumentException("Side length must be at least 5!");
    }
    this.triangle = createEmptyBoard(side);
    this.deck = null;
    this.hand = null;
    this.r = new Random();
    this.score = 0;
    this.started = false;
  }

  private List<List<PlayingCard>> createEmptyBoard(int side) {
    if (side < 5) {
      throw new IllegalArgumentException("The side length must be at least 5!");
    }
    List<List<PlayingCard>> al = new ArrayList<>();
    for (int i = 0; i < side; i++) {
      al.add(new ArrayList<PlayingCard>());
      for (int j = 0; j < (side - i); j++) {
        al.get(i).add(null);
      }
    }
    return al;
  }

  //  private void checkCoordinates(int row, int col) {
  //    if (row >= this.triangle.size() || row < 0) {
  //      throw new IllegalArgumentException("Invalid board row index!");
  //    }
  //    if (col >= this.triangle.get(row).size()) {
  //      throw new IllegalArgumentException("Invalid board column index!");
  //    }
  //  }

  /**
   * Places a card from your hand into a slot on the board. This removes the card from your hand.
   *
   * @param cardIdx index of the card in hand to place (0-index based)
   * @param row row to place the card in (0-index based)
   * @param col column to place the card in (0-index based)
   * @throws IllegalStateException if the game hasn't started yet
   * @throws IllegalArgumentException if the cardIdx is not within your hand
   * @throws IllegalArgumentException if the row is not within the game's board
   * @throws IllegalArgumentException if the col is not within the game's board
   * @throws IllegalArgumentException if a card has already been played in that position
   */
  @Override
  public void placeCardInPosition(int cardIdx, int row, int col) {
    if (!this.started) {
      throw new IllegalStateException("The game hasn't started yet!");
    }
    try {
      if (cardIdx > this.hand.size() || cardIdx < 0) {
        throw new IllegalArgumentException("Invalid hand index!");
      }
      if (col >= this.triangle.size() || col < 0) {
        throw new IllegalArgumentException("Bad column index!");
      }
      if (col > row || row >= this.triangle.size() || row < 0) {
        throw new IllegalArgumentException(
            "Bad bounds! Column and Row should be between "
                + 0
                + " and "
                + (this.triangle.size())
                + "(col), "
                + (this.triangle.get(col).size() - col)
                + "(row), but is "
                + col
                + ","
                + row);
      }
      if (this.triangle.get(col).get(row - col) == null) {
        PlayingCard c = this.hand.remove(cardIdx);
        this.triangle.get(col).set(row - col, c);
        if (!this.deck.isEmpty()) {
          this.hand.add(drawCard());
        }
        this.score = scoreBoard(); // updating the score dynamically :-D
      } else {
        throw new IllegalArgumentException("You've already played something there!");
      }
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Bad indices!");
    }
  }

  /**
   * Gets the score of this board. Notably, this is used by the LoosePokerTriangles subclass.
   *
   * @return the score of the current board.
   */
  protected int scoreBoard() {
    int i = 0;
    for (List<PlayingCard> l : this.triangle) {
      // System.out.println(l);
      i += cs3500.pokerpolygons.model.hw02.Score.scoreHand(l);
    }
    List<PlayingCard> diagonal = new ArrayList<>();
    for (int k = 0; k < this.getHeight(); k++) {
      diagonal.add(getCardAt(k, k));
    }
    i += cs3500.pokerpolygons.model.hw02.Score.scoreHand(diagonal);
    for (int k = 0; k < this.getHeight(); k++) {
      i += cs3500.pokerpolygons.model.hw02.Score.scoreHand(grabRows(k));
    }
    return i;
  }

  /**
   * Gets the rows of the triangles for scoring.
   *
   * @param r the 'height' of the given row to get.
   * @return a row of cards.
   */
  protected List<PlayingCard> grabRows(int r) {
    List<PlayingCard> list = new ArrayList<>();
    for (int i = 0; i < this.getHeight(); i++) {
      if (r - i >= 0) {
        list.add(this.triangle.get(i).get(r - i));
      }
    }
    // System.out.println(list);
    return list;
  }

  /**
   * Remove a card from your hand and remove it from the game.
   *
   * @param cardIdx index of the card in hand to discard (0-index based)
   * @throws IllegalStateException if the game hasn't started yet
   * @throws IllegalArgumentException if the card index is outside the hand's bounds
   */
  @Override
  public void discardCard(int cardIdx) {
    if (!this.started) {
      throw new IllegalStateException("The game hasn't started yet!");
    }
    if (this.deck.size() + this.hand.size() <= getBoardSize()) {
      throw new IllegalStateException("There's not enough cards left to do that!");
    }
    if (cardIdx < 0 || cardIdx >= new ArrayList<>(this.hand).size()) {
      throw new IllegalArgumentException("Invalid discard index!");
    }
    this.hand.remove(cardIdx);
    this.hand.add(drawCard());
  }

  private int getBoardSize() {
    int acc = 0;
    for (List<PlayingCard> a : this.triangle) {
      for (PlayingCard p : a) {
        if (p == null) {
          acc += 1;
        }
      }
    }
    return acc;
  }

  /**
   * Starts the game.
   *
   * @param deck list of cards to play the game with
   * @param shuffle whether the deck should be shuffled
   * @param handSize maximum hand size for the game
   * @throws IllegalArgumentException if there aren't enough cards to cover the board
   * @throws IllegalArgumentException if the given hand size is less than 1
   * @throws IllegalArgumentException if the game has already started
   */
  @Override
  public void startGame(List<PlayingCard> deck, boolean shuffle, int handSize) {
    if (deck == null || deck.isEmpty()) {
      throw new IllegalArgumentException("Deck cannot be null!");
    }
    if (deck.size() < getBoardSize() + handSize) {
      throw new IllegalArgumentException(
          "That's not enough cards to cover the board and your hand!");
    }
    if (handSize < 1) {
      throw new IllegalArgumentException("The handSize must be at least 1!");
    }
    if (this.started) {
      throw new IllegalStateException("The game has already started!");
    }
    this.deck = new ArrayList<>(deck);
    this.started = true;
    this.hand = new ArrayList<>();
    if (shuffle) {
      Collections.shuffle(this.deck, this.r);
    }
    for (int i = 0; i < handSize; i++) {
      // Drawing cards to hand
      this.hand.add(this.drawCard());
    }
  }

  /**
   * Draw a random card from your deck.
   *
   * @return a random card in your deck.
   * @throws IllegalStateException if the game hasn't started yet
   * @throws IllegalStateException if your deck is empty
   */
  private PlayingCard drawCard() {
    if (!this.started) {
      throw new IllegalStateException("The game hasn't started yet!");
    }
    if (this.deck.isEmpty()) {
      throw new IllegalStateException("Your deck, " + this.deck + ", has no cards left!");
    }
    // System.out.println(this.deck);
    return this.deck.remove(0);
  }

  /**
   * Gets the width of the PokerTriangle.
   *
   * @return the width of the PokerTriangle.
   */
  @Override
  public int getWidth() {
    return this.triangle.size();
  }

  /**
   * Gets the height of the PokerTriangle.
   *
   * @return the height of the PokerTriangle.
   */
  @Override
  public int getHeight() {
    return this.triangle.get(0).size();
  }

  /**
   * Creates a full 52-Card deck.
   *
   * @return a full 52-Card deck.
   */
  @Override
  public List<PlayingCard> getNewDeck() {
    ArrayList<PlayingCard> d = new ArrayList<PlayingCard>();
    for (int i = 1; i <= 52; i++) {
      d.add(new PlayingCard(i));
    }
    return d;
  }

  /**
   * Finds and returns the card at the location in the triangle.
   *
   * @param row the row to access
   * @param col the column to access
   * @return the card at the given location
   * @throws IllegalStateException if the game hasn't started yet
   */
  @Override
  public PlayingCard getCardAt(int row, int col) {
    if (!this.started) {
      throw new IllegalStateException("The game hasn't started yet!");
    }
    if (col >= this.triangle.size() || col < 0) {
      // System.out.println(this.triangle);
      throw new IllegalArgumentException(
          "Bad column index! Column should be below " + this.triangle.size() + " and above 0.");
    }
    if (col > row) {
      throw new IllegalArgumentException(
          "Bad bounds! Column and Row should be between "
              + 0
              + " and "
              + (this.triangle.size())
              + "(col), "
              + (this.triangle.get(col).size() - col)
              + "(row), but is "
              + col
              + ","
              + row);
    }
    return this.triangle.get(col).get(row - col);
  }

  /**
   * Returns your hand.
   *
   * @return your hand.
   * @throws IllegalStateException if the game hasn't started yet
   */
  @Override
  public List<PlayingCard> getHand() {
    if (!this.started) {
      throw new IllegalStateException("You don't have a hand, you haven't started yet!");
    }
    return this.hand;
  }

  /**
   * Returns your score.
   *
   * @return your score.
   */
  @Override
  public int getScore() {
    return this.score;
  }

  /**
   * Returns the remaining deck size.
   *
   * @return the remaining deck size.
   * @throws IllegalStateException if the game hasn't started yet
   */
  @Override
  public int getRemainingDeckSize() {
    if (!this.started) {
      throw new IllegalStateException("You don't have a deck, you haven't started yet!");
    }
    return this.deck.size();
  }

  /**
   * Returns if the game has ended by checking if there are empty slots left on the board.
   *
   * @return if the game has ended.
   * @throws IllegalStateException if the game hasn't started yet
   */
  @Override
  public boolean isGameOver() {
    if (!this.started) {
      throw new IllegalStateException("The game hasn't started yet!");
    }
    return this.getBoardSize() == 0;
  }
}
