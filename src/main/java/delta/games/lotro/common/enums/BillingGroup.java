package delta.games.lotro.common.enums;

/**
 * Billing group.
 * @author DAM
 */
public class BillingGroup extends LotroEnumEntry
{
  public static final BillingGroup ANY=new BillingGroup();

  @Override
  public String toString()
  {
    return getLabel();
  }
}
