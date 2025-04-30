package cs3500.pokerpolygons.view;

import java.io.IOException;

/**
 * An abstract class representing a textual view of a game of PokerPolygons. Can be used to
 * represent triangles or rectangles.
 */
public abstract class AbstractPokerPolygonsTextualView implements PokerPolygonsTextualView {

  protected String space(String s) {
    if (s.length() == 2) {
      return " ";
    }
    return "";
  }

  /**
   * Renders the current game of PokerTriangles as text.
   *
   * @param out where to send the model information to
   * @throws IOException if the rendering fails for some reason
   */
  @Override
  public void render(Appendable out) throws IOException {
    out.append(this.toString());
  }
}
