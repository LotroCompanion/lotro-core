package delta.games.lotro.lore.items.legendary.relics.comparators;

import java.util.Comparator;
import java.util.List;

import delta.games.lotro.lore.items.legendary.relics.RelicType;

/**
 * Relic types comparator.
 * @author DAM
 */
public class RelicTypeComparator implements Comparator<RelicType>
{
  private List<RelicType> _sortedTypes;

  /**
   * Constructor.
   */
  public RelicTypeComparator()
  {
    _sortedTypes=RelicType.getAll();
  }

  @Override
  public int compare(RelicType type1, RelicType type2)
  {
    int index1=_sortedTypes.indexOf(type1);
    int index2=_sortedTypes.indexOf(type2);
    int typeDiff=index1-index2;
    return typeDiff;
  }
}
