package delta.games.lotro.character.traits;

import java.util.List;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.common.stats.StatsProvider;

/**
 * Simple test to compute stats for some tiered scalable traits.
 * @author DAM
 */
public class MainTestTraits
{
  private void evalTrait(TraitDescription trait, int tiers, int level)
  {
    for(int i=1;i<=tiers;i++)
    {
      StatsProvider statsProvider=trait.getStatsProvider();
      BasicStatsSet stats=statsProvider.getStats(i,level);
      System.out.println(trait.getName()+", tier "+i+", level "+level+" => "+stats);
    }
    
  }

  private void doIt()
  {
    TraitsManager traitsMgr=TraitsManager.getInstance();
    List<TraitDescription> traits=traitsMgr.getAll();
    int nbTraits=traits.size();
    System.out.println("Loaded "+nbTraits+" traits");
    for(TraitDescription trait : traits)
    {
      if ("Might Increase".equals(trait.getName()))
      {
        evalTrait(trait,5,116);
      }
      if ("Finesse Increase".equals(trait.getName()))
      {
        evalTrait(trait,5,116);
      }
    }
  }

  /**
   * @param args
   */
  public static void main(String[] args)
  {
    new MainTestTraits().doIt();
  }
}
