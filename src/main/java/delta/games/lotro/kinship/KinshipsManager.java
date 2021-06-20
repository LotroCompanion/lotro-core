package delta.games.lotro.kinship;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import delta.games.lotro.common.id.InternalGameId;
import delta.games.lotro.kinship.events.KinshipEvent;
import delta.games.lotro.kinship.events.KinshipEventType;
import delta.games.lotro.utils.events.EventsManager;

/**
 * Manages all known kinships.
 * @author DAM
 */
public final class KinshipsManager
{
  private static KinshipsManager _instance=new KinshipsManager();

  private KinshipsStorageManager _storage;
  private List<Kinship> _kinships;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static KinshipsManager getInstance()
  {
    return _instance;
  }

  /**
   * Private constructor.
   */
  private KinshipsManager()
  {
    _storage=new KinshipsStorageManager();
    _kinships=new ArrayList<Kinship>();
    _kinships.addAll(_storage.getAllKinships());
  }

  /**
   * Get a list of all managed kinships.
   * @return a list of kinships.
   */
  public List<Kinship> getAllKinships()
  {
    List<Kinship> kinships=new ArrayList<Kinship>(_kinships);
    return kinships;
  }

  /**
   * Get a kinship by name.
   * @param kinshipName Kinship name.
   * @return A kinship or <code>null</code> if not found.
   */
  public Kinship getKinshipByName(String kinshipName)
  {
    for(Kinship kinship : _kinships)
    {
      String name=kinship.getName();
      if (Objects.equals(name,kinshipName))
      {
        return kinship;
      }
    }
    return null;
  }

  /**
   * Get a kinship by ID.
   * @param kinshipID Kinship ID.
   * @return A kinship or <code>null</code> if not found.
   */
  public Kinship getKinshipByID(long kinshipID)
  {
    for(Kinship kinship : _kinships)
    {
      Long currentKinshipID=kinship.getID();
      if ((currentKinshipID!=null) && (InternalGameId.lightMatch(currentKinshipID.longValue(),kinshipID)))
      {
        return kinship;
      }
    }
    return null;
  }

  /**
   * Add a new kinship.
   * @param summary Kinship summary.
   * @return A kinship or <code>null</code> if an error occurs.
   */
  public Kinship addKinship(KinshipSummary summary)
  {
    Kinship kinship=_storage.newKinship(summary);
    if (kinship!=null)
    {
      _kinships.add(kinship);
      // Broadcast kinship creation event...
      KinshipEvent event=new KinshipEvent(KinshipEventType.KINSHIP_ADDED,kinship);
      EventsManager.invokeEvent(event);
    }
    return kinship;
  }

  /**
   * Remove a kinship.
   * @param kinship Targeted kinship.
   * @return <code>true</code> if successful, <code>false</code> otherwise.
   */
  public boolean removeKinship(Kinship kinship)
  {
    boolean ret=_kinships.remove(kinship);
    if (ret)
    {
      _storage.removeKinship(kinship);
      // Broadcast kinship deletion event...
      KinshipEvent event=new KinshipEvent(KinshipEventType.KINSHIP_REMOVED,kinship);
      EventsManager.invokeEvent(event);
    }
    return ret;
  }
}
