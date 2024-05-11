package delta.games.lotro.character.status.titles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.lore.titles.TitleDescription;
import delta.games.lotro.lore.titles.TitlesManager;

/**
 * Storage for all acquired titles for a single character.
 * @author DAM
 */
public class TitlesStatusManager
{
  private Map<Integer,TitleStatus> _status;

  /**
   * Constructor.
   */
  public TitlesStatusManager()
  {
    _status=new HashMap<Integer,TitleStatus>();
  }

  /**
   * Remove all known titles.
   */
  public void clear()
  {
    _status.clear();
  }

  /**
   * Get the the total number of completions.
   * @return A completions count.
   */
  public int getTitlesCount()
  {
    int nb=0;
    for(TitleStatus status : _status.values())
    {
      if (status.getState()==TitleState.ACQUIRED)
      {
        nb++;
      }
    }
    return nb;
  }

  /**
   * Get the status of a title.
   * @param title Targeted title.
   * @param createIfNecessary Indicates if the status shall be created if it
   * does not exist.
   * @return A title status or <code>null</code>.
   */
  public TitleStatus get(TitleDescription title, boolean createIfNecessary)
  {
    Integer key=Integer.valueOf(title.getIdentifier());
    TitleStatus ret=_status.get(key);
    if ((ret==null) && (createIfNecessary))
    {
      ret=new TitleStatus(title);
      _status.put(key,ret);
    }
    return ret;
  }

  /**
   * Get all managed statuses.
   * @return A list of statuses, ordered by title ID.
   */
  public List<TitleStatus> getAll()
  {
    List<Integer> ids=new ArrayList<Integer>(_status.keySet());
    Collections.sort(ids);
    List<TitleStatus> ret=new ArrayList<TitleStatus>();
    for(Integer id : ids)
    {
      ret.add(_status.get(id));
    }
    return ret;
  }

  /**
   * Mark the obsolete titles.
   */
  public void markObsoleteTitles()
  {
    TitlesManager titlesMgr=TitlesManager.getInstance();
    List<TitleDescription> obsoleteTitles=new ArrayList<TitleDescription>();
    for(TitleStatus status : _status.values())
    {
      if (status.getState()==TitleState.ACQUIRED)
      {
        TitleDescription title=status.getTitle();
        String exclusionGroup=title.getExclusionGroup();
        if (exclusionGroup!=null)
        {
          List<TitleDescription> obsoletedTitles=titlesMgr.getObsoletedTitles(title);
          for(TitleDescription obsoletedTitle : obsoletedTitles)
          {
            obsoleteTitles.add(obsoletedTitle);
          }
        }
      }
    }
    // Perform obsolete mark
    for(TitleDescription title : obsoleteTitles)
    {
      get(title,true).setState(TitleState.SUPERSEDED);
    }
  }
}
