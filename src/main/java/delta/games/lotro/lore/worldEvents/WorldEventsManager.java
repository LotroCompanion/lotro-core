package delta.games.lotro.lore.worldEvents;

import java.util.HashMap;
import java.util.Map;

/**
 * Manager for world events.
 * @author DAM
 */
public class WorldEventsManager
{
  private Map<Integer,WorldEvent> _mapByID;

  /**
   * Constructor.
   */
  public WorldEventsManager()
  {
    _mapByID=new HashMap<Integer,WorldEvent>();
  }

  /**
   * Get a world event using its identifier.
   * @param worldEventID Identifier of the world event to get.
   * @return A world event or <code>null</code>.
   */
  public WorldEvent getWorldEvent(int worldEventID)
  {
    return _mapByID.get(Integer.valueOf(worldEventID));
  }
}
