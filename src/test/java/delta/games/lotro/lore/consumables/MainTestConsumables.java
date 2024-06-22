package delta.games.lotro.lore.consumables;

import java.io.File;
import java.util.List;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.stats.StatUtils;
import delta.games.lotro.common.stats.StatsProvider;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.consumables.io.xml.ConsumableXMLParser;

/**
 * Simple test class to show the stats of consumables.
 * @author DAM
 */
public class MainTestConsumables
{
  /**
   * Basic main method for test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    File consumablesFile=LotroCoreConfig.getInstance().getFile(DataFiles.CONSUMABLES);
    List<Consumable> consumables=new ConsumableXMLParser().parseConsumablesFile(consumablesFile);
    for(Consumable consumable : consumables)
    {
      System.out.println("Consumable: "+consumable.getIdentifier()+" "+consumable.getName());
      StatsProvider provider=consumable.getProvider();
      BasicStatsSet stats=provider.getStats(1,130);
      List<String> lines=StatUtils.getStatsForDisplay(stats);
      for(String line : lines)
      {
        System.out.println("\t"+line);
      }
    }
  }
}
