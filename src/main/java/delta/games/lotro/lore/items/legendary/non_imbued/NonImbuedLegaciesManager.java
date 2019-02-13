package delta.games.lotro.lore.items.legendary.non_imbued;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Effect;
import delta.games.lotro.common.constraints.ClassAndSlot;
import delta.games.lotro.common.constraints.ClassAndSlotFilter;
import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.lore.items.EquipmentLocation;
import delta.games.lotro.lore.items.legendary.AbstractLegacy;

/**
 * Manager for non-imbued legacies.
 * @author DAM
 */
public class NonImbuedLegaciesManager
{
  private Map<StatDescription,TieredNonImbuedLegacy> _tieredLegacies;
  private Map<Integer,DefaultNonImbuedLegacy> _defaultLegacies;

  /**
   * Constructor.
   */
  public NonImbuedLegaciesManager()
  {
    _tieredLegacies=new HashMap<StatDescription,TieredNonImbuedLegacy>();
    _defaultLegacies=new HashMap<Integer,DefaultNonImbuedLegacy>();
  }

  /**
   * Add a legacy.
   * @param legacy Legacy to add.
   */
  public void addTieredLegacy(TieredNonImbuedLegacy legacy)
  {
    StatDescription stat=legacy.getStat();
    _tieredLegacies.put(stat,legacy);
  }

  /**
   * Add a legacy.
   * @param legacy Legacy to add.
   */
  public void addDefaultLegacy(DefaultNonImbuedLegacy legacy)
  {
    Effect effect=legacy.getEffect();
    Integer identifier=Integer.valueOf(effect.getIdentifier());
    _defaultLegacies.put(identifier,legacy);
  }

  /**
   * Get the legacy for a given stat.
   * @param stat Stat to use.
   * @return A legacy or <code>null</code> if not found.
   */
  public TieredNonImbuedLegacy getLegacy(StatDescription stat)
  {
    return _tieredLegacies.get(stat);
  }

  /**
   * Get a default legacy by its identifier.
   * @param identifier Identifier to use.
   * @return A legacy or <code>null</code> if not found.
   */
  public DefaultNonImbuedLegacy getDefaultLegacy(int identifier)
  {
    return _defaultLegacies.get(Integer.valueOf(identifier));
  }

  /**
   * Register a legacy usage.
   * @param legacy Legacy to use.
   * @param characterClass Involved character class.
   * @param slot Involved slot.
   */
  public void registerLegacyUsage(AbstractNonImbuedLegacy legacy, CharacterClass characterClass, EquipmentLocation slot)
  {
    if (isAllowedCombinaison(characterClass,slot))
    {
      CompoundFilter<ClassAndSlot> constraints=legacy.getClassAndSlotFilter();
      Filter<ClassAndSlot> newConstraint=new ClassAndSlotFilter(characterClass,slot);
      constraints.addFilter(newConstraint);
    }
  }

  private boolean isAllowedCombinaison(CharacterClass characterClass, EquipmentLocation slot)
  {
    if (slot==EquipmentLocation.RANGED_ITEM)
    {
      if ((characterClass!=CharacterClass.HUNTER) && (characterClass!=CharacterClass.WARDEN))
      {
        return false;
      }
    }
    if (slot==EquipmentLocation.CLASS_SLOT)
    {
      if ((characterClass==CharacterClass.HUNTER) || (characterClass==CharacterClass.WARDEN))
      {
        return false;
      }
    }
    return true;
  }

  /**
   * Get all tiered legacies for a given character class and slot.
   * @param characterClass Targeted character class.
   * @param slot Targeted slot.
   * @return a possibly empty but not <code>null</code> list of legacies.
   */
  public List<TieredNonImbuedLegacy> getTieredLegacies(CharacterClass characterClass, EquipmentLocation slot)
  {
    return filterLegacies(characterClass,slot,_tieredLegacies.values());
  }

  /**
   * Get all tiered legacies for a given character class and slot.
   * @param characterClass Targeted character class.
   * @param slot Targeted slot.
   * @return a possibly empty but not <code>null</code> list of legacies.
   */
  public List<DefaultNonImbuedLegacy> getDefaultLegacies(CharacterClass characterClass, EquipmentLocation slot)
  {
    return filterLegacies(characterClass,slot,_defaultLegacies.values());
  }

  private <T extends AbstractLegacy> List<T> filterLegacies(CharacterClass characterClass, EquipmentLocation slot, Collection<T> legacies)
  {
    List<T> ret=new ArrayList<T>();
    ClassAndSlot classAndSlot=new ClassAndSlot(characterClass,slot);
    for(T legacy : legacies)
    {
      Filter<ClassAndSlot> constraint=legacy.getClassAndSlotFilter();
      boolean ok=((constraint!=null) && (constraint.accept(classAndSlot)));
      if (ok)
      {
        ret.add(legacy);
      }
    }
    return ret;
  }

  /**
   * Get a displayable content of this object.
   * @return a displayable string.
   */
  public String dump()
  {
    StringBuilder sb=new StringBuilder();
    for(TieredNonImbuedLegacy legacy : _tieredLegacies.values())
    {
      sb.append(legacy.dump());
    }
    for(DefaultNonImbuedLegacy legacy : _defaultLegacies.values())
    {
      sb.append(legacy).append(EndOfLine.NATIVE_EOL);
    }
    return sb.toString();
  }
}
