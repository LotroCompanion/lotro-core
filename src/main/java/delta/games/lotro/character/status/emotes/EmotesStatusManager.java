package delta.games.lotro.character.status.emotes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.lore.emotes.EmoteDescription;

/**
 * Storage for all emote status data for a single character.
 * @author DAM
 */
public class EmotesStatusManager
{
  private Map<Integer,EmoteStatus> _status;

  /**
   * Constructor.
   */
  public EmotesStatusManager()
  {
    _status=new HashMap<Integer,EmoteStatus>();
  }

  /**
   * Clear data.
   */
  public void clear()
  {
    _status.clear();
  }

  /**
   * Get the status of an emote.
   * @param emote Targeted emote.
   * @param createIfNecessary Indicates if the status shall be created if it
   * does not exist.
   * @return An emote status or <code>null</code>.
   */
  public EmoteStatus get(EmoteDescription emote, boolean createIfNecessary)
  {
    Integer key=Integer.valueOf(emote.getIdentifier());
    EmoteStatus ret=_status.get(key);
    if ((ret==null) && (createIfNecessary))
    {
      ret=new EmoteStatus(emote);
      _status.put(key,ret);
    }
    return ret;
  }

  /**
   * Get all managed statuses.
   * @return A list of statuses, ordered by title ID.
   */
  public List<EmoteStatus> getAll()
  {
    List<Integer> ids=new ArrayList<Integer>(_status.keySet());
    Collections.sort(ids);
    List<EmoteStatus> ret=new ArrayList<EmoteStatus>();
    for(Integer id : ids)
    {
      ret.add(_status.get(id));
    }
    return ret;
  }
}
