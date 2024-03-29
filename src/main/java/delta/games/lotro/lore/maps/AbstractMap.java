package delta.games.lotro.lore.maps;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;

/**
 * Base class for maps (Dungeon and ParchmentMap)
 * @author DAM
 */
public class AbstractMap implements Identifiable,Named
{
  protected int _identifier;
  protected String _name;

  /**
   * Constructor.
   * @param identifier Internal identifier.
   * @param name Map name.
   */
  public AbstractMap(int identifier, String name)
  {
    _identifier=identifier;
    if (name==null)
    {
      name="";
    }
    _name=name;
  }

  /**
   * Get the internal identifier.
   * @return the internal identifier.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Get the map name.
   * @return the map name.
   */
  public String getName()
  {
    return _name;
  }

  @Override
  public String toString()
  {
    return _identifier+" - "+_name;
  }
}
