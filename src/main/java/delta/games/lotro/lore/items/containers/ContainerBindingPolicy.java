package delta.games.lotro.lore.items.containers;

/**
 * Binding policy for a container.
 * @author DAM
 */
public enum ContainerBindingPolicy
{
  /**
   * Binds contained items to the character that opens the container.
   */
  BIND_ON_CHARACTER,
  /**
   * Binds contained items to the account of the character that opens the container.
   */
  BIND_ON_ACCOUNT,
  /**
   * None.
   */
  NONE
}
