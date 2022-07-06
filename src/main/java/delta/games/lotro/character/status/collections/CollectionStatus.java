package delta.games.lotro.character.status.collections;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.skills.SkillDescription;
import delta.games.lotro.character.skills.SkillsManager;
import delta.games.lotro.character.status.skills.SkillStatus;
import delta.games.lotro.character.status.skills.SkillsStatusManager;
import delta.games.lotro.lore.collections.Collectable;
import delta.games.lotro.lore.collections.CollectionDescription;

/**
 * Status of a single collection for a single character.
 * @author DAM
 */
public class CollectionStatus
{
  private CollectionDescription _collection;
  private List<Collectable> _completed;
  private List<Collectable> _all;

  /**
   * Constructor.
   * @param collection Associated collection.
   */
  public CollectionStatus(CollectionDescription collection)
  {
    _collection=collection;
    _completed=new ArrayList<Collectable>();
    _all=new ArrayList<Collectable>(collection.getElements());
  }

  /**
   * Get the associated collection.
   * @return the associated collection.
   */
  public CollectionDescription getCollection()
  {
    return  _collection;
  }

  /**
   * Assess the status of the managed collection, using the provided skills status.
   * @param statusMgr Skills status manager.
   */
  public void assess(SkillsStatusManager statusMgr)
  {
    _completed.clear();
    SkillsManager skillsMgr=SkillsManager.getInstance();
    for(Collectable element : _all)
    {
      int skillID=element.getIdentifier();
      SkillDescription skill=skillsMgr.getSkill(skillID);
      if (skill!=null)
      {
        SkillStatus skillStatus=statusMgr.get(skill,false);
        if ((skillStatus!=null) && (skillStatus.isAvailable()))
        {
          _completed.add(element);
        }
      }
    }
  }

  /**
   * Indicates if the managed collection is complete or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isComplete()
  {
    return _completed.size()==_all.size();
  }

  /**
   * Get the total number of elements in the managed collection.
   * @return A count.
   */
  public int getTotalCount()
  {
    return _all.size();
  }

  /**
   * Get the number of completed elements in the managed collection.
   * @return A count.
   */
  public int getCompletedCount()
  {
    return _completed.size();
  }

  /**
   * Get the completed elements.
   * @return A possibly empty but never <code>null</code> list of collection elements.
   */
  public List<Collectable> getCompletedElements()
  {
    return _completed;
  }

  /**
   * Indicates if the given collection element is completed or not.
   * @param element Element to use.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isCompleted(Collectable element)
  {
    return _completed.contains(element);
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Collection ").append(_collection.getName()).append(": ");
    if (isComplete())
    {
      sb.append("completed");
    }
    else
    {
      int nbCompleted=getCompletedCount();
      if (nbCompleted==0)
      {
        sb.append("not started");
      }
      else
      {
        int nbTotal=getTotalCount();
        sb.append(nbCompleted).append('/').append(nbTotal);
      }
    }
    return sb.toString();
  }
}
