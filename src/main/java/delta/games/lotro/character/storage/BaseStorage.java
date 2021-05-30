package delta.games.lotro.character.storage;

/**
 * Base class for storage containers.
 * @author DAM
 */
public abstract class BaseStorage
{
  /**
   * Get the number of slots used.
   * @return a slots count.
   */
  public abstract int getUsed();

  /**
   * Get the total number of slots.
   * @return a slots count.
   */
  public abstract int getCapacity();
}
