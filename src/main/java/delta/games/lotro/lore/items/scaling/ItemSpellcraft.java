package delta.games.lotro.lore.items.scaling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.common.utils.NumericTools;
import delta.games.lotro.common.progression.ProgressionsManager;
import delta.games.lotro.utils.maths.Progression;

/**
 * Item spellcraft.
 * @author DAM
 */
public class ItemSpellcraft
{
  private static final Logger LOGGER=LoggerFactory.getLogger(ItemSpellcraft.class);

  private String _propertyName;
  private Progression _progression;

  /**
   * Constructor.
   * @param propertyName Name of the property that rules the spellcraft (provides a value).
   * @param progression Value to spellcraft curve (may be <code>null</code>).
   */
  public ItemSpellcraft(String propertyName, Progression progression)
  {
    _propertyName=propertyName;
    _progression=progression;
  }

  /**
   * Get the property name.
   * @return a property name.
   */
  public String getPropertyName()
  {
    return _propertyName;
  }

  /**
   * Get the value to spellcraft curve.
   * @return A progression or <code>null</code>.
   */
  public Progression getProgression()
  {
    return _progression;
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
   * Build an item spellcraft from a string specification.
   * @param spellcraftStr Input string.
   * @return A new item spellcraft.
   */
  public static ItemSpellcraft fromString(String spellcraftStr)
  {
    int index=spellcraftStr.indexOf(':');
    String propertyName=null;
    Integer progressionId=null;
    if (index!=-1)
    {
      propertyName=spellcraftStr.substring(0,index);
      progressionId=NumericTools.parseInteger(spellcraftStr.substring(index+1));
    }
    else
    {
      propertyName=spellcraftStr;
    }
    Progression progression=null;
    if (progressionId!=null)
    {
      progression=ProgressionsManager.getInstance().getProgression(progressionId.intValue());
      if (progression==null)
      {
        LOGGER.warn("Progression not found: {}",progressionId);
      }
    }
    return new ItemSpellcraft(propertyName,progression);
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_propertyName);
    if (_progression!=null)
    {
      sb.append(':');
      sb.append(_progression.getIdentifier());
    }
    return sb.toString();
  }
}
