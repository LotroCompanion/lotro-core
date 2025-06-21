package delta.games.lotro.lore.travels.map;

import java.awt.Rectangle;

/**
 * Label on the travels map.
 * @author DAM
 */
public class TravelsMapLabel
{
  private Rectangle _uiPosition;
  private String _text;

  /**
   * Constructor.
   * @param uiPosition UI position.
   * @param text Text.
   */
  public TravelsMapLabel(Rectangle uiPosition, String text)
  {
    _uiPosition=uiPosition;
    _text=text;
  }

  /**
   * Get the UI position.
   * @return the UI position.
   */
  public Rectangle getUIPosition()
  {
    return _uiPosition;
  }

  /**
   * Get the text.
   * @return the text.
   */
  public String getText()
  {
    return _text;
  }

  @Override
  public String toString()
  {
    return _text;
  }
}
