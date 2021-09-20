package delta.games.lotro.lore.agents.mobs.loot;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.enums.MobType;
import delta.games.lotro.common.enums.Species;
import delta.games.lotro.common.enums.SubSpecies;
import delta.games.lotro.common.enums.comparator.LotroEnumEntryCodeComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;

/**
 * Manager for all species loot.
 * @author DAM
 */
public class SpeciesLootsManager
{
  private static final SpeciesLootsManager _instance=load();
  private Map<Species,SpeciesLoot> _loots;

  /**
   * Get the reference instance of this class.
   * @return the reference instance of this class.
   */
  public static SpeciesLootsManager getInstance()
  {
    return _instance;
  }

  private static SpeciesLootsManager load()
  {
    File from=LotroCoreConfig.getInstance().getFile(DataFiles.GENERIC_MOB_LOOTS);
    //return new MobsXMLParser().parseXML(from); TODO
    return new SpeciesLootsManager();
  }

  /**
   * Constructor.
   */
  public SpeciesLootsManager()
  {
    _loots=new HashMap<Species,SpeciesLoot>();
  }

  /**
   * Add loot data for a species.
   * @param loot Loot data to add.
   */
  public void addSpeciesLoot(SpeciesLoot loot)
  {
    Species species=loot.getSpecies();
    _loots.put(species,loot);
  }

  /**
   * Get the loot data for a species.
   * @param species Species to use.
   * @return Some loot data or <code>null</code> if not found.
   */
  public SpeciesLoot getSpeciesLoot(Species species)
  {
    return _loots.get(species);
  }

  /**
   * Get the loot specification for a given species/subspecies/type.
   * @param species Species to use.
   * @param subSpecies Subspecies to use.
   * @param type Mob type to use.
   * @return A loot specification or <code>null</code> if not found.
   */
  public GenericMobLootSpec getLoot(Species species, SubSpecies subSpecies, MobType type)
  {
    SpeciesLoot speciesLoot=getSpeciesLoot(species);
    if (speciesLoot==null)
    {
      return null;
    }
    SubSpeciesLoot subSpeciesLoot=speciesLoot.findSubSpeciesLoot(subSpecies);
    if (subSpeciesLoot==null)
    {
      return null;
    }
    GenericMobLootSpec ret=subSpeciesLoot.findMobTypeLoot(type);
    return ret;
  }

  /**
   * Get a list of managed child loots.
   * @return A list of loots.
   */
  public List<SpeciesLoot> getLootSpecs()
  {
    List<Species> allSpecies=new ArrayList<Species>(_loots.keySet());
    Collections.sort(allSpecies,new LotroEnumEntryCodeComparator<Species>());
    List<SpeciesLoot> ret=new ArrayList<SpeciesLoot>();
    for(Species species : allSpecies)
    {
      ret.add(_loots.get(species));
    }
    return ret;
  }
}
