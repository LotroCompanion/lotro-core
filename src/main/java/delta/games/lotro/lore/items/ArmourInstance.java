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
}
