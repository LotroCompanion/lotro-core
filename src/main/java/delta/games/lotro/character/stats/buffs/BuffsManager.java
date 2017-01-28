package delta.games.lotro.character.stats.buffs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.stats.BasicStatsSet;

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
      AbstractBuffImpl impl=buff.getBuff().getImpl();
      BasicStatsSet buffContrib=impl.getStats(c,buff);
      if (buffContrib!=null)
      {
        ret.addStats(buffContrib);
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
