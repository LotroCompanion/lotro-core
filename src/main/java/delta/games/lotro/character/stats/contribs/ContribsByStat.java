package delta.games.lotro.character.stats.contribs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * Contributions for a single stat.
 * @author DAM
 */
public class ContribsByStat
{
  private STAT _stat;
  private Map<String,StatContribution> _contribs;

  /**
   * Constructor.
   * @param stat Targeted stat.
   */
  public ContribsByStat(STAT stat)
  {
    _stat=stat;
    _contribs=new HashMap<String,StatContribution>();
  }

  /**
   * Get the targeted stat.
   * @return A stat.
   */
  public STAT getStat()
  {
    return _stat;
  }

  /**
   * Add a contribution.
   * @param contrib Contribution to add.
   */
  public void addContrib(StatContribution contrib)
  {
    StatContributionSource source=contrib.getSource();
    StatContribution old=_contribs.get(source.getId());
    if (old==null)
    {
      old=new StatContribution(source,new FixedDecimalsInteger());
      _contribs.put(source.getId(),old);
    }
    old.add(contrib.getValue());
  }

  /**
   * Remove a contribution using its identifier.
   * @param sourceId Source identifier.
   */
  public void remove(String sourceId)
  {
    _contribs.remove(sourceId);
  }

  /**
   * Get all contributions.
   * @return A list of all contributions, sorted by ascending value.
   */
  public List<StatContribution> getContribs()
  {
    List<StatContribution> ret=new ArrayList<StatContribution>(_contribs.values());
    Collections.sort(ret,new StatContributionComparator());
    return ret;
  }

  @Override
  public String toString()
  {
    return _contribs.toString();
  }
}
