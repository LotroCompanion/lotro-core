package delta.games.lotro.character.stats;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.stats.contribs.StatsContribution;
import delta.games.lotro.character.status.traits.shared.TraitSlotsStatus;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;

/**
 * Racial traits stats computer.
 * @author DAM
 */
public class RacialTraitsStatsComputer
{
  /**
   * Compute stats contributions for racial traits.
   * @param status Status to use.
   * @param c Character to use.
   * @return a possibly empty but never <code>null</code> list of stats contributions.
   */
  public static List<StatsContribution> getContributions(TraitSlotsStatus status, CharacterData c)
  {
    List<StatsContribution> ret=new ArrayList<StatsContribution>();
    TraitsManager traitsMgr=TraitsManager.getInstance();
    int nbSlots=status.getSlotsCount();
    for(int i=0;i<nbSlots;i++)
    {
      int traitID=status.getTraitAt(i);
      if (traitID==0)
      {
        continue;
      }
      TraitDescription trait=traitsMgr.getTrait(traitID);
      if (trait==null)
      {
        continue;
      }
      StatsContribution contrib=handleTrait(c,trait,1);
      if (contrib!=null)
      {
        ret.add(contrib);
      }
    }
    return ret;
  }

  private static StatsContribution handleTrait(CharacterData c, TraitDescription trait, int rank)
  {
    BasicStatsSet stats=StatsComputer.getStats(c,trait,rank);
    StatsContribution contrib=StatsContribution.getTraitContrib(trait,stats);
    return contrib;
  }
}
