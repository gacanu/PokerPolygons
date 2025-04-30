package cs3500.pokerpolygons.model.hw02;

/**
 * A method representing a playing card, with a suit, rank, and ID out of 56 cards. Suits are
 * represented by the Unicode suit strings (♠ ♣ ♡ ♢).
 */
public class PlayingCard implements PlayingCardInterface {

  private final String suit;
  private final int rank;
  private final int ID;

  /**
   * A method for creating a PlayingCard object.
   *
   * @param suit the suit of the card.
   * @param rank the rank of the card. Aces are rank 1.
   */
  public PlayingCard(String suit, int rank) {
    if (!(suit.equals("♠") || suit.equals("♣") || suit.equals("♡") || suit.equals("♢"))) {
      throw new IllegalArgumentException(suit + " is not a valid suit!");
    }
    if (rank < 1 || rank > 13) {
      throw new IllegalArgumentException(rank + " is not a valid card rank!");
    }
    this.suit = suit;
    this.rank = rank;
    this.ID = toID(suit, rank);
  }

  /** A method for creating a PlayingCard object. */
  public PlayingCard(String in) {
    int newRank;
    switch (in.substring(0, in.length() - 1)) {
      case "K":
        newRank = 13;
        break;
      case "Q":
        newRank = 12;
        break;
      case "J":
        newRank = 11;
        break;
      case "A":
        newRank = 1;
        break;
      default:
        newRank = Integer.parseInt(in.substring(0, in.length() - 1));
    }
    if (newRank < 1 || newRank > 13) {
      throw new IllegalArgumentException(newRank + " is not a valid card rank!");
    }
    String newSuit = toSuit(in.charAt(in.length() - 1));
    this.rank = newRank;
    this.suit = newSuit;
    this.ID = toID(newSuit, newRank);
  }

  /**
   * A method for creating a PlayingCard object based on their order in a 52 card deck.
   *
   * @param id Position of this card out of 52 in a 52 card deck.
   */
  public PlayingCard(int id) {
    if (id < 1 || id > 52) {
      throw new IllegalArgumentException(id + " is not a valid card ID!");
    }
    if (id % 13 != 0) {
      this.rank = id % 13;
    } else {
      this.rank = 13;
    }
    this.suit = toSuit(id);
    this.ID = id;
  }

  private String toSuit(char in) {
    switch (in) {
      case 'S':
        return "♠";
      case 'H':
        return "♡";
      case 'D':
        return "♢";
      case 'C':
        return "♣";
      default:
        throw new IllegalArgumentException("Incorrect input! Must be S, H, D, or C!");
    }
  }

  /**
   * Returns a suit string based on the ID of a given card. Goes in order Spade, Club, Heart,
   * Diamond.
   *
   * @param id the ID of the given card.
   * @return the string of the suit based on the given ID.
   */
  private String toSuit(int id) {
    switch ((int) Math.ceil((double) id / 13.0)) {
      case 1:
        return "♠";
      case 2:
        return "♣";
      case 3:
        return "♡";
      case 4:
        return "♢";
      default:
        throw new IllegalArgumentException("Please input a number between 1 and 52!");
    }
  }

  /**
   * Gets an ID from a card's suit and rank.
   *
   * @param suit the suit of the card.
   * @param rank the rank of the card.
   * @return the ID of a card.
   */
  private int toID(String suit, int rank) {
    switch (suit) {
      case "♠":
        return rank;
      case "♣":
        return rank + 13;
      case "♡":
        return rank + 26;
      case "♢":
        return rank + 39;
      default:
        return 0;
    }
  }

  /**
   * Returns the suit of this card.
   *
   * @return the suit of this card.
   */
  @Override
  public String getSuit() {
    return this.suit;
  }

  /**
   * Returns the rank of this card.
   *
   * @return the rank of this card.
   */
  @Override
  public int getRank() {
    return this.rank;
  }

  /**
   * Returns the ID of this card.
   *
   * @return the ID of this card.
   */
  @Override
  public int getID() {
    return this.ID;
  }

  /**
   * Returns a string representation of this card. For example, the Ten of Spades would be
   * represented as "10♠".
   *
   * @return a string representation of this card.
   */
  @Override
  public String toString() {
    if (this.rank == 1 || this.rank >= 11) {
      switch (this.rank) {
        case 1:
          return "A" + this.suit;
        case 11:
          return "J" + this.suit;
        case 12:
          return "Q" + this.suit;
        case 13:
          return "K" + this.suit;
      }
    }
    return this.rank + this.suit;
  }

  /**
   * Tests if two objects are equal.
   *
   * @param o the object to compare.
   * @return if they are equal.
   */
  public boolean equals(Object o) {
    if (!(o instanceof Card)) {
      return false;
    }
    Card other = (Card) o;
    return this.toString().equals(o.toString());
  }

  /**
   * Returns the hashcode of this object.
   *
   * @return the hashcode of this object.
   */
  public int hashCode() {
    return ID;
  }
}
