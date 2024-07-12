package delta.games.lotro.character.status.allegiances;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.lore.allegiances.AllegianceDescription;
import delta.games.lotro.lore.allegiances.AllegiancesManager;
import delta.games.lotro.lore.allegiances.Points2LevelCurve;

/**
 * Storage for all allegiances status data for a single character.
 * @author DAM
 */
public class AllegiancesStatusManager
{
  private AllegianceDescription _currentAllegiance;
  private Map<Integer,AllegianceStatus> _status;

  /**
   * Constructor.
   */
  public AllegiancesStatusManager()
  {
    _status=new HashMap<Integer,AllegianceStatus>();
  }

  /**
   * Clear data.
   */
  public void clear()
  {
    _currentAllegiance=null;
    _status.clear();
  }

  /**
   * Get the current allegiance.
   * @return an allegiance or <code>null</code> if no current allegiance.
   */
  public AllegianceDescription getCurrentAllegiance()
  {
    return _currentAllegiance;
  }

  /**
   * Set the current allegiance.
   * @param allegiance Allegiance to set as current.
   */
  public void setCurrentAllegiance(AllegianceDescription allegiance)
  {
    _currentAllegiance=allegiance;
  }

  /**
   * Get the status of an allegiance.
   * @param allegiance Targeted allegiance.
   * @param createIfNecessary Indicates if the status shall be created if it
   * does not exist.
   * @return An allegiance status or <code>null</code>.
   */
  public AllegianceStatus get(AllegianceDescription allegiance, boolean createIfNecessary)
  {
    Integer key=Integer.valueOf(allegiance.getIdentifier());
    AllegianceStatus ret=_status.get(key);
    if ((ret==null) && (createIfNecessary))
    {
      ret=new AllegianceStatus(allegiance);
      int curveID=allegiance.getAdvancementProgressionID();
      AllegiancesManager mgr=AllegiancesManager.getInstance();
      Points2LevelCurve curve=mgr.getCurvesManager().getCurve(curveID);
      ret.setPoints2LevelCurve(curve);
      _status.put(key,ret);
    }
    return ret;
  }

  /**
   * Get all managed statuses.
   * @return A list of statuses, ordered by title ID.
   */
  public List<AllegianceStatus> getAll()
  {
    List<Integer> ids=new ArrayList<Integer>(_status.keySet());
    Collections.sort(ids);
    List<AllegianceStatus> ret=new ArrayList<AllegianceStatus>();
    for(Integer id : ids)
    {
      ret.add(_status.get(id));
    }
    return ret;
  }
}
