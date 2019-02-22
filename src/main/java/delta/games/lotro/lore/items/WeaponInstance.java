package delta.games.lotro.lore.items;

/**
 * Weapon instance.
 * @param <T> Type of the reference item.
 * @author DAM
 */
public class WeaponInstance<T extends Weapon> extends ItemInstance<T>
{
  /**
   * Constructor.
   */
  public WeaponInstance()
  {
    super();
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public WeaponInstance(WeaponInstance<T> source)
  {
    this();
    copyFrom(source);
  }

  /**
   * Copy item instance data from a source.
   * @param itemInstance Source item instance.
   */
  @Override
  public void copyFrom(ItemInstance<?> itemInstance)
  {
    super.copyFrom(itemInstance);
  }
}
