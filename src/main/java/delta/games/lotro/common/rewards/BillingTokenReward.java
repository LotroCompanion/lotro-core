package delta.games.lotro.common.rewards;

import delta.games.lotro.common.enums.BillingGroup;

/**
 * Billing-token reward.
 * @author DAM
 */
public class BillingTokenReward extends RewardElement
{
  private BillingGroup _billingGroup;

  /**
   * Constructor.
   * @param billingGroup Targeted billing group.
   */
  public BillingTokenReward(BillingGroup billingGroup)
  {
    _billingGroup=billingGroup;
  }

  /**
   * Get the managed billing group.
   * @return a billing group.
   */
  public BillingGroup getBillingGroup()
  {
    return _billingGroup;
  }

  /**
   * Get the name of the managed billing group.
   * @return a title name.
   */
  public String getName()
  {
    return (_billingGroup!=null)?_billingGroup.getLabel():"";
  }

  @Override
  public String toString()
  {
    return getName();
  }
}
