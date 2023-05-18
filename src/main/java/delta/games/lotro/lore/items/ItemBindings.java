package delta.games.lotro.lore.items;

/**
 * Well-known item bindings.
 * @author DAM
 */
public class ItemBindings
{
  /**
   * Item is bound on equip.
   */
  public static final ItemBinding BIND_ON_EQUIP=ItemBinding.getItemBindingByKey("BIND_ON_EQUIP");
  /**
   * Item is bound on acquire.
   */
  public static final ItemBinding BIND_ON_ACQUIRE=ItemBinding.getItemBindingByKey("BIND_ON_ACQUIRE");
  /**
   * Item is bound to account on acquire.
   */
  public static final ItemBinding BOUND_TO_ACCOUNT_ON_ACQUIRE=ItemBinding.getItemBindingByKey("BOUND_TO_ACCOUNT_ON_ACQUIRE");
  /**
   * Item is not bound at all.
   */
  public static final ItemBinding NONE=ItemBinding.getItemBindingByKey("NONE");
}
