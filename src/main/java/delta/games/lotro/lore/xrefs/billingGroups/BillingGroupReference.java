package delta.games.lotro.lore.xrefs.billingGroups;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Billing group reference.
 * @author DAM
 * @param <T> Source type.
 */
public class BillingGroupReference<T>
{
  private T _source;
  private HashSet<BillingGroupRole> _roles;

  /**
   * Constructor.
   * @param source Source.
   * @param role Role.
   */
  public BillingGroupReference(T source, BillingGroupRole role)
  {
    _source=source;
    _roles=new HashSet<BillingGroupRole>();
    _roles.add(role);
  }

  /**
   * Get the source.
   * @return the source.
   */
  public T getSource()
  {
    return _source;
  }

  /**
   * Get the roles of the billing group for the source.
   * @return a list of roles.
   */
  public List<BillingGroupRole> getRoles()
  {
    List<BillingGroupRole> ret=new ArrayList<BillingGroupRole>(_roles);
    return ret;
  }
}
