package delta.games.lotro.common.treasure;

import delta.common.utils.text.EndOfLine;

/**
 * Entry in a 'treasure list'.
 * @author DAM
 */
public class TreasureListEntry
{
  private int _weight;
  private TreasureGroupProfile _treasureGroup;

  /**
   * Constructor.
   * @param weight Weight.
   * @param treasureGroup Trophy list.
   */
  public TreasureListEntry(int weight, TreasureGroupProfile treasureGroup)
  {
    _weight=weight;
    _treasureGroup=treasureGroup;
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
   * Get the treasure group.
   * @return the treasure group.
   */
  public TreasureGroupProfile getTreasureGroup()
  {
    return _treasureGroup;
  }

  /**
   * Dump contents.
   * @param sb Output.
   * @param level Indentation level.
   */
  public void dump(StringBuilder sb, int level)
  {
    for(int i=0;i<level;i++) sb.append('\t');
    sb.append('(').append(_weight).append(')').append(EndOfLine.NATIVE_EOL);
    _treasureGroup.dump(sb,level);
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    dump(sb,0);
    return sb.toString().trim();
  }
}
