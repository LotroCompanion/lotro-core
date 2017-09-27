package delta.games.lotro.character.stats.buffs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.stats.contribs.StatsContribution;

/**
 * Storage for all buffs put on a character.
 * @author DAM
 */
public class BuffsManager
{
  private List<BuffInstance> _buffs;

  /**
   * Constructor.
   */
  public BuffsManager()
  {
    _buffs=new ArrayList<BuffInstance>();
  }

  /**
   * Add a buff in this manager.
   * @param buffInstance Buff to add.
   */
  public void addBuff(BuffInstance buffInstance)
  {
    _buffs.add(buffInstance);
  }

  /**
   * Set the contents of this object from a given source.
   * @param source Source to copy.
   */
  public void copyFrom(BuffsManager source)
  {
    _buffs.clear();
    for(BuffInstance buff : source._buffs)
    {
      BuffInstance newBuff=new BuffInstance(buff);
      _buffs.add(newBuff);
    }
  }

  /**
   * Get the number of buffs.
   * @return a buff count.
   */
  public int getBuffsCount()
  {
    return _buffs.size();
  }

  /**
   * Get a buff.
   * @param index Index of the targeted buff (starting at 0).
   * @return A buff.
   */
  public BuffInstance getBuffAt(int index)
  {
    return _buffs.get(index);
  }

  /**
   * Remove buff.
   * @param index Index of buff to remove (starting at 0).
   */
  public void removeBuffAt(int index)
  {
    _buffs.remove(index);
  }

  /**
   * Get all ids of contained buffs.
   * @return a set of buff ids.
   */
  public Set<String> getBuffId()
  {
    HashSet<String> buffIds=new HashSet<String>();
    for(BuffInstance buff : _buffs)
    {
      String id=buff.getBuff().getId();
      buffIds.add(id);
    }
    return buffIds;
  }

  /**
   * Get buffs to apply for a character.
   * @param c Targeted character.
   * @return Some stats.
   */
  public BasicStatsSet getBuffs(CharacterData c)
  {
    BasicStatsSet ret=new BasicStatsSet();
    for(BuffInstance buff : _buffs)
    {
      BasicStatsSet buffContrib=buff.getStats(c);
      if (buffContrib!=null)
      {
        ret.addStats(buffContrib);
      }
    }
    return ret;
  }

  /**
   * Get stats contributions for the buffs on the given character.
   * @param c Targeted character.
   * @return A possibly empty but not <code>null</code> list of contributions.
   */
  public List<StatsContribution> getContributions(CharacterData c)
  {
    List<StatsContribution> ret=new ArrayList<StatsContribution>();
    for(BuffInstance buff : _buffs)
    {
      BasicStatsSet buffContrib=buff.getStats(c);
      if (buffContrib!=null)
      {
        StatsContribution statsContrib=StatsContribution.getBuffContrib(buff.getBuff().getId(),buffContrib);
        ret.add(statsContrib);
      }
    }
    return ret;
  }

  /**
   * Get buffs to apply on raw stats.
   * @param c Targeted character.
   * @param raw Raw stats.
   */
  public void applyBuffs(CharacterData c,BasicStatsSet raw)
  {
    for(BuffInstance buff : _buffs)
    {
      AbstractBuffImpl impl=buff.getBuff().getImpl();
      impl.apply(c,raw,buff);
    }
  }

  /**
   * Remove all buffs.
   */
  public void clear()
  {
    _buffs.clear();
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    for(BuffInstance buff : _buffs)
    {
      sb.append(buff).append(EndOfLine.NATIVE_EOL);
    }
    return sb.toString().trim();
  }
}
