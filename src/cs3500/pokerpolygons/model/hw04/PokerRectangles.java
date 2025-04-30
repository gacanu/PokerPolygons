package cs3500.pokerpolygons.model.hw04;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw02.Score;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Collections;

/** A class representing a game of PokerPolygons played on a rectangular grid; PokerRectangles. */
public class PokerRectangles implements PokerPolygons<PlayingCard> {
  private final Random r;
  private final PlayingCard[][] board;
  private int score;
  private List<PlayingCard> hand;
  private List<PlayingCard> deck;
  private boolean started;

  /**
   * Constructs a PokerRectangles with a width and height.
   *
   * @param width the width of the new board.
   * @param height the height of the new board.
   */
  public PokerRectangles(int width, int height) {
    this.r = new Random();
    this.board = createEmptyBoard(width, height);
    this.score = 0;
    this.hand = null;
    this.deck = null;
    this.started = false;
  }

  /**
   * Constructs a PokerRectangles with the given board and random value for testing.
   *
   * @param board the board to be used.
   * @param r the Random value to be used in shuffling.
   */
  public PokerRectangles(PlayingCard[][] board, Random r) {
    if (r == null || board == null) {
      throw new IllegalArgumentException("The random object cannot be null!");
    }
    this.r = r;
    this.board = board;
    this.score = 0;
    this.hand = null;
    this.deck = null;
    this.started = false;
  }

  /**
   * Constructs a PokerRectangles with a width and height.
   *
   * @param width the width of the new board.
   * @param height the height of the new board.
   * @param r the random value to be used for shuffling and drawing cards.
   */
  public PokerRectangles(int width, int height, Random r) {
    if (r == null) {
      throw new IllegalArgumentException("The random object cannot be null!");
    }
    this.r = r;
    this.board = createEmptyBoard(width, height);
    this.score = 0;
    this.hand = null;
    this.deck = null;
    this.started = false;
  }

  private PlayingCard[][] createEmptyBoard(int width, int height) {
    if (width < 5 || height < 5) {
      throw new IllegalArgumentException("Width and height of the board must be greater than 4!");
    }
    return new PlayingCard[width][height];
  }

  /**
   * Places a card in the given position on the board. Row is the vertical index, and column is the
   * horizontal index. The top left is (0,0).
   *
   * @param cardIdx index of the card in hand to place (0-index based)
   * @param row row to place the card in (0-index based)
   * @param col column to place the card in (0-index based)
   */
  @Override
  public void placeCardInPosition(int cardIdx, int col, int row) {
    if (!this.started) {
      throw new IllegalStateException("The game hasn't started yet!");
    }
    PlayingCard toPlace = null;
    try {
      hand.get(cardIdx);
    } catch (IndexOutOfBoundsException ie) {
      throw new IllegalArgumentException("Hand index " + cardIdx + " is invalid!");
    }
    try {
      if (board[row][col] != null) {
        throw new IllegalArgumentException("You've already played something there!");
      }
      toPlace = hand.remove(cardIdx);
      if (!this.deck.isEmpty()) {
        this.hand.add(drawCard());
      }
      board[row][col] = toPlace;
      this.score = scoreBoard();
    } catch (IndexOutOfBoundsException ie) {
      throw new IllegalArgumentException(
          "Row index " + row + " and col index " + col + " are invalid!");
    }
  }

  private int scoreBoard() {
    int i = 0;
    for (PlayingCard[] array : this.board) {
      i += Score.scoreHand(Arrays.asList(array));
    }
    for (int j = 0; j < this.getHeight(); j++) {
      i += Score.scoreHand(grabRows(j));
    }
    return i;
  }

  private List<PlayingCard> grabRows(int j) {
    List<PlayingCard> l = new ArrayList<>();
    for (PlayingCard[] array : this.board) {
      l.add(array[j]);
    }
    return l;
  }

  private int getRemainingSpots() {
    int nulls = 0;
    for (PlayingCard[] x : board) {
      for (PlayingCard y : x) {
        if (y == null) {
          nulls++;
        }
      }
    }
    return nulls;
  }

  /**
   * Discards a card at the given position in the hand.
   *
   * @param cardIdx index of the card in hand to discard (0-index based)
   */
  @Override
  public void discardCard(int cardIdx) {
    if (!this.started) {
      throw new IllegalStateException("The game hasn't started yet!");
    }
    if (this.deck.size() + this.hand.size() <= getRemainingSpots()) {
      throw new IllegalStateException("You don't have enough cards left to do that!");
    }
    try {
      hand.remove(cardIdx);
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Discard index " + cardIdx + " is invalid!");
    }
    this.hand.add(drawCard());
  }

  /**
   * Starts a game with the given deck, handsize, and whether to shuffle.
   *
   * @param deck list of cards to play the game with
   * @param shuffle whether the deck should be shuffled
   * @param handSize maximum hand size for the game
   */
  @Override
  public void startGame(List<PlayingCard> deck, boolean shuffle, int handSize) {
    if (deck == null || deck.isEmpty()) {
      throw new IllegalArgumentException("Deck cannot be null/empty!");
    }
    if (deck.size() < getRemainingSpots() + handSize) {
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
    this.hand = new ArrayList<>();
    this.started = true;
    if (shuffle) {
      Collections.shuffle(this.deck, this.r);
    }
    for (int i = 0; i < handSize; i++) {
      // System.out.println(this.hand);
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
   * Return the width of this board.
   *
   * @return the width of this board.
   */
  @Override
  public int getWidth() {
    return board.length;
  }

  /**
   * Returns the height of this board.
   *
   * @return the height of this board.
   */
  @Override
  public int getHeight() {
    return board[0].length;
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
   * Get the card at the desired position on the board. The top left of the board is (0, 0); The
   * vertical coordinates are represented by row, and the horizontal by col.
   *
   * @param row the row to access
   * @param col the column to access
   * @return the card at the given position on the board.
   */
  @Override
  public PlayingCard getCardAt(int col, int row) {
    if (!this.started) {
      throw new IllegalStateException("The game hasn't started yet!");
    }
    try {
      return board[row][col];
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException(
          "Row " + row + " and col " + col + " were out of bounds on the board array.");
    }
  }

  /**
   * Returns your hand of cards.
   *
   * @return your hand full of cards.
   */
  @Override
  public List<PlayingCard> getHand() {
    if (!this.started) {
      throw new IllegalStateException("You don't have a hand, you haven't started yet!");
    }
    return this.hand;
  }

  /**
   * Returns the current score in the game.
   *
   * @return the score in the game.
   */
  @Override
  public int getScore() {
    return this.score;
  }

  /**
   * Returns how many cards are left in your deck.
   *
   * @return how many cards are left in the deck.
   */
  @Override
  public int getRemainingDeckSize() {
    if (!this.started) {
      throw new IllegalStateException("You don't have a hand, you haven't started yet!");
    }
    return this.deck.size();
  }

  /**
   * Returns if the game is over - i.e. that there are no more empty spaces left on the board.
   *
   * @return if the game is over.
   */
  @Override
  public boolean isGameOver() {
    if (!this.started) {
      throw new IllegalStateException("You haven't even started the game yet!");
    }
    return getRemainingSpots() == 0;
  }
}
