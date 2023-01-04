package delta.games.lotro.character.stats;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.classes.traitTree.TraitTree;
import delta.games.lotro.character.stats.contribs.StatsContribution;
import delta.games.lotro.character.status.traitTree.TraitTreeStatus;
import delta.games.lotro.character.traits.TraitDescription;

/**
 * Trait tree stats computer.
 * @author DAM
 */
public class TraitTreeStatsComputer
{
  /**
   * Compute stats contributions for a trait tree.
   * @param status Trait tree status to use.
   * @param c Character to use.
   * @return a possibly empty but never <code>null</code> list of stats contributions.
   */
  public static List<StatsContribution> getContributions(TraitTreeStatus status, CharacterData c)
  {
    List<StatsContribution> ret=new ArrayList<StatsContribution>();
    // Handle selected traits
    TraitTree tree=status.getTraitTree();
    for(TraitDescription trait : tree.getAllTraits())
    {
      Integer rank=status.getRankForTrait(trait.getIdentifier());
      if ((rank!=null) && (rank.intValue()>0))
      {
        StatsContribution contrib=handleTrait(c,trait,rank.intValue());
        if (contrib!=null)
        {
          ret.add(contrib);
        }
      }
    }
    // Handle unlocked traits
    List<TraitDescription> unlockedTraits=status.getUnlockedTraits();
    for(TraitDescription trait : unlockedTraits)
    {
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
