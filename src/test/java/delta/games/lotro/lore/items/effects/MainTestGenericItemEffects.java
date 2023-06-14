package delta.games.lotro.lore.items.effects;

import java.util.List;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.effects.Effect;
import delta.games.lotro.common.enums.EquipmentCategory;
import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.stats.StatUtils;

/**
 * Test class for the generic item effects.
 * @author DAM
 */
public class MainTestGenericItemEffects
{
  private void doIt()
  {
    GenericItemEffectsManager mgr=GenericItemEffectsManager.getInstance();
    LotroEnum<EquipmentCategory> categoryEnum=LotroEnumsRegistry.getInstance().get(EquipmentCategory.class);
    for(EquipmentCategory category : categoryEnum.getAll())
    {
      GenericItemEffects itemEffects=mgr.getEffects(category);
      handleGenericItemEffects(itemEffects);
    }
  }

  private void handleGenericItemEffects(GenericItemEffects genericItemEffects)
  {
    if (genericItemEffects==null)
    {
      return;
    }
    EquipmentCategory category=genericItemEffects.getCategory();
    System.out.println("Category: "+category);
    List<Effect> effects=genericItemEffects.getEffects();
    for(Effect effect : effects)
    {
      System.out.println("\t"+effect);
      BasicStatsSet stats=effect.getStatsProvider().getStats(1,100);
      List<String> lines=StatUtils.getStatsDisplayLinesAsList(stats);
      for(String line : lines)
      {
        System.out.println("\t\t"+line);
      }
    }
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MainTestGenericItemEffects().doIt();
  }
}
