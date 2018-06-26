package delta.games.lotro.character.stats.buffs;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.stats.BasicStatsSet;

/**
 * Buff implemented that routes on child buffs depending on the character level.
 * @author DAM
 */
public class LevelBasedBuff extends AbstractBuffImpl
{
  private List<Integer> _minLevels;
  private List<Integer> _maxLevels;
  private List<AbstractBuffImpl> _buffs;

  /**
   * Constructor.
   */
  public LevelBasedBuff()
  {
    _minLevels=new ArrayList<Integer>();
    _maxLevels=new ArrayList<Integer>();
    _buffs=new ArrayList<AbstractBuffImpl>();
  }

  /**
   * Add a child buff.
   * @param minLevel Minimum level.
   * @param maxLevel Maximum level.
   * @param buff Buff to use.
   */
  public void defineBuff(int minLevel, int maxLevel, AbstractBuffImpl buff)
  {
    _minLevels.add(Integer.valueOf(minLevel));
    _maxLevels.add(Integer.valueOf(maxLevel));
    _buffs.add(buff);
  }

  private AbstractBuffImpl findBuff(int level)
  {
    AbstractBuffImpl ret=null;
    int nb=_minLevels.size();
    for(int i=0;i<nb;i++)
    {
      if ((_minLevels.get(i).intValue()>=level) && (_maxLevels.get(i).intValue()<=level))
      {
        ret=_buffs.get(i);
      }
    }
    return ret;
  }

  /**
   * Compute the stats contribution of a buff on a character.
   * @param character Targeted character.
   * @param buff Buff to use.
   * @return A stats set or <code>null</code> if not supported.
   */
  public BasicStatsSet getStats(CharacterData character, BuffInstance buff)
  {
    AbstractBuffImpl buffImpl=findBuff(character.getLevel());
    if (buffImpl!=null)
    {
      return buffImpl.getStats(character,buff);
    }
    return null;
  }

  /**
   * Compute the stats contribution of a buff from raw stats.
   * @param character Targeted character.
   * @param buff Buff to use.
   * @param raw Raw stats.
   * @return A stats set or <code>null</code> if not supported.
   */
  public BasicStatsSet getStats(CharacterData character, BasicStatsSet raw, BuffInstance buff)
  {
    AbstractBuffImpl buffImpl=findBuff(character.getLevel());
    if (buffImpl!=null)
    {
      return buffImpl.getStats(character,raw,buff);
    }
    return null;
  }

  /**
   * Get all managed tiers.
   * @return a sorted list of all managed tiers or <code>null</code> if tiers are not supported.
   */
  public List<Integer> getTiers()
  {
    AbstractBuffImpl buffImpl=_buffs.get(0);
    return buffImpl.getTiers();
  }
}
