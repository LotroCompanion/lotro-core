package delta.games.lotro.character.stats.buffs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.common.utils.text.EndOfLine;
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
   * Indicates if this buff manager has the given buff.
   * @param buffId Identifier of the buff to use.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean hasBuff(String buffId)
  {
    for(BuffInstance buff : _buffs)
    {
      String id=buff.getBuff().getId();
      if (id.equals(buffId))
      {
        return true;
      }
    }
    return false;
  }

  /**
   * Add a buff in this manager.
   * @param buffInstance Buff to add.
   */
  public void addBuff(BuffInstance buffInstance)
  {
    if (!hasBuff(buffInstance.getBuff().getId()))
    {
      _buffs.add(buffInstance);
    }
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
   * Get a buff using its identifier.
   * @param buffId Buff identifier.
   * @return A buff instance or <code>null</code> if not found.
   */
  public BuffInstance getBuffById(String buffId)
  {
    for(BuffInstance buff : _buffs)
    {
      String id=buff.getBuff().getId();
      if (id.equals(buffId))
      {
        return buff;
      }
    }
    return null;
  }

  /**
   * Remove a buff using its identifier.
   * @param buffId Buff identifier.
   * @return <code>true</code> if a buff was removed, <code>false</code> otherwise.
   */
  public boolean removeBuff(String buffId)
  {
    boolean ret=false;
    BuffInstance buff=getBuffById(buffId);
    if (buff!=null)
    {
      ret=_buffs.remove(buff);
    }
    return ret;
  }

  /**
   * Get all ids of contained buffs.
   * @return a set of buff ids.
   */
  public Set<String> getBuffIds()
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
   * Get stats contributions for the buffs for the given character level.
   * @param level Targeted character level.
   * @return A possibly empty but not <code>null</code> list of contributions.
   */
  public List<StatsContribution> getContributions(int level)
  {
    List<StatsContribution> ret=new ArrayList<StatsContribution>();
    for(BuffInstance buff : _buffs)
    {
      BasicStatsSet buffContrib=buff.getStats(level);
      if (buffContrib!=null)
      {
        StatsContribution statsContrib=StatsContribution.getBuffContrib(buff,buffContrib);
        ret.add(statsContrib);
      }
    }
    return ret;
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
