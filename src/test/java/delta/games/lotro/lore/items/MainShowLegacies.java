package delta.games.lotro.lore.items;

import java.util.List;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.classes.ClassesManager;
import delta.games.lotro.lore.items.legendary.non_imbued.DefaultNonImbuedLegacy;
import delta.games.lotro.lore.items.legendary.non_imbued.NonImbuedLegaciesManager;
import delta.games.lotro.lore.items.legendary.non_imbued.TieredNonImbuedLegacy;

/**
 * Simple tool to show available legacies by slot and class.
 * @author DAM
 */
public class MainShowLegacies
{
  private void doIt()
  {
    NonImbuedLegaciesManager nonImbuedLegaciesMgr=NonImbuedLegaciesManager.getInstance();
    EquipmentLocation[] slots= { EquipmentLocations.MAIN_HAND, EquipmentLocations.RANGED_ITEM,
        EquipmentLocations.CLASS_SLOT,EquipmentLocations.BRIDLE
    };
    for(ClassDescription characterClass : ClassesManager.getInstance().getAllCharacterClasses())
    {
      for(EquipmentLocation slot : slots)
      {
        System.out.println("Class: "+characterClass+", slot: "+slot);
        List<DefaultNonImbuedLegacy> legacies=nonImbuedLegaciesMgr.getDefaultLegacies(characterClass,slot);
        for(DefaultNonImbuedLegacy legacy : legacies)
        {
          System.out.println("\t"+legacy);
        }
        List<TieredNonImbuedLegacy> tieredLegacies=nonImbuedLegaciesMgr.getTieredLegacies(characterClass,slot);
        for(TieredNonImbuedLegacy legacy : tieredLegacies)
        {
          System.out.println("\t"+legacy);
        }
      }
    }
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MainShowLegacies().doIt();
  }

}
