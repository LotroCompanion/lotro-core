package delta.games.lotro.lore.agents.mobs.loot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.enums.MobType;
import delta.games.lotro.common.enums.Species;
import delta.games.lotro.common.enums.SubSpecies;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryCodeComparator;

/**
 * Generic loot data for a single species and subspecies.
 * @author DAM
 */
public class SubSpeciesLoot
{
  private Species _parent;
  private SubSpecies _subSpecies;
  private Map<MobType,GenericMobLootSpec> _lootByType;

  /**
   * Constructor.
   * @param parent Managed species.
   * @param subSpecies Managed subspecies.
   */
  public SubSpeciesLoot(Species parent, SubSpecies subSpecies)
  {
    _parent=parent;
    _subSpecies=subSpecies;
    _lootByType=new HashMap<MobType,GenericMobLootSpec>();
  }

  /**
   * Get the managed species.
   * @return the managed species.
   */
  public Species getSpecies()
  {
    return _parent;
  }

  /**
   * Get the managed subspecies.
   * @return the managed subspecies.
   */
  public SubSpecies getSubSpecies()
  {
    return _subSpecies;
  }

  /**
   * Add loot data for a mob type.
   * @param loot Loot data to add.
   */
  public void addMobTypeLoot(GenericMobLootSpec loot)
  {
    MobType type=loot.getMobType();
    _lootByType.put(type,loot);
  }

  /**
   * Get the loot data for a mob type.
   * @param mobType Mob type to use.
   * @return Some loot data or <code>null</code> if not found.
   */
  public GenericMobLootSpec getMobTypeLoot(MobType mobType)
  {
    return _lootByType.get(mobType);
  }

  /**
   * Find the loot data for a mob type.
   * If data for the specified mob type is not found, then we search for default loot data (no mob type).
   * @param mobType Mob type to use.
   * @return Some loot data or <code>null</code> if not found.
   */
  public GenericMobLootSpec findMobTypeLoot(MobType mobType)
  {
    GenericMobLootSpec ret=_lootByType.get(mobType);
    if ((ret==null) && (mobType!=null))
    {
      ret=_lootByType.get(null);
    }
    return ret;
  }

  /**
   * Get a list of managed loot specifications.
   * @return A list of loot specifications.
   */
  public List<GenericMobLootSpec> getLootSpecs()
  {
    List<MobType> types=new ArrayList<MobType>(_lootByType.keySet());
    Collections.sort(types,new LotroEnumEntryCodeComparator<MobType>());
    List<GenericMobLootSpec> ret=new ArrayList<GenericMobLootSpec>();
    for(MobType type : types)
    {
      ret.add(_lootByType.get(type));
    }
    return ret;
  }
}
