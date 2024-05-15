package delta.games.lotro.lore.agents.mobs.loots;

import java.util.List;

import delta.games.lotro.common.enums.MobType;
import delta.games.lotro.common.enums.Species;
import delta.games.lotro.common.enums.SubSpecies;
import delta.games.lotro.common.treasure.LootsManager;
import delta.games.lotro.lore.agents.mobs.loot.GenericMobLootSpec;
import delta.games.lotro.lore.agents.mobs.loot.SpeciesLoot;
import delta.games.lotro.lore.agents.mobs.loot.SpeciesLootsManager;
import delta.games.lotro.lore.agents.mobs.loot.SubSpeciesLoot;
import delta.games.lotro.lore.items.ItemsManager;

/**
 * Simple test class for the species loot manager.
 * @author DAM
 */
public class MainTestSpeciesLootManager
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    long now=System.currentTimeMillis();
    ItemsManager.getInstance();
    long now1=System.currentTimeMillis();
    LootsManager.getInstance();
    long now2=System.currentTimeMillis();
    @SuppressWarnings("unused")
    SpeciesLootsManager mgr=SpeciesLootsManager.getInstance();
    long now3=System.currentTimeMillis();
    System.out.println("Items: "+(now1-now)+"ms");
    System.out.println("Loots: "+(now2-now1)+"ms");
    System.out.println("Species loots: "+(now3-now2)+"ms");
    List<SpeciesLoot> speciesLoots=mgr.getLootSpecs();
    for(SpeciesLoot speciesLoot : speciesLoots)
    {
      Species species=speciesLoot.getSpecies();
      System.out.println("Species: "+species.getLabel());
      for(SubSpeciesLoot subSpeciesLoot : speciesLoot.getLootSpecs())
      {
        SubSpecies subSpecies=subSpeciesLoot.getSubSpecies();
        System.out.println("\tSubspecies: "+subSpecies);
        for(GenericMobLootSpec mobTypeLoot : subSpeciesLoot.getLootSpecs())
        {
          MobType mobType=mobTypeLoot.getMobType();
          System.out.println("\t\tMob type: "+mobType);
          for(Integer level : mobTypeLoot.getLevels())
          {
            System.out.println("\t\t\tLevel: "+mobTypeLoot.getEntryForLevel(level.intValue()));
          }
        }
      }
    }
  }
}
