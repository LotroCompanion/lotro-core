package delta.games.lotro.lore.items.legendary.non_imbued;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.lore.items.EquipmentLocation;

/**
 * Manager for non-imbued legacies.
 * @author DAM
 */
public class NonImbuedLegaciesManager
{
  private Map<StatDescription,NonImbuedLegacy> _legacies;
  private Map<String,LegaciesAvailableForClassAndSlot> _legaciesUsage;

  /**
   * Constructor.
   */
  public NonImbuedLegaciesManager()
  {
    _legacies=new HashMap<StatDescription,NonImbuedLegacy>();
    _legaciesUsage=new HashMap<String,LegaciesAvailableForClassAndSlot>();
  }

  /**
   * Add a legacy.
   * @param legacy Legacy to add.
   */
  public void addLegacy(NonImbuedLegacy legacy)
  {
    StatDescription stat=legacy.getStat();
    _legacies.put(stat,legacy);
  }

  /**
   * Get the legacy for a given stat.
   * @param stat Stat to use.
   * @return A legacy or <code>null</code> if not found.
   */
  public NonImbuedLegacy getLegacy(StatDescription stat)
  {
    return _legacies.get(stat);
  }

  private String buildKey(CharacterClass characterClass, EquipmentLocation slot)
  {
    return characterClass.getKey()+"-"+slot.getKey();
  }

  /**
   * Register a legacy usage.
   * @param legacy Legacy to use.
   * @param characterClass Involved character class.
   * @param slot Involved slot.
   */
  public void addLegacyUsage(NonImbuedLegacy legacy, CharacterClass characterClass, EquipmentLocation slot)
  {
    String key=buildKey(characterClass,slot);
    LegaciesAvailableForClassAndSlot legacies=_legaciesUsage.get(key);
    if (legacies==null)
    {
      legacies=new LegaciesAvailableForClassAndSlot(characterClass,slot);
      _legaciesUsage.put(key,legacies);
    }
    legacies.addLegacyUsage(legacy);
  }

  /**
   * Get a displayable content of this object.
   * @return a displayable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    for(NonImbuedLegacy legacy : _legacies.values())
    {
      sb.append(legacy);
    }
    List<String> keys=new ArrayList<String>(_legaciesUsage.keySet());
    Collections.sort(keys);
    for(String key : keys)
    {
      sb.append(key).append(EndOfLine.NATIVE_EOL);
      LegaciesAvailableForClassAndSlot availableLegacies=_legaciesUsage.get(key);
      for(NonImbuedLegacy legacy : availableLegacies.getAll())
      {
        sb.append('\t').append(legacy.getStat().getName()).append(EndOfLine.NATIVE_EOL);
      }
    }
    return sb.toString();
  }
}
