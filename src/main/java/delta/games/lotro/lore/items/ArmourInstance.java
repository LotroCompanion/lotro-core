package delta.games.lotro.lore.items;

/**
 * Instance of an armour item.
 * @author DAM
 */
public class ArmourInstance extends ItemInstance<Armour>
{
  /**
   * Constructor.
   */
  public ArmourInstance()
  {
    super();
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public ArmourInstance(ArmourInstance source)
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
