package delta.games.lotro.lore.agents.mobs.loot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.enums.Species;
import delta.games.lotro.common.enums.SubSpecies;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryCodeComparator;

/**
 * Generic loot data for a single species.
 * @author DAM
 */
public class SpeciesLoot
{
  private Species _species;
  private Map<SubSpecies,SubSpeciesLoot> _lootBySubSpecies;

  /**
   * Constructor.
   * @param species Managed species.
   */
  public SpeciesLoot(Species species)
  {
    _species=species;
    _lootBySubSpecies=new HashMap<SubSpecies,SubSpeciesLoot>();
  }

  /**
   * Get the managed species.
   * @return the managed species.
   */
  public Species getSpecies()
  {
    return _species;
  }

  /**
   * Add loot data for a subspecies.
   * @param loot Loot data to add.
   */
  public void addSubSpeciesLoot(SubSpeciesLoot loot)
  {
    SubSpecies subSpecies=loot.getSubSpecies();
    _lootBySubSpecies.put(subSpecies,loot);
  }

  /**
   * Get the loot data for a subspecies.
   * @param subSpecies Subspecies to use.
   * @return Some loot data or <code>null</code> if not found.
   */
  public SubSpeciesLoot getSubSpeciesLoot(SubSpecies subSpecies)
  {
    return _lootBySubSpecies.get(subSpecies);
  }

  /**
   * Find the loot data for a subspecies.
   * If data for the specified subspecies is not found, then we search for default loot data (no subspecies).
   * @param subSpecies Subspecies to use.
   * @return Some loot data or <code>null</code> if not found.
   */
  public SubSpeciesLoot findSubSpeciesLoot(SubSpecies subSpecies)
  {
    SubSpeciesLoot ret=_lootBySubSpecies.get(subSpecies);
    if ((ret==null) && (subSpecies!=null))
    {
      ret=_lootBySubSpecies.get(null);
    }
    return ret;
  }

  /**
   * Get a list of managed child loots.
   * @return A list of loots.
   */
  public List<SubSpeciesLoot> getLootSpecs()
  {
    List<SubSpecies> allSubSpecies=new ArrayList<SubSpecies>(_lootBySubSpecies.keySet());
    Collections.sort(allSubSpecies,new LotroEnumEntryCodeComparator<SubSpecies>());
    List<SubSpeciesLoot> ret=new ArrayList<SubSpeciesLoot>();
    for(SubSpecies subSpecies : allSubSpecies)
    {
      ret.add(_lootBySubSpecies.get(subSpecies));
    }
    return ret;
  }
}
