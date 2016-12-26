package delta.games.lotro.lore.items;

import delta.common.utils.text.EndOfLine;

/**
 * Armour description.
 * @author DAM
 */
public class Armour extends Item
{
  private int _armourValue;

  private ArmourType _type;

  /**
   * Constructor.
   */
  public Armour()
  {
    super();
    setCategory(ItemCategory.ARMOUR);
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public Armour(Armour source)
  {
    this();
    copyFrom(source);
  }

  /**
   * Get the armour value for this item.
   * @return an armour value.
   */
  public int getArmourValue()
  {
    return _armourValue;
  }

  /**
   * Set the armour value for this item.
   * @param armourValue Armour value to set.
   */
  public void setArmourValue(int armourValue)
  {
    _armourValue=armourValue;
  }
  
  /**
   * Get armour type.
   * @return an armour type.
   */
  public ArmourType getArmourType()
  {
    return _type;
  }

  /**
   * Set armour type.
   * @param type Armour type to set.
   */
  public void setArmourType(ArmourType type)
  {
    _type=type;
  }

  /**
   * Copy item data from a source.
   * @param item Source item.
   */
  public void copyFrom(Item item)
  {
    super.copyFrom(item);
    if (item instanceof Armour)
    {
      Armour armour=(Armour)item;
      _armourValue=armour._armourValue;
      _type=armour._type;
    }
  }

  /**
   * Dump the contents of this armour as a string.
   * @return A readable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    String itemDump=super.dump();
    sb.append(itemDump);
    sb.append(EndOfLine.NATIVE_EOL);
    sb.append("Armour value=");
    sb.append(_armourValue);
    sb.append(", Armour type=");
    sb.append(_type);
    return sb.toString();
  }
}
