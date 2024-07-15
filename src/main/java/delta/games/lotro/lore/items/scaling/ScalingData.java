package delta.games.lotro.lore.items.scaling;

/**
 * Item scaling data.
 * @author DAM
 */
public class ScalingData
{
  private Munging _munging;
  private ItemLevelBonus _itemLevelBonus;

  /**
   * Constructor.
   * @param munging Munging.
   * @param itemLevelBonus Item level bonus.
   */
  public ScalingData(Munging munging, ItemLevelBonus itemLevelBonus)
  {
    _munging=munging;
    _itemLevelBonus=itemLevelBonus;
  }

  /**
   * Get the munging information.
   * @return the munging information.
   */
  public Munging getMunging()
  {
    return _munging;
  }

  /**
   * Get the item level bonus information.
   * @return the item level bonus (may be <code>null</code>).
   */
  public ItemLevelBonus getItemLevelBonus()
  {
    return _itemLevelBonus;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder("Munging: ");
    sb.append(_munging);
    if (_itemLevelBonus!=null)
    {
      sb.append(" ; item Level Bonus: ");
      sb.append(_itemLevelBonus);
    }
    return sb.toString();
  }

  /**
   * Build a scaling from a string specification.
   * @param scalingStr Input string.
   * @return A new scaling.
   */
  public static ScalingData fromString(String scalingStr)
  {
    int index=scalingStr.indexOf(';');
    String mungingStr=scalingStr;
    String bonusStr=null;
    if (index!=-1)
    {
      mungingStr=scalingStr.substring(0,index);
      bonusStr=scalingStr.substring(index+1);
    }
    Munging munging=Munging.fromString(mungingStr);
    ItemLevelBonus bonus=null;
    if (bonusStr!=null)
    {
      bonus=ItemLevelBonus.fromString(bonusStr);
    }
    return new ScalingData(munging,bonus);
  }

  /**
   * Get a string specification from this object.
   * @return A string.
   */
  public String asString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_munging.asString());
    if (_itemLevelBonus!=null)
    {
      sb.append(';');
      sb.append(_itemLevelBonus.asString());
    }
    return sb.toString();
  }
}
