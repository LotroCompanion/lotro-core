package delta.games.lotro.common.effects;

import delta.games.lotro.common.geo.Position;

/**
 * Recall effect.
 * @author DAM
 */
public class RecallEffect extends Effect
{
  private Position _position;

  /**
   * Constructor.
   */
  public RecallEffect()
  {
    super();
  }

  /**
   * Get the target position.
   * @return A position or <code>null</code> if none.
   */
  public Position getPosition()
  {
    return _position;
  }

  /**
   * Set the target position.
   * @param position Position to set.
   */
  public void setPosition(Position position)
  {
    _position=position;
  }
}
