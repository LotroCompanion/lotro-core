package delta.games.lotro.common.rewards;

import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.utils.Proxy;

/**
 * Trait reward.
 * @author DAM
 */
public class TraitReward extends RewardElement
{
  private Proxy<TraitDescription> _trait;

  /**
   * Constructor.
   * @param traitProxy Trait proxy.
   */
  public TraitReward(Proxy<TraitDescription> traitProxy)
  {
    _trait=traitProxy;
  }

  /**
   * Get the trait proxy.
   * @return a trait proxy or <code>null</code> if not set.
   */
  public Proxy<TraitDescription> getTraitProxy()
  {
    return _trait;
  }

  /**
   * Get the name of the rewarded trait.
   * @return a trait name.
   */
  public String getName()
  {
    return _trait.getName();
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    if (_trait!=null)
    {
      sb.append(_trait.getName());
    }
    else
    {
      sb.append('?');
    }
    return sb.toString();
  }
}
