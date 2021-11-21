package delta.games.lotro.lore.allegiances;

import java.util.List;

import delta.games.lotro.common.rewards.Rewards;
import delta.games.lotro.lore.deeds.DeedDescription;

/**
 * Simple test class to show the rewards of allegiances.
 * @author DAM
 */
public class MainTestAllegiancesRewards
{
  private void doIt()
  {
    AllegiancesManager mgr=AllegiancesManager.getInstance();
    for(AllegianceDescription allegiance : mgr.getAll())
    {
      doIt(allegiance);
    }
  }

  private void doIt(AllegianceDescription allegiance)
  {
    List<DeedDescription> deeds=allegiance.getDeeds();
    for(DeedDescription deed : deeds)
    {
      System.out.println("Deed: "+deed);
      Rewards rewards=deed.getRewards();
      System.out.println(rewards);
    }
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    new MainTestAllegiancesRewards().doIt();
  }
}
