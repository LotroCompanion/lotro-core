package delta.games.lotro.character.stats.buffs;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.EndOfLine;

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
