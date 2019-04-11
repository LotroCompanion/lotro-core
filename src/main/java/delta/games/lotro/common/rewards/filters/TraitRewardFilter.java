package delta.games.lotro.common.rewards.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.common.Trait;
import delta.games.lotro.common.rewards.Rewards;

/**
 * Filter for rewards that contain a trait.
 * @author DAM
 */
public class TraitRewardFilter implements Filter<Rewards>
{
  private String _trait;

  /**
   * Constructor.
   * @param trait Trait to select (may be <code>null</code>).
   */
  public TraitRewardFilter(String trait)
  {
    _trait=trait;
  }

  /**
   * Get the trait to use.
   * @return A trait or <code>null</code>.
   */
  public String getTrait()
  {
    return _trait;
  }

  /**
   * Set the trait to select.
   * @param trait Trait to use, may be <code>null</code>.
   */
  public void setTrait(String trait)
  {
    _trait=trait;
  }

  public boolean accept(Rewards rewards)
  {
    if (_trait==null)
    {
      return true;
    }
    Trait[] traits=rewards.getTraits();
    if (traits!=null)
    {
      for(Trait trait : traits)
      {
        if (_trait.equals(trait.getName()))
        {
          return true;
        }
      }
    }
    return false;
  }
}
