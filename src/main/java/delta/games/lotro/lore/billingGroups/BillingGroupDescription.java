package delta.games.lotro.lore.billingGroups;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.common.enums.BillingGroup;
import delta.games.lotro.lore.titles.TitleDescription;

/**
 * Description of a billing group.
 * @author DAM
 */
public class BillingGroupDescription implements Identifiable,Named
{
  private BillingGroup _id;
  private List<TitleDescription> _accountTitles;

  /**
   * Constructor.
   * @param id Billing group.
   */
  public BillingGroupDescription(BillingGroup id)
  {
    _id=id;
    _accountTitles=new ArrayList<TitleDescription>();
  }

  @Override
  public int getIdentifier()
  {
    return _id.getCode();
  }

  @Override
  public String getName()
  {
    return _id.getLabel();
  }

  /**
   * Get the managed billing group.
   * @return A billing group.
   */
  public BillingGroup getId()
  {
    return _id;
  }

  /**
   * Add an account-wide title.
   * @param title Title to add.
   */
  public void addAccountTitle(TitleDescription title)
  {
    if (!_accountTitles.contains(title))
    {
      _accountTitles.add(title);
    }
  }

  /**
   * Get the associated account-wide titles.
   * @return A possibly empty but never <code>null</code> list of titles.
   */
  public List<TitleDescription> getAccountTitles()
  {
    return new ArrayList<TitleDescription>(_accountTitles);
  }
}
