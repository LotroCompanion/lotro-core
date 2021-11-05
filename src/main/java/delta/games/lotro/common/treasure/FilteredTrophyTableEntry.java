package delta.games.lotro.common.treasure;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.common.requirements.UsageRequirement;

/**
 * Entry in a 'filtered trophy table':
 * <ul>
 * <li>requirements,
 * <li>trophy list or weighted treasure table.
 * </ul>
 * @author DAM
 */
public class FilteredTrophyTableEntry
{
  private UsageRequirement _requirements;
  private LootTable _table;

  /**
   * Constructor.
   * @param lootTable Loot table.
   */
  public FilteredTrophyTableEntry(LootTable lootTable)
  {
    _requirements=new UsageRequirement();
    _table=lootTable;
  }

  /**
   * Get the usage requirement.
   * @return the usage requirement.
   */
  public UsageRequirement getUsageRequirement()
  {
    return _requirements;
  }

  /**
   * Get the loot table.
   * @return the loot table.
   */
  public LootTable getLootTable()
  {
    return _table;
  }

  /**
   * Dump contents.
   * @param sb Output.
   * @param level Indentation level.
   */
  public void dump(StringBuilder sb, int level)
  {
    for(int i=0;i<level;i++) sb.append('\t');
    sb.append('(').append(_requirements).append(") ").append(EndOfLine.NATIVE_EOL);
    if (_table!=null)
    {
      _table.dump(sb,level+1);
    }
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    dump(sb,0);
    return sb.toString().trim();
  }
}
