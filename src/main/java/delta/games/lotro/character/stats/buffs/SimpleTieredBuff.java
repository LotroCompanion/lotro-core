package delta.games.lotro.character.stats.buffs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.stats.BasicStatsSet;

/**
 * A buff that associates a tier to some predefined stats.
 * @author DAM
 */
public class SimpleTieredBuff extends AbstractBuffImpl
{
  private HashMap<Integer,BasicStatsSet> _stats;

  /**
   * Constructor.
   */
  public SimpleTieredBuff()
  {
    _stats=new HashMap<Integer,BasicStatsSet>();
  }

  @Override
  public List<Integer> getTiers()
  {
    List<Integer> ret=new ArrayList<Integer>(_stats.keySet());
    Collections.sort(ret);
    return ret;
  }

  /**
   * Add a tier specification.
   * @param tier Tier to add.
   * @param stats Associated stats.
   */
  public void addTier(int tier, BasicStatsSet stats)
  {
    BasicStatsSet tieredStats=new BasicStatsSet(stats);
    _stats.put(Integer.valueOf(tier),tieredStats);
  }

  @Override
  public BasicStatsSet getStats(CharacterData character, BuffInstance buff)
  {
    Integer tier=buff.getTier();
    BasicStatsSet stats=null;
    if (tier!=null)
    {
      stats=_stats.get(tier);
    }
    return stats;
  }
}
