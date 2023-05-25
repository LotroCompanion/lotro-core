package delta.games.lotro.lore.agents;

import delta.games.lotro.common.Interactable;

/**
 * Base class for agents (mobs and NPCs).
 * @author DAM
 */
public class AgentDescription implements Interactable
{
  private int _identifier;
  private String _name;

  /**
   * Constructor.
   * @param id Identifier.
   * @param name Name.
   */
  public AgentDescription(int id, String name)
  {
    _identifier=id;
    if (name==null) name="";
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
   * Get the name.
   * @return a name (may be empty but not <code>null</code>)..
   */
  public String getName()
  {
    return _name;
  }
}
