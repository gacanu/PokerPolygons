package cs3500.pokerpolygons;

import static java.lang.Integer.parseInt;

import cs3500.pokerpolygons.controller.PokerPolygonsTextualController;
import cs3500.pokerpolygons.model.hw02.PlayingCard;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.model.hw04.PokerPolygonsBuilder;
import cs3500.pokerpolygons.view.PokerRectanglesTextualView;
import cs3500.pokerpolygons.view.PokerTrianglesTextualView;

import java.io.InputStreamReader;

/** A class that plays a game of PokerPolygons when Main() is called. */
public class PokerPolygonsGame {

  private static boolean checkValid(String s) {
    try {
      parseInt(s);
    } catch (NumberFormatException e) {
      return true;
    }
    return parseInt(s) <= 0;
  }

  /**
   * Runs the game with a standard ruleset.
   *
   * @param args arguments to give the main function.
   */
  public static void main(String[] args) {
    PokerPolygonsTextualController controller =
        new PokerPolygonsTextualController(new InputStreamReader(System.in), System.out);
    PokerPolygonsBuilder builder = new PokerPolygonsBuilder();
    PokerPolygons<PlayingCard> game;
    int index = 0;
    if (args.length > 0) {
      for (String s : args) {
        if (index >= 1 && checkValid(s)) {
          return; // Stops the function if given invalid or weird input (negatives, not numbers,
          // etc.)
        }
        index += 1;
      }
      String firstArg = args[0];
      switch (firstArg) {
        case "tri":
          builder.setType(PokerPolygonsBuilder.GameType.TRI);
          game = readyTriangle(args, builder);
          break;
        case "loose":
          builder.setType(PokerPolygonsBuilder.GameType.LOOSE);
          game = readyTriangle(args, builder);
          break;
        case "rectangle":
          builder.setType(PokerPolygonsBuilder.GameType.RECT);
          game = readyRect(args, builder);
          break;
        default:
          throw new IllegalArgumentException(
              "First argument must be one of 'tri', 'loose', or 'rect'.");
      }
      if (args.length > 1 && (firstArg.equals("tri") || firstArg.equals("loose"))) {
        controller.playGame(
            game, new PokerTrianglesTextualView(game), game.getNewDeck(), true, parseInt(args[1]));
        return;
      }
      if (args.length > 1 && firstArg.equals("rectangle")) {
        controller.playGame(
            game, new PokerRectanglesTextualView(game), game.getNewDeck(), true, parseInt(args[1]));
        return;
      }
      if (args.length == 1 && (firstArg.equals("tri") || firstArg.equals("loose"))) {
        controller.playGame(game, new PokerTrianglesTextualView(game), game.getNewDeck(), true, 1);
        return;
      }
      if (args.length == 1 && firstArg.equals("rectangle")) {
        controller.playGame(game, new PokerRectanglesTextualView(game), game.getNewDeck(), true, 1);
      }
    } else {
      throw new IllegalArgumentException("No command line args! Try 'tri', 'loose', or 'rect'.");
    }
  }

  private static PokerPolygons<PlayingCard> readyRect(String[] args, PokerPolygonsBuilder game) {
    switch (args.length) {
      case 1:
      case 2:
        return game.build();
      case 3:
        return game.setHeight(parseInt(args[2])).build();
      default:
        return game.setWidth(parseInt(args[2]))
            .setHeight(parseInt(args[3]))
            .build(); // For arg size >=3...
    }
  }

  private static PokerPolygons<PlayingCard> readyTriangle(
      String[] args, PokerPolygonsBuilder game) {
    switch (args.length) {
      case 1:
      case 2:
        return game.build();
      default:
        return game.setSideLength(parseInt(args[2])).build(); // Argument size 3 or more...
    }
  }
}
