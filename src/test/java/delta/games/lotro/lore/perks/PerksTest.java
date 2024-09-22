package delta.games.lotro.lore.perks;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.enums.PerkUICategory;

/**
 * Test class for perks.
 * @author DAM
 */
class PerksTest
{
  /**
   * Test perks loading. Dump the results.
   */
  @Test
  void testPerksLoading()
  {
    PerksManager perksMgr=PerksManager.getInstance();
    for(PerkDescription perk : perksMgr.getAll())
    {
      assertNotNull(perk);
      // Attributes
      int id=perk.getIdentifier();
      String name=perk.getName();
      System.out.println("***** ID="+id+", name="+name+" ********");
      // Description
      String description=perk.getDescription();
      System.out.println("Description: "+description);
      int iconID=perk.getIconId();
      System.out.println("Icon ID: "+iconID);
      // Effect
      Effect effect=perk.getEffect();
      System.out.println("Effect: "+effect);
      // UI Category
      PerkUICategory category=perk.getUICategory();
      System.out.println("Category: "+category);
      // Min Level
      int minLevel=perk.getMinLevel();
      System.out.println("Min level: "+minLevel);
      // Points cost
      int pointsCost=perk.getPointsCost();
      System.out.println("Points cost: "+pointsCost);
    }
  }
}
