package delta.games.lotro.house;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.character.status.housing.House;
import delta.games.lotro.character.status.housing.HouseIdentifier;
import delta.games.lotro.house.events.HouseEvent;
import delta.games.lotro.house.events.HouseEventType;
import delta.games.lotro.utils.events.EventsManager;

/**
 * Manages all known houses.
 * @author DAM
 */
public final class HousesManager
{
  private static HousesManager _instance=new HousesManager();

  private HousesStorageManager _storage;
  private Map<HouseIdentifier,HouseEntry> _houses;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static HousesManager getInstance()
  {
    return _instance;
  }

  /**
   * Private constructor.
   */
  private HousesManager()
  {
    _storage=new HousesStorageManager();
    _houses=new HashMap<HouseIdentifier,HouseEntry>();
    for(HouseEntry entry : _storage.getAllHouses())
    {
      _houses.put(entry.getIdentifier(),entry);
    }
  }

  /**
   * Get a list of all managed houses, sorted by name.
   * @return a list of houses.
   */
  public List<HouseEntry> getAllHouses()
  {
    List<HouseEntry> houses=new ArrayList<HouseEntry>(_houses.values());
    return houses;
  }

  /**
   * Add/update a house.
   * @param house House.
   */
  public void addOrUpdateHouse(House house)
  {
    HouseEntry entry=_houses.get(house.getIdentifier());
    HouseEventType eventType;
    if (entry==null)
    {
      entry=new HouseEntry(house.getIdentifier());
      eventType=HouseEventType.HOUSE_ADDED;
    }
    else
    {
      eventType=HouseEventType.HOUSE_UPDATED;
    }
    boolean ok=_storage.saveHouse(house);
    if (ok)
    {
      entry.setHouse(house);
      _houses.put(house.getIdentifier(),entry);
      // Broadcast house creation event...
      HouseEvent event=new HouseEvent(eventType,entry);
      EventsManager.invokeEvent(event);
    }
  }

  /**
   * Remove a house.
   * @param house Targeted house.
   * @return <code>true</code> if successful, <code>false</code> otherwise.
   */
  public boolean removeHouse(HouseIdentifier house)
  {
    HouseEntry toRemove=_houses.remove(house);
    if (toRemove==null)
    {
      return true;
    }
    boolean ok=_storage.removeHouse(house);
    if (ok)
    {
      // Broadcast account deletion event...
      HouseEvent event=new HouseEvent(HouseEventType.HOUSE_REMOVED,toRemove);
      EventsManager.invokeEvent(event);
    }
    return ok;
  }
}
