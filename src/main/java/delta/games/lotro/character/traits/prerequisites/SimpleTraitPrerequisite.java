package delta.games.lotro.character.traits.prerequisites;

import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.utils.Proxy;

/**
 * Simple trait pre-requisite.
 * @author DAM
 */
public class SimpleTraitPrerequisite extends AbstractTraitPrerequisite
{
  private Proxy<TraitDescription> _traitProxy;

  /**
   * Constructor.
   */
  public SimpleTraitPrerequisite()
  {
    _traitProxy=new Proxy<TraitDescription>();
  }

  /**
   * Get the trait proxy.
   * @return the trait proxy.
   */
  public Proxy<TraitDescription> getTraitProxy()
  {
    return _traitProxy;
  }
}
