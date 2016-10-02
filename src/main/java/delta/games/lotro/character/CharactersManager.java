package delta.games.lotro.character;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages all known toons.
 * @author DAM
 */
public class CharactersManager
{
  private static CharactersManager _instance=new CharactersManager();

  private CharactersStorageManager _storage;
  private List<CharacterFile> _toons;

  private PropertyChangeSupport _listeners;

  /**
   * Constant for "toon added" event.
   */
  public static final String TOON_ADDED = "TOON_ADDED";

  /**
   * Constant for "toon updated" event.
   */
  public static final String TOON_UPDATED = "TOON_UPDATED";

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static CharactersManager getInstance()
  {
    return _instance;
  }

  /**
   * Private constructor.
   */
  private CharactersManager()
  {
    _storage=new CharactersStorageManager();
    _toons=new ArrayList<CharacterFile>();
    _toons.addAll(_storage.getAllToons());
    _listeners=new PropertyChangeSupport(this);
  }

  /**
   * Add an event listener.
   * @param listener Event listener to add.
   */
  public void addPropertyChangeListener(PropertyChangeListener listener)
  {
    _listeners.addPropertyChangeListener(listener);
  }

  /**
   * Remove an event listener.
   * @param listener Event listener to remove.
   */
  public void removePropertyChangeListener(PropertyChangeListener listener)
  {
    _listeners.removePropertyChangeListener(listener);
  }

  /**
   * Get a list of all managed toons, sorted by name.
   * @return a list of toons.
   */
  public List<CharacterFile> getAllToons()
  {
    List<CharacterFile> toons=new ArrayList<CharacterFile>(_toons);
    // TODO sort by server/toon name?
    return toons;
  }

  /**
   * Get a toon using its identifier.
   * @param toonID Identifier of the toon to get.
   * @return A toon or <code>null</code> if not found.
   */
  public CharacterFile getToonById(String toonID)
  {
    int index=toonID.indexOf("#");
    String serverName=toonID.substring(0,index);
    String toonName=toonID.substring(index+1);
    CharacterFile ret=getToonById(serverName,toonName);
    return ret;
  }

  /**
   * Get a toon by name.
   * @param serverName Server name.
   * @param toonName Toon name.
   * @return A toon or <code>null</code> if not found.
   */
  public CharacterFile getToonById(String serverName, String toonName)
  {
    for(CharacterFile toon : _toons)
    {
      CharacterSummary summary=toon.getSummary();
      if (summary!=null)
      {
        if ((serverName.equals(summary.getServer())) && (toonName.equals(summary.getName())))
        {
          return toon;
        }
      }
    }
    return null;
  }

  /**
   * Add a new toon.
   * @param info Character description.
   * @return A character file or <code>null</code> if an error occurs.
   */
  public CharacterFile addToon(CharacterData info)
  {
    CharacterFile file=_storage.newToon(info);
    if (file!=null)
    {
      _toons.add(file);
      // Broadcast toon creation event...
      _listeners.firePropertyChange(TOON_ADDED,false,true);
    }
    return file;
  }

  /**
   * Broadcast a toon update.
   * @param toon Involved toon.
   */
  public void broadcastToonUpdate(CharacterFile toon)
  {
    _listeners.firePropertyChange(TOON_UPDATED,null,toon);
  }
}
