package delta.games.lotro.lore.items.scaling;

import org.apache.log4j.Logger;

import delta.common.utils.NumericTools;
import delta.games.lotro.common.progression.ProgressionsManager;
import delta.games.lotro.utils.maths.Progression;

/**
 * Item level munging.
 * @author DAM
 */
public class Munging
{
  private static final Logger LOGGER=Logger.getLogger(Munging.class);

  private Integer _min;
  private Integer _max;
  private Progression _progression;

  /**
   * Constructor.
   * @param min Minimum level/item level (may be <code>null</code>).
   * @param max Maximum level/item level (may be <code>null</code>).
   * @param progression Level to item level curve (may be <code>null</code>).
   */
  public Munging(Integer min, Integer max, Progression progression)
  {
    _min=min;
    _max=max;
    _progression=progression;
  }

  /**
   * Get the minimum level/item level.
   * @return a level (may be <code>null</code>).
   */
  public Integer getMin()
  {
    return _min;
  }

  /**
   * Get the maximum level/item level.
   * @return a level (may be <code>null</code>).
   */
  public Integer getMax()
  {
    return _max;
  }

  /**
   * Get the item to item level curve.
   * @return A progression or <code>null</code>.
   */
  public Progression getProgression()
  {
    return _progression;
  }

  /**
   * Get the item level for a given base level.
   * @param level Base level.
   * @return A level or <code>null</code> if no match.
   */
  public Integer getItemLevel(int level)
  {
    Float itemLevel=_progression.getValue(level);
    if (itemLevel!=null)
    {
      return Integer.valueOf(itemLevel.intValue());
    }
    return null;
  }

  /**
   * Get a string specification from this object.
   * @return A string.
   */
  public String asString()
  {
    return toString();
  }

  /**
   * Build a munging from a string specification.
   * @param mungingStr Input string.
   * @return A new munging.
   */
  public static Munging fromString(String mungingStr)
  {
    int index=mungingStr.indexOf(':');
    String levelsStr=null;
    Integer progressionId=null;
    if (index!=-1)
    {
      levelsStr=mungingStr.substring(0,index);
      progressionId=NumericTools.parseInteger(mungingStr.substring(index+1));
    }
    else
    {
      levelsStr=mungingStr;
    }
    Integer min=null;
    Integer max=null;
    if (levelsStr.length()>0)
    {
      index=levelsStr.indexOf('-');
      String minStr=levelsStr.substring(0,index);
      if (minStr.length()>0)
      {
        min=NumericTools.parseInteger(minStr);
      }
      String maxStr=levelsStr.substring(index+1);
      if (maxStr.length()>0)
      {
        max=NumericTools.parseInteger(maxStr);
      }
    }
    Progression progression=null;
    if (progressionId!=null)
    {
      progression=ProgressionsManager.getInstance().getProgression(progressionId.intValue());
      if (progression==null)
      {
        LOGGER.warn("Progression not found: "+progressionId);
      }
    }
    return new Munging(min,max,progression);
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    if ((_min!=null) || (_max!=null))
    {
      if (_min!=null)
      {
        sb.append(_min);
      }
      sb.append('-');
      if (_max!=null)
      {
        sb.append(_max);
      }
    }
    if (_progression!=null)
    {
      sb.append(':');
      sb.append(_progression.getIdentifier());
    }
    return sb.toString();
  }
}
