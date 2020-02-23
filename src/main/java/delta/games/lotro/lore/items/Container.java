package delta.games.lotro.lore.items;

import delta.games.lotro.common.Identifiable;

/**
 * Container-specific data.
 * @author DAM
 */
public class Container implements Identifiable
{
  private int _identifier;

  /**
   * Constructor.
   * @param identifier Item identifier.
   */
  public Container(int identifier)
  {
    _identifier=identifier;
  }

  @Override
  public int getIdentifier()
  {
    return _identifier;
  }
}
