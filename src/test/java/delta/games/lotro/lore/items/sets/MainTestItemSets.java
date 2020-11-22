package delta.games.lotro.lore.items.sets;

import java.util.List;

import delta.games.lotro.common.stats.StatDescription;
import delta.games.lotro.common.stats.StatProvider;
import delta.games.lotro.common.stats.StatsProvider;

/**
 * Simple test class to show the bonuses of item sets.
 * @author DAM
 */
public class MainTestItemSets
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    ItemsSetsManager mgr=ItemsSetsManager.getInstance();
    List<ItemsSet> itemsSets=mgr.getAll();
    for(ItemsSet itemsSet : itemsSets)
    {
      System.out.println("Items set: "+itemsSet.getName());
      int level=itemsSet.getLevel();
      for(ItemsSetBonus bonusSet : itemsSet.getBonuses())
      {
        int count=bonusSet.getPiecesCount();
        System.out.println("\tCount="+count);
        StatsProvider statsProvider=bonusSet.getStatsProvider();
        int nbStatsProviders=statsProvider.getNumberOfStatProviders();
        for(int i=0;i<nbStatsProviders;i++)
        {
          StatProvider statProvider=statsProvider.getStatProvider(i);
          Float value=statProvider.getStatValue(1,level);
          String override=statProvider.getDescriptionOverride();
          String display;
          if (override!=null)
          {
            if (override.indexOf("{***}")!=-1)
            {
              display=override.replace("{***}",value.toString());
            }
            else
            {
              display=override;
            }
            display="OVERRIDE: "+display;
          }
          else
          {
            StatDescription stat=statProvider.getStat();
            display=stat.getName()+": "+value;
          }
          System.out.println("\t\tSTAT="+display);
        }
      }
    }
  }
}
