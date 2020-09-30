package delta.games.lotro.lore.agents.mobs;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.lore.agents.AgentClassification;

/**
 * Mob description.
 * @author DAM
 */
public class MobDescription implements Identifiable
{
  private int _identifier;
  private String _name;
  private AgentClassification _classification;

  /**
   * Constructor.
   * @param id Identifier.
   * @param name Name.
   */
  public MobDescription(int id, String name)
  {
    _identifier=id;
    _name=name;
    _classification=new AgentClassification();
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

  /**
   * Get the mob classification.
   * @return a classification.
   */
  public AgentClassification getClassification()
  {
    return _classification;
  }
}
