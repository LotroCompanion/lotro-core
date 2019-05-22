package delta.games.lotro.lore.mobs;

import delta.games.lotro.common.Identifiable;

/**
 * Mob description.
 * @author DAM
 */
public class MobDescription implements Identifiable
{
  private int _identifier;
  private String _name;

  /**
   * Constructor.
   * @param id Identifier.
   * @param name Name.
   */
  public MobDescription(int id, String name)
  {
    _identifier=id;
    _name=name;
  }

  /**
   * Get the identifier.
   * @return an identifier.
   */
  public int getIdentifier()
  {
    return _identifier;
  }

  /**
   * Get the mob name.
   * @return a name.
   */
  public String getName()
  {
    return _name;
  }
}
