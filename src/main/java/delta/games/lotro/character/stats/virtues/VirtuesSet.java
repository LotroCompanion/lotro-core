package delta.games.lotro.character.stats.virtues;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.stats.BasicStatsSet;
import delta.games.lotro.character.virtues.VirtueDescription;
import delta.games.lotro.character.virtues.VirtuesManager;

/**
 * Storage for the virtues of a single character.
 * @author DAM
 */
public class VirtuesSet
{
  /**
   * Maximum number of selected virtues.
   */
  public static final int MAX_VIRTUES=5;

  /**
   * Rank for virtues (map indexed by virtue identifiers).
   */
  private Map<Integer,Integer> _virtues;
  /**
   * Selected virtues.
   */
  private VirtueDescription[] _selectedVirtues;
  /**
   * Stats that may give bonus to virtue ranks.
   */
  private BasicStatsSet _contributingStats;

  /**
   * Constructor.
   */
  public VirtuesSet()
  {
    _virtues=new HashMap<Integer,Integer>();
    _selectedVirtues=new VirtueDescription[MAX_VIRTUES];
    _contributingStats=new BasicStatsSet();
  }

  /**
   * Clear data.
   */
  public void clear()
  {
    _virtues.clear();
    for(int i=0;i<MAX_VIRTUES;i++)
    {
      _selectedVirtues[i]=null;
    }
    _contributingStats.clear();
  }

  /**
   * Set the contents of this object from a given source.
   * @param source Source to copy.
   */
  public void copyFrom(VirtuesSet source)
  {
    _virtues.clear();
    _virtues.putAll(source._virtues);
    System.arraycopy(source._selectedVirtues,0,_selectedVirtues,0,MAX_VIRTUES);
    _contributingStats=new BasicStatsSet(source._contributingStats);
  }

  /**
   * Set the stats that may give bonus points.
   * @param stats Stats to set.
   */
  public void setContributingStats(BasicStatsSet stats)
  {
    _contributingStats.clear();
    BasicStatsSet usefullStats=VirtueUtils.extractVirtueRankStats(stats);
    _contributingStats.clear();
    _contributingStats.addStats(usefullStats);
  }

  /**
   * Set the value for a virtue.
   * @param virtue Virtue.
   * @param rank Rank to set.
   */
  public void setVirtueValue(VirtueDescription virtue, int rank)
  {
    _virtues.put(Integer.valueOf(virtue.getIdentifier()),Integer.valueOf(rank));
  }

  /**
   * Get rank of a given virtue.
   * @param virtue Virtue.
   * @return A rank value (starting at 0).
   */
  public int getVirtueRank(VirtueDescription virtue)
  {
    int ret=0;
    if (virtue!=null)
    {
      Integer rank=_virtues.get(Integer.valueOf(virtue.getIdentifier()));
      if (rank!=null)
      {
        ret=rank.intValue();
      }
    }
    return ret;
  }

  /**
   * Get the bonus rank for the given virtue.
   * @param virtue Virtue.
   * @return A bonus rank or 0 if none.
   */
  public int getVirtueBonusRank(VirtueDescription virtue)
  {
    return VirtueUtils.getVirtueRankBonus(_contributingStats,virtue);
  }

  /**
   * Indicates if the given virtue is selected or not.
   * @param virtue Virtue.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isSelected(VirtueDescription virtue)
  {
    for(int i=0;i<MAX_VIRTUES;i++)
    {
      if (_selectedVirtues[i]==virtue)
      {
        return true;
      }
    }
    return false;
  }

  /**
   * Get the virtue selected at the specified index.
   * @param index Targeted index, between 0 and <code>MAX_VIRTUES-1</code>.
   * @return A virtue or <code>null</code> if no virtue at the given index.
   */
  public VirtueDescription getSelectedVirtue(int index)
  {
    return _selectedVirtues[index];
  }

  /**
   * Indicates the index of the given virtue.
   * @param virtue Virtue.
   * @return An index or <code>null</code> if it is not selected.
   */
  public Integer getVirtueIndex(VirtueDescription virtue)
  {
    for(int i=0;i<MAX_VIRTUES;i++)
    {
      if (_selectedVirtues[i]==virtue)
      {
        return Integer.valueOf(i);
      }
    }
    return null;
  }

  /**
   * Set the virtue selected at the specified index.
   * @param id Virtue identifier (may be <code>null</code>).
   * @param index Targeted index, between 0 and <code>MAX_VIRTUES-1</code>.
   */
  public void setSelectedVirtue(VirtueDescription id, int index)
  {
    _selectedVirtues[index]=id;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    int index=0;
    List<VirtueDescription> virtues=VirtuesManager.getInstance().getAll();
    for(VirtueDescription virtue : virtues)
    {
      int rank=getVirtueRank(virtue);
      boolean isSelected=isSelected(virtue);
      if ((rank>0) || isSelected)
      {
        if (index>0)
        {
          sb.append(',');
        }
        sb.append(virtue.getPersistenceKey());
        sb.append('(');
        sb.append(rank);
        if (isSelected)
        {
          sb.append('*');
        }
        sb.append(')');
        index++;
      }
    }
    return sb.toString();
  }
}
