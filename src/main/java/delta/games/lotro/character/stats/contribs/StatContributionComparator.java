package delta.games.lotro.character.stats.contribs;

import java.util.Comparator;

/**
 * Comparator for stat contributions.
 * @author DAM
 */
public class StatContributionComparator implements Comparator<StatContribution>
{
  public int compare(StatContribution o1, StatContribution o2)
  {
    return Float.compare(o1.getValue().floatValue(),o2.getValue().floatValue());
  }
}
