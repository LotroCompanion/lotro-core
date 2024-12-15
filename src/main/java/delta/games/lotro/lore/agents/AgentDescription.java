package delta.games.lotro.lore.agents;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Interactable;
import delta.games.lotro.common.effects.EffectGenerator;

/**
 * Base class for agents (mobs and NPCs).
 * @author DAM
 */
public class AgentDescription implements Interactable
{
  private int _identifier;
  private String _name;
  private List<EffectGenerator> _startupEffects;

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
    _startupEffects=new ArrayList<EffectGenerator>();
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

  /**
   * Add a 'startup' effect.
   * @param generator Effect generator.
   */
  public void addStartupEffect(EffectGenerator generator)
  {
    _startupEffects.add(generator);
  }

  /**
   * Get the 'startup' effects.
   * @return A list of effect generators.
   */
  public List<EffectGenerator> getStartupEffects()
  {
    return _startupEffects;
  }
}
