package delta.games.lotro.character.deeds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.lore.deeds.DeedDescription;
import delta.games.lotro.lore.deeds.DeedsManager;

/**
 * Storage for all deed statuses for a single character.
 * @author DAM
 */
public class DeedsStatusManager
{
  private String _characterName;
  private String _server;
  private Map<Integer,DeedStatus> _status;

  /**
   * Constructor.
   */
  public DeedsStatusManager()
  {
    _status=new HashMap<Integer,DeedStatus>();
  }

  /**
   * Set character infos.
   * @param name Character name.
   * @param server Server name.
   */
  public void setCharacter(String name, String server)
  {
    _characterName=name;
    _server=server;
  }

  /**
   * Get the character name.
   * @return the character name.
   */
  public String getCharacterName()
  {
    return _characterName;
  }

  /**
   * Get the server name.
   * @return the server name.
   */
  public String getServer()
  {
    return _server;
  }

  /**
   * Get a deed status.
   * @param deedKey Key of the targeted deed.
   * @param createIfNecessary Indicates if the status shall be created if it
   * does not exist.
   * @return A deed status or <code>null</code>.
   */
  public DeedStatus get(String deedKey, boolean createIfNecessary)
  {
    DeedStatus ret=null;
    DeedDescription deed=DeedsManager.getInstance().getDeed(deedKey);
    if (deed!=null)
    {
      ret=get(deed,createIfNecessary);
    }
    return ret;
  }

  /**
   * Get a deed status.
   * @param deed Targeted deed.
   * @param createIfNecessary Indicates if the status shall be created if it
   * does not exist.
   * @return A deed status or <code>null</code>.
   */
  public DeedStatus get(DeedDescription deed, boolean createIfNecessary)
  {
    Integer key=Integer.valueOf(deed.getIdentifier());
    DeedStatus ret=_status.get(key);
    if ((ret==null) && (createIfNecessary))
    {
      ret=new DeedStatus(deed);
      _status.put(key,ret);
    }
    return ret;
  }

  /**
   * Cleanup un-needed entries.
   */
  public void cleanup()
  {
    List<Integer> keys=new ArrayList<Integer>(_status.keySet());
    for(Integer key : keys)
    {
      DeedStatus deedStatus=_status.get(key);
      if (deedStatus.isEmpty())
      {
        _status.remove(key);
      }
    }
  }

  /**
   * Get all managed deed statuses.
   * @return A list of deed statuses, ordered by deed key.
   */
  public List<DeedStatus> getAll()
  {
    List<Integer> deedKeys=new ArrayList<Integer>(_status.keySet());
    Collections.sort(deedKeys);
    List<DeedStatus> ret=new ArrayList<DeedStatus>();
    for(Integer deedKey : deedKeys)
    {
      ret.add(_status.get(deedKey));
    }
    return ret;
  }
}
