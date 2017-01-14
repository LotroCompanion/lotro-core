package delta.games.lotro.character.stats.virtues;

import java.util.HashMap;
import java.util.Map;

import delta.games.lotro.common.VirtueId;

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
  private Map<VirtueId,Integer> _virtues;
  private VirtueId[] _selectedVirtues;

  /**
   * Constructor.
   */
  public VirtuesSet()
  {
    _virtues=new HashMap<VirtueId,Integer>();
    _selectedVirtues=new VirtueId[MAX_VIRTUES];
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
  }

  /**
   * Set the value for a virtue.
   * @param id Virtue identifier.
   * @param rank Rank to set.
   */
  public void setVirtueValue(VirtueId id, int rank)
  {
    _virtues.put(id,Integer.valueOf(rank));
  }

  /**
   * Get rank of a given virtue.
   * @param id Virtue identifier.
   * @return A rank value (starting at 0).
   */
  public int getVirtueRank(VirtueId id)
  {
    Integer ret=_virtues.get(id);
    if (ret!=null)
    {
      return ret.intValue();
    }
    return 0;
  }

  /**
   * Indicates if the given virtue is selected or not.
   * @param id Virtue identifier.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isSelected(VirtueId id)
  {
    for(int i=0;i<MAX_VIRTUES;i++)
    {
      if (_selectedVirtues[i]==id)
      {
        return true;
      }
    }
    return false;
  }

  /**
   * Get the virtue selected at the specified index.
   * @param index Targeted index, between 0 and <code>MAX_VIRTUES-1</code>.
   * @return A virtue identifier or <code>null</code> if no virtue at the given index.
   */
  public VirtueId getSelectedVirtue(int index)
  {
    return _selectedVirtues[index];
  }

  /**
   * Indicates the index of the given virtue.
   * @param id Virtue identifier.
   * @return An index or <code>null</code> if it is not selected.
   */
  public Integer getVirtueIndex(VirtueId id)
  {
    for(int i=0;i<MAX_VIRTUES;i++)
    {
      if (_selectedVirtues[i]==id)
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
  public void setSelectedVirtue(VirtueId id, int index)
  {
    _selectedVirtues[index]=id;
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    int index=0;
    for(VirtueId id : VirtueId.values())
    {
      int rank=getVirtueRank(id);
      boolean isSelected=isSelected(id);
      if ((rank>0) || isSelected)
      {
        if (index>0)
        {
          sb.append(',');
        }
        sb.append(id);
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
