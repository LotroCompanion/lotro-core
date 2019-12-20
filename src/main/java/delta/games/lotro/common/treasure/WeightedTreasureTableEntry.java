package delta.games.lotro.common.treasure;

import delta.common.utils.text.EndOfLine;

/**
 * Entry in a 'weighted treasure table':
 * <ul>
 * <li>weight,
 * <li>trophy list.
 * </ul>
 * @author DAM
 */
public class WeightedTreasureTableEntry
{
  private int _weight;
  private TrophyList _trophyList;

  /**
   * Constructor.
   * @param weight Weight.
   * @param trophyList Trophy list.
   */
  public WeightedTreasureTableEntry(int weight, TrophyList trophyList)
  {
    _weight=weight;
    _trophyList=trophyList;
  }

  /**
   * Get the weight.
   * @return the weight.
   */
  public int getWeight()
  {
    return _weight;
  }

  /**
   * Get the trophy list.
   * @return the trophy list.
   */
  public TrophyList getTrophyList()
  {
    return _trophyList;
  }

  /**
   * Dump contents.
   * @param sb Output.
   * @param level Indentation level.
   */
  public void dump(StringBuilder sb, int level)
  {
    for(int i=0;i<level;i++) sb.append('\t');
    sb.append('(').append(_weight).append(") ").append(EndOfLine.NATIVE_EOL);
    _trophyList.dump(sb,level+1);
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    dump(sb,0);
    return sb.toString().trim();
  }
}
