package delta.games.lotro.common.treasure;

import delta.common.utils.text.EndOfLine;
import delta.games.lotro.lore.items.legendary.relics.Relic;

/**
 * Entry in a 'relics list':
 * <ul>
 * <li>probability (percentage: ]0-100]),
 * <li>relic or relics treasure group.
 * </ul>
 * @author DAM
 */
public class RelicsListEntry
{
  private float _probability;
  private Relic _relic;
  private RelicsTreasureGroup _treasureGroup;

  /**
   * Constructor.
   * @param probability Probability.
   * @param relic Relic.
   * @param treasureGroup Relics treasure group.
   */
  public RelicsListEntry(float probability, Relic relic, RelicsTreasureGroup treasureGroup)
  {
    _probability=probability;
    _relic=relic;
    _treasureGroup=treasureGroup;
  }

  /**
   * Get the probability.
   * @return the probability.
   */
  public float getProbability()
  {
    return _probability;
  }

  /**
   * Get the rewarded relic.
   * @return a relic or <code>null</code>.
   */
  public Relic getRelic()
  {
    return _relic;
  }

  /**
   * Get the treasure group.
   * @return the treasure group.
   */
  public RelicsTreasureGroup getRelicsTreasureGroup()
  {
    return _treasureGroup;
  }

  /**
   * Get the probability label.
   * @return a readable label for probability.
   */
  public String getProbabilityLabel() {
    if (_probability==1.0)
    {
      return "Always";
    }
    return String.format("%.1f%%",Float.valueOf(_probability*100));
  }

  /**
   * Dump contents.
   * @param sb Output.
   * @param level Indentation level.
   */
  public void dump(StringBuilder sb, int level)
  {
    for(int i=0;i<level;i++) sb.append('\t');
    sb.append(getProbabilityLabel());
    if (_relic!=null)
    {
      sb.append(' ').append(_relic.getName());
      sb.append(EndOfLine.NATIVE_EOL);
    }
    else if (_treasureGroup!=null)
    {
      sb.append(EndOfLine.NATIVE_EOL);
      _treasureGroup.dump(sb,level+1);
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
