package delta.games.lotro.lore.items;

/**
 * Item binding.
 * @author DAM
 */
public enum ItemBinding
{
  /**
   * Item is bound on equip.
   */
  BIND_ON_EQUIP("Bind on Equip"),
  /**
   * Item is bound on acquire.
   */
  BIND_ON_ACQUIRE("Bind on Acquire"),
  /**
   * Item is bound to account on acquire.
   */
  BOUND_TO_ACCOUNT_ON_ACQUIRE("Bind to Account on Acquire"),
  /**
   * Item is not bound at all.
   */
  NONE("None");

  private String _label;

  private ItemBinding(String label)
  {
    _label=label;
  }

  /**
   * Get a readable label for this object.
   * @return a readable label.
   */
  public String getLabel()
  {
    return _label;
  }

  @Override
  public String toString()
  {
    return _label;
  }

  /**
   * All.
   */
  public static final ItemBinding[] ALL={ BIND_ON_ACQUIRE, BOUND_TO_ACCOUNT_ON_ACQUIRE, BIND_ON_EQUIP, NONE };
}
