package delta.games.lotro.character;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.character.events.CharacterEvent;
import delta.games.lotro.character.events.CharacterEventType;
import delta.games.lotro.common.id.InternalGameId;
import delta.games.lotro.utils.events.EventsManager;

/**
 * Manages all known toons.
 * @author DAM
 */
public final class CharactersManager
{
  private static CharactersManager _instance=new CharactersManager();

  private CharactersStorageManager _storage;
  private List<CharacterFile> _toons;

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
  }

  /**
   * Get a list of all managed toons, sorted by name.
   * @return a list of toons.
   */
  public List<CharacterFile> getAllToons()
  {
    List<CharacterFile> toons=new ArrayList<CharacterFile>(_toons);
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
   * Get a toon using its internal identifier.
   * @param id Identifier to use.
   * @return A toon or <code>null</code> if not found.
   */
  public CharacterFile getToonById(InternalGameId id)
  {
    if (id==null)
    {
      return null;
    }
    for(CharacterFile toon : _toons)
    {
      CharacterSummary summary=toon.getSummary();
      if (summary!=null)
      {
        if (id.equals(summary.getId()))
        {
          return toon;
        }
      }
    }
    return null;
  }

  /**
   * Add a new toon.
   * @param summary Character summary.
   * @return A character file or <code>null</code> if an error occurs.
   */
  public CharacterFile addToon(CharacterSummary summary)
  {
    CharacterFile file=_storage.newToon(summary);
    if (file!=null)
    {
      _toons.add(file);
      // Broadcast toon creation event...
      CharacterEvent event=new CharacterEvent(CharacterEventType.CHARACTER_ADDED,file,null);
      EventsManager.invokeEvent(event);
    }
    return file;
  }

  /**
   * Remove a character.
   * @param file Character file.
   * @return <code>true</code> if successful, <code>false</code> otherwise.
   */
  public boolean removeToon(CharacterFile file)
  {
    boolean ret=_toons.remove(file);
    if (ret)
    {
      _storage.removeToon(file);
      // Broadcast toon deletion event...
      CharacterEvent event=new CharacterEvent(CharacterEventType.CHARACTER_REMOVED,file,null);
      EventsManager.invokeEvent(event);
    }
    return ret;
  }
}
