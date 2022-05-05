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
}
