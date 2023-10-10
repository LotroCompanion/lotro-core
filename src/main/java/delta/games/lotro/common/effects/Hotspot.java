package delta.games.lotro.common.effects;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;

/**
 * Hotspot.
 * @author DAM
 */
public class Hotspot implements Identifiable,Named
{
  private int _identifier;
  private String _name;
  private List<EffectGenerator> _effects;

  /**
   * Constructor.
   * @param identifier Identifier.
   */
  public Hotspot(int identifier)
  {
    _identifier=identifier;
    _name="";
    _effects=new ArrayList<EffectGenerator>();
  }

  public int getIdentifier()
  {
    return _identifier;
  }

  @Override
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name.
   * @param name Name to set.
   */
  public void setName(String name)
  {
    if (name==null)
    {
      name="";
    }
    _name=name;
  }

  /**
   * Add a generated effect.
   * @param generator Generator to add.
   */
  public void addEffect(EffectGenerator generator)
  {
    _effects.add(generator);
  }
  
  /**
   * Get the managed effects.
   * @return A list of effects.
   */
  public List<EffectGenerator> getEffects()
  {
    return _effects;
  }
}
