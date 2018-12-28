package delta.games.lotro.character.stats.virtues;

import java.util.HashMap;
import java.util.List;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.traits.TraitDescription;
import delta.games.lotro.character.traits.TraitsManager;
import delta.games.lotro.common.VirtueId;
import delta.games.lotro.common.stats.StatsProvider;

/**
 * Manager for contributions of virtues to player stats.
 * @author DAM
 */
public final class VirtuesContributionsMgr
{
  private static final String VIRTUE_SEED="Virtue:";
  private HashMap<VirtueId,TraitDescription> _traits;

  private static final VirtuesContributionsMgr _instance=new VirtuesContributionsMgr();

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static VirtuesContributionsMgr get()
  {
    return _instance;
  }

  /**
   * Constructor.
   */
  private VirtuesContributionsMgr()
  {
    _traits=new HashMap<VirtueId,TraitDescription>();
    init();
  }

  /**
   * Get the contribution for a given virtue and rank.
   * @param virtueId Virtue identifier.
   * @param rank Rank (starting at 1).
   * @return A stats set or <code>null</code> if not found.
   */
  public BasicStatsSet getContribution(VirtueId virtueId, int rank)
  {
    BasicStatsSet stats=null;
    TraitDescription trait=_traits.get(virtueId);
    if (trait!=null)
    {
      StatsProvider statsProvider=trait.getStatsProvider();
      stats=statsProvider.getStats(1,rank);
    }
    return stats;
  }

  /**
   * Get stats contribution for a set of virtues.
   * @param virtues Virtues set.
   * @return A stats set.
   */
  public BasicStatsSet getContribution(VirtuesSet virtues)
  {
    BasicStatsSet ret=new BasicStatsSet();
    for(int i=0;i<VirtuesSet.MAX_VIRTUES;i++)
    {
      VirtueId virtue=virtues.getSelectedVirtue(i);
      if (virtue!=null)
      {
        int rank=virtues.getVirtueRank(virtue);
        BasicStatsSet virtueContrib=getContribution(virtue,rank);
        ret.addStats(virtueContrib);
      }
    }
    return ret;
  }

  private void init()
  {
    TraitsManager traitsMgr=TraitsManager.getInstance();
    List<TraitDescription> traits=traitsMgr.getAll();
    for(TraitDescription trait : traits)
    {
      String key=trait.getKey();
      if (key.startsWith(VIRTUE_SEED))
      {
        String virtueKey=key.substring(VIRTUE_SEED.length());
        VirtueId virtueId=VirtueId.valueOf(virtueKey);
        _traits.put(virtueId,trait);
      }
    }
  }
}
