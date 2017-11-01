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
    return o1.getValue().compareTo(o2.getValue());
  }
}
