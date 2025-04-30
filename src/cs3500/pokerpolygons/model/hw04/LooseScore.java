package cs3500.pokerpolygons.model.hw04;

import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.Score;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class for scoring hands 'loosely'. I.e. Straights can be made with gaps of 1, and Flushes
 * require only a set of all Black suits or all Red suits.
 */
public class LooseScore extends Score {

  /**
   * Scores the given hand.
   *
   * @param list the hand to be scored.
   * @return the score awarded to the hand.
   */
  public static int scoreHand(List<PlayingCard> list) {
    if (list == null) {
      throw new IllegalArgumentException("This list cannot be null!");
    }
    List<PlayingCard> l = new ArrayList<PlayingCard>(list);
    l.removeAll(Collections.singleton(null));
    if (l.size() >= 5) {
      if (checkStraightFlush(l)) {
        // System.out.println(l + " " + 75);
        return 75;
      }
      if (checkFourOfAKind(l)) {
        // System.out.println(l + " " + 50);
        return 50;
      }
      if (checkFullHouse(l)) {
        // System.out.println(l + " " + 25);
        return 25;
      }
      if (checkFlush(l, false)) {
        // System.out.println(l + " " + 20);
        return 20;
      }
      if (checkStraight(l)) {
        // System.out.println(l + " " + 15);
        return 15;
      }
      if (checkThreeOfAKind(l)) {
        // System.out.println(l + " " + 10);
        return 10;
      }
      if (checkTwoPair(l)) {
        // System.out.println(l + " " + 5);
        return 5;
      }
      if (checkPair(l)) {
        // System.out.println(l + " " + 2);
        return 2;
      }
    }
    return 0;
  }

  protected static boolean checkStraightFlush(List<PlayingCard> l) {
    return checkFlush(l, true);
  }

  /**
   * A method to check for straights (directly ascending ranks, without wrapping) in a given poker
   * hand.
   *
   * @param l the hand which to check.
   * @return if the given hand is a straight.
   */
  protected static boolean checkStraight(List<PlayingCard> l) {
    List<Integer> ls = new ArrayList<>();
    List<Integer> la;
    for (PlayingCard c : l) {
      ls.add(getRank(c));
    }
    Collections.sort(ls);
    la = new ArrayList<>(ls);
    for (int i = 0; i < la.size(); i++) {
      if (la.get(i) == 1) {
        la.set(i, 14);
      }
    }
    Collections.sort(la);
    if (l.size() >= 5) {
      return checkDirectlyAscending(ls, ls.get(0) - 1, 0, 0)
          || checkDirectlyAscending(la, la.get(0) - 1, 0, 0);
    }
    return false;
  }

  protected static int getRank(PlayingCard c) {
    String s = c.toString();
    int i = s.length() - 1;
    switch (c.toString().substring(0, i)) {
      case "A":
        return 1;
      case "K":
        return 13;
      case "Q":
        return 12;
      case "J":
        return 11;
      default:
        return Integer.parseInt(c.toString().substring(0, i));
    }
  }

  private static boolean checkDirectlyAscending(List<Integer> l, int last, int index, int soFar) {
    List<Integer> ls = new ArrayList<Integer>(l);
    // This isn't really the smartest way of doing this, but it *is* the simplest.
    if (soFar == 5) { // Finished case; this is done moving through and found a straight
      return true;
    }
    if (index >= ls.size()) {
      return false;
    }
    // checks to see if there is a one-step or two-step gap, as per loose scoring
    if (ls.get(index) == last + 1 || ls.get(index) == last + 2) {
      return checkDirectlyAscending(ls, ls.get(index), index + 1, soFar + 1);
    }
    if (ls.get(index) == last) { // Repeated elements don't end the streak!
      return checkDirectlyAscending(ls, last, index + 1, soFar);
    }
    return checkDirectlyAscending(ls, ls.get(index), index + 1, 1);
  }

  /**
   * A method used to check for flushes within a hand of cards.
   *
   * @param l a hand which to check.
   * @param checkStraightFlush whether to check a straight flush as well.
   * @return if the hand is a flush.
   */
  protected static boolean checkFlush(List<PlayingCard> l, boolean checkStraightFlush) {
    List<String> ls = new ArrayList<>();
    for (PlayingCard c : l) {
      ls.add(getSuit(c));
    }
    if (ls.size() >= 5) {
      for (String s : ls) {
        if (Collections.frequency(ls, s) >= 5) {
          if (checkStraightFlush) {
            return toStraightFlush(l, s);
          }
          return true;
        }
      }
    }
    return false;
  }

  // ♠ ♣ ♡ ♢
  // NOTE: clubs are converted to spades and diamonds to hearts, because I'm so darn clever ;-]
  protected static String getSuit(PlayingCard c) {
    String s = c.toString();
    String suit;
    if (s.length() == 3) {
      suit = s.substring(2);
    } else {
      suit = s.substring(1);
    }
    if (suit.equals("♣")) {
      return "♠";
    }
    if (suit.equals("♢")) {
      return "♡";
    }
    return suit;
  }

  private static boolean toStraightFlush(List<PlayingCard> l, String toCheck) {
    List<PlayingCard> newl = new ArrayList<>(l);
    newl.removeIf(c -> !(getSuit(c).equals(toCheck))); // Only keep elements of that flush
    return checkStraight(newl);
  }
}
