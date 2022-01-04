package delta.games.lotro.character.status.travels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.enums.LotroEnum;
import delta.games.lotro.common.enums.LotroEnumsRegistry;
import delta.games.lotro.common.enums.TravelLink;

/**
 * Storage for all anchors status data for a single character.
 * @author DAM
 */
public class AnchorsStatusManager
{
  private Map<TravelLink,AnchorStatus> _status;

  /**
   * Constructor.
   */
  public AnchorsStatusManager()
  {
    _status=new HashMap<TravelLink,AnchorStatus>();
  }

  /**
   * Clear data.
   */
  public void clear()
  {
    _status.clear();
  }

  /**
   * Get the status of an anchor.
   * @param type Targeted anchort.
   * @param createIfNecessary Indicates if the status shall be created if it
   * does not exist.
   * @return An anchor status or <code>null</code>.
   */
  public AnchorStatus get(TravelLink type, boolean createIfNecessary)
  {
    AnchorStatus ret=_status.get(type);
    if ((ret==null) && (createIfNecessary))
    {
      ret=new AnchorStatus(type);
      _status.put(type,ret);
    }
    return ret;
  }

  /**
   * Get all managed statuses.
   * @return A list of statuses, ordered by title ID.
   */
  public List<AnchorStatus> getAll()
  {
    List<AnchorStatus> ret=new ArrayList<AnchorStatus>();
    LotroEnum<TravelLink> typeEnum=LotroEnumsRegistry.getInstance().get(TravelLink.class);
    for(TravelLink type : typeEnum.getAll())
    {
      AnchorStatus status=_status.get(type);
      if (status!=null)
      {
        ret.add(status);
      }
    }
    return ret;
  }
}
