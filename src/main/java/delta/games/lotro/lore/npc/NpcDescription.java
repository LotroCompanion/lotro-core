package delta.games.lotro.lore.npc;

import delta.games.lotro.common.Identifiable;

/**
 * NPC description.
 * @author DAM
 */
public class NpcDescription implements Identifiable
{
  private int _identifier;
  private String _name;

  /**
   * Constructor.
   * @param id Identifier.
   * @param name Name.
   */
  public NpcDescription(int id, String name)
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
   * Get the NPC name.
   * @return a name.
   */
  public String getName()
  {
    return _name;
  }
}
