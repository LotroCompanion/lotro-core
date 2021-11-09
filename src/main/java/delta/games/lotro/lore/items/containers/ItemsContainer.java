package delta.games.lotro.lore.items.containers;

import delta.games.lotro.lore.items.Container;

/**
 * Container-specific data (items).
 * @author DAM
 */
public class ItemsContainer extends Container
{
  private LootTables _lootTables;
  private ContainerBindingPolicy _bindingPolicy;
  private boolean _useCharacterForMunging;
  private ContainerOpenPolicy _openPolicy;

  /**
   * Constructor.
   * @param identifier Item identifier.
   */
  public ItemsContainer(int identifier)
  {
    super(identifier);
    _lootTables=new LootTables();
    _bindingPolicy=ContainerBindingPolicy.NONE;
    _useCharacterForMunging=false;
    _openPolicy=ContainerOpenPolicy.AUTOMATIC;
  }

  /**
   * Get the loot tables.
   * @return a loot tables manager.
   */
  public LootTables getLootTables()
  {
    return _lootTables;
  }

  /**
   * Get the container binding policy.
   * @return a binding policy.
   */
  public ContainerBindingPolicy getBindingPolicy()
  {
    return _bindingPolicy;
  }

  /**
   * Set the container binding policy.
   * @param bindingPolicy the policy to set.
   */
  public void setBindingPolicy(ContainerBindingPolicy bindingPolicy)
  {
    _bindingPolicy=bindingPolicy;
  }

  /**
   * Indicates if munging is used to scale the result items using the character level.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isUseCharacterForMunging()
  {
    return _useCharacterForMunging;
  }

  /**
   * Set the 'useCharacterForMunging' flag.
   * @param useCharacterForMunging value to set.
   */
  public void setUseCharacterForMunging(boolean useCharacterForMunging)
  {
    _useCharacterForMunging=useCharacterForMunging;
  }

  /**
   * Get the container open policy.
   * @return an open policy.
   */
  public ContainerOpenPolicy getOpenPolicy()
  {
    return _openPolicy;
  }

  /**
   * Set the container open policy.
   * @param openPolicy the policy to set
   */
  public void setOpenPolicy(ContainerOpenPolicy openPolicy)
  {
    _openPolicy=openPolicy;
  }
}
