package delta.games.lotro.lore.items.legendary.imbued;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.lore.items.legendary.LegendaryConstants;

/**
 * Attributes of an imbued legendary item instance.
 * @author DAM
 */
public class ImbuedLegendaryInstanceAttrs
{
  /**
   * Imbued legacy instances.
   */
  private List<ImbuedLegacyInstance> _legacies;

  /**
   * Constructor.
   */
  public ImbuedLegendaryInstanceAttrs()
  {
    _legacies=new ArrayList<ImbuedLegacyInstance>();
    init();
  }

  /**
   * Copy constructor.
   * @param source Source.
   */
  public ImbuedLegendaryInstanceAttrs(ImbuedLegendaryInstanceAttrs source)
  {
    _legacies=new ArrayList<ImbuedLegacyInstance>();
    for(ImbuedLegacyInstance legacyInstance : source._legacies)
    {
      ImbuedLegacyInstance copy=new ImbuedLegacyInstance(legacyInstance);
      _legacies.add(copy);
    }
  }

  private void init()
  {
    for(int i=0;i<LegendaryConstants.MAX_LEGACIES+1;i++)
    {
      ImbuedLegacyInstance legacy=new ImbuedLegacyInstance();
      _legacies.add(legacy);
    }
  }

  /**
   * Get the number of legacies.
   * @return a legacies count.
   */
  public int getNumberOfLegacies()
  {
    return _legacies.size();
  }

  /**
   * Get the main legacy.
   * @return the main legacy.
   */
  public ImbuedLegacyInstance getMainLegacy()
  {
    return _legacies.get(0);
  }

  /**
   * Get the legacy at the given index.
   * @param index An index, starting at 0.
   * @return A legacy or <code>null</code> if not set.
   */
  public ImbuedLegacyInstance getLegacy(int index)
  {
    return _legacies.get(index);
  }

  /**
   * Get a list of all standard legacies.
   * @return a list of all standard legacies.
   */
  public List<ImbuedLegacyInstance> getStandardLegacies()
  {
    List<ImbuedLegacyInstance> legacies=new ArrayList<ImbuedLegacyInstance>(_legacies);
    // Remove main legacy
    legacies.remove(0);
    return legacies;
  }

  /**
   * Get a list of all legacies.
   * @return a list of all legacies.
   */
  public List<ImbuedLegacyInstance> getLegacies()
  {
    return new ArrayList<ImbuedLegacyInstance>(_legacies);
  }

  /**
   * Get the total number of tiers.
   * @return the total number of tiers.
   */
  public int getTotalTiers()
  {
    int ret=0;
    for(ImbuedLegacyInstance legacy : _legacies)
    {
      int currentTier=legacy.getCurrentLevel();
      ret+=currentTier;
    }
    return ret;
  }

  /**
   * Get the maximum total number of tiers.
   * @return the maximum total number of tiers.
   */
  public int getMaxTotalTiers()
  {
    int ret=0;
    for(ImbuedLegacyInstance legacy : _legacies)
    {
      int currentMaxTier=legacy.getCurrentMaxTier();
      ret+=currentMaxTier;
    }
    return ret;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Imbued: Total tiers=").append(getTotalTiers());
    sb.append(", max total tiers=").append(getMaxTotalTiers());
    sb.append(EndOfLine.NATIVE_EOL);
    int index=1;
    for(ImbuedLegacyInstance legacy : _legacies)
    {
      sb.append("\tLegacy #").append(index).append(": ");
      sb.append(legacy).append(EndOfLine.NATIVE_EOL);
      index++;
    }
    return sb.toString().trim();
  }
}
