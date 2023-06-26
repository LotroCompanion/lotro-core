package delta.games.lotro.common.rewards;

import delta.games.lotro.character.traits.TraitDescription;

/**
 * Trait reward.
 * @author DAM
 */
public class TraitReward extends RewardElement
{
  private TraitDescription _trait;

  /**
   * Constructor.
   * @param trait Trait.
   */
  public TraitReward(TraitDescription trait)
  {
    _trait=trait;
  }

  /**
   * Get the trait.
   * @return a trait or <code>null</code> if not set.
   */
  public TraitDescription getTrait()
  {
    return _trait;
  }

  /**
   * Get the name of the rewarded trait.
   * @return a trait name.
   */
  public String getName()
  {
    return (_trait!=null)?_trait.getName():"?";
  }

  @Override
  public String toString()
  {
    return getName();
  }
}
