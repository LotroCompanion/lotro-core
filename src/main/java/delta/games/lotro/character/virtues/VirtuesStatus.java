package delta.games.lotro.character.virtues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Status of all virtues.
 * @author DAM
 */
public class VirtuesStatus
{
  private Map<Integer,SingleVirtueStatus> _statuses;

  /**
   * Constructor.
   */
  public VirtuesStatus()
  {
    _statuses=new HashMap<Integer,SingleVirtueStatus>();
  }

  /**
   * Get the status for a virtue.
   * @param virtue Virtue to use.
   * @return A virtue status.
   */
  public SingleVirtueStatus getVirtueStatus(VirtueDescription virtue)
  {
    int virtueId=virtue.getIdentifier();
    Integer key=Integer.valueOf(virtueId);
    SingleVirtueStatus status=_statuses.get(key);
    if (status==null)
    {
      status=new SingleVirtueStatus(virtue);
      _statuses.put(key,status);
    }
    return status;
  }

  /**
   * Get a list of all virtue statuses, ordered by virtue identifier.
   * @return A list of virtue statuses.
   */
  public List<SingleVirtueStatus> getAll()
  {
    List<SingleVirtueStatus> ret=new ArrayList<SingleVirtueStatus>(_statuses.values());
    Comparator<SingleVirtueStatus> c=new Comparator<SingleVirtueStatus>()
    {
      public int compare(SingleVirtueStatus v1, SingleVirtueStatus v2)
      {
        int id1=v1.getVirtue().getIdentifier();
        int id2=v2.getVirtue().getIdentifier();
        return Integer.compare(id1,id2);
      }
    };
    Collections.sort(ret,c);
    return ret;
  }
}
