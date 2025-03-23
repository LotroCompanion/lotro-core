package delta.games.lotro.lore.collections.birds;

import delta.games.lotro.common.Identifiable;
import delta.games.lotro.common.Named;
import delta.games.lotro.lore.items.Item;

/**
 * Description of a bird.
 * @author DAM
 */
public class BirdDescription implements Identifiable,Named
{
  private Item _item;
  private int _callSoundID;
  private int _typeCode;
  private String _elvishName;
  private int _iconID;
  private int _largeIconID;

  /**
   * Constructor.
   * @param item Underlying item.
   */
  public BirdDescription(Item item)
  {
    _item=item;
  }

  @Override
  public int getIdentifier()
  {
    return _item.getIdentifier();
  }

  @Override
  public String getName()
  {
    return _item.getName();
  }

  /**
   * Get the underlying item.
   * @return the underlying item.
   */
  public Item getItem()
  {
    return _item;
  }

  /**
   * Get the bird call sound identifier.
   * @return A sound identifier.
   */
  public int getCallSoundID()
  {
    return _callSoundID;
  }

  /**
   * Set the bird call sound identifier.
   * @param callSoundID the identifier to set.
   */
  public void setCallSoundID(int callSoundID)
  {
    _callSoundID=callSoundID;
  }

  /**
   * Get the bird type code.
   * @return the bird type code.
   */
  public int getTypeCode()
  {
    return _typeCode;
  }

  /**
   * Set the bird type code.
   * @param typeCode the code to set.
   */
  public void setTypeCode(int typeCode)
  {
    _typeCode=typeCode;
  }

  /**
   * Get the elvish name for this bird.
   * @return an elvish name.
   */
  public String getElvishName()
  {
    return _elvishName;
  }

  /**
   * Set the elvish name for this bird.
   * @param elvishName the elvish name to set.
   */
  public void setElvishName(String elvishName)
  {
    _elvishName=elvishName;
  }

  /**
   * Get the icon ID for this bird?
   * @return an icon identifier.
   */
  public int getIconID()
  {
    return _iconID;
  }

  /**
   * Set the icon ID for this bird?
   * @param iconID the icon identifier to set.
   */
  public void setIconID(int iconID)
  {
    _iconID=iconID;
  }

  /**
   * Get the large icon ID for this bird?
   * @return an icon identifier.
   */
  public int getLargeIconID()
  {
    return _largeIconID;
  }

  /**
   * Set the large icon ID for this bird?
   * @param largeIconID the icon identifier to set.
   */
  public void setLargeIconID(int largeIconID)
  {
    _largeIconID=largeIconID;
  }
}
