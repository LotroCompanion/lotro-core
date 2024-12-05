package delta.games.lotro.lore.agents.mobs.loot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import delta.games.lotro.common.enums.MobType;
import delta.games.lotro.common.enums.Species;
import delta.games.lotro.common.enums.SubSpecies;

/**
 * Gathers the loot entries for a given species/subspecies/mob type.
 * @author DAM
 */
public class GenericMobLootSpec
{
  private static final Logger LOGGER=LoggerFactory.getLogger(GenericMobLootSpec.class);

  private Species _species;
  private SubSpecies _subSpecies;
  private MobType _mobType;
  private Map<Integer,GenericMobLootEntry> _levelEntries;

  /**
   * Constructor.
   * @param species Species.
   * @param subSpecies Subspecies (may be <code>null</code>).
   * @param mobType Mob type (may be <code>null</code>).
   */
  public GenericMobLootSpec(Species species, SubSpecies subSpecies, MobType mobType)
  {
    _species=species;
    _subSpecies=subSpecies;
    _mobType=mobType;
    _levelEntries=new HashMap<Integer,GenericMobLootEntry>();
  }

  /**
   * Get the species.
   * @return the species.
   */
  public Species getSpecies()
  {
    return _species;
  }

  /**
   * Get the subspecies.
   * @return A subspecies or <code>null</code>.
   */
  public SubSpecies getSubSpecies()
  {
    return _subSpecies;
  }

  /**
   * Get the mob type.
   * @return A mob type or <code>null</code>.
   */
  public MobType getMobType()
  {
    return _mobType;
  }

  /**
   * Add an entry.
   * @param entry Entry to add.
   */
  public void addLevelEntry(GenericMobLootEntry entry)
  {
    Integer key=Integer.valueOf(entry.getLevel());
    GenericMobLootEntry old=_levelEntries.put(key,entry);
    if (old!=null)
    {
      LOGGER.warn("Override of a level entry: old={}, new={}",old,entry);
    }
  }

  /**
   * Get the entry for a given level.
   * @param level Level to use.
   * @return An entry or <code>null</code> if not found.
   */
  public GenericMobLootEntry getEntryForLevel(int level)
  {
    return _levelEntries.get(Integer.valueOf(level));
  }

  /**
   * Get the managed levels.
   * @return A sorted list of levels.
   */
  public List<Integer> getLevels()
  {
    List<Integer> ret=new ArrayList<Integer>(_levelEntries.keySet());
    Collections.sort(ret);
    return ret;
  }
}
