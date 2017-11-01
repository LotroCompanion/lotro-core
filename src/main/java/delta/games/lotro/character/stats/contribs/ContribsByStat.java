package delta.games.lotro.character.stats.contribs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.stats.STAT;
import delta.games.lotro.utils.FixedDecimalsInteger;

/**
 * @author dm
 */
public class ContribsByStat
{
  private STAT _stat;
  private Map<String,StatContribution> _contribs;

  /**
   * Constructor.
   * @param stat
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
   * @param contrib
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
   * Get all contributions.
   * @return A list of all contributions, sorted by ascending value.
   */
  public List<StatContribution> getContribs()
  {
    List<StatContribution> ret=new ArrayList<StatContribution>(_contribs.values());
    Collections.sort(ret,new StatContributionComparator());
    return ret;
  }
}
