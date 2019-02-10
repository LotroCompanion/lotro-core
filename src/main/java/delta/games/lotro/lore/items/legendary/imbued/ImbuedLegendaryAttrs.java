package delta.games.lotro.lore.items.legendary.imbued;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.lore.items.legendary.LegendaryAttrs;

/**
 * Attributes of an imbued legendary item instance.
 * @author DAM
 */
public class ImbuedLegendaryAttrs extends LegendaryAttrs
{
  /**
   * Imbued legacy instances.
   */
  private List<ImbuedLegacyInstance> _legacies;

  /**
   * Constructor.
   */
  public ImbuedLegendaryAttrs()
  {
    _legacies=new ArrayList<ImbuedLegacyInstance>();
  }

  /**
   * Add a legacy.
   * @param legacy Legacy to add.
   */
  public void addLegacy(ImbuedLegacyInstance legacy)
  {
    _legacies.add(legacy);
  }

  /**
   * Remove a legacy.
   * @param legacy Legacy to remove.
   */
  public void removeLegacy(ImbuedLegacyInstance legacy)
  {
    _legacies.remove(legacy);
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
      int currentTier=legacy.getCurrentTier();
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
    sb.append("Total tiers=").append(getTotalTiers());
    sb.append(", max total tiers=").append(getMaxTotalTiers());
    sb.append(EndOfLine.NATIVE_EOL);
    int index=1;
    for(ImbuedLegacyInstance legacy : _legacies)
    {
      sb.append("\tLegacy #").append(index).append(": ");
      sb.append(legacy).append(EndOfLine.NATIVE_EOL);
      index++;
    }
    return sb.toString();
  }
}
