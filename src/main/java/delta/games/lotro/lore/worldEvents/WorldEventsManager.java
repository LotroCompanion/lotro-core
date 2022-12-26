package delta.games.lotro.lore.worldEvents;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.common.IdentifiableComparator;
import delta.games.lotro.config.DataFiles;
import delta.games.lotro.config.LotroCoreConfig;
import delta.games.lotro.lore.worldEvents.io.xml.WorldEventsXMLParser;

/**
 * Manager for world events.
 * @author DAM
 */
public class WorldEventsManager
{
  private static WorldEventsManager _instance;

  private Map<Integer,WorldEvent> _mapByID;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static final WorldEventsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new WorldEventsManager();
      _instance.resolve();
    }
    return _instance;
  }

  /**
   * Constructor.
   */
  private WorldEventsManager()
  {
    _mapByID=new HashMap<Integer,WorldEvent>();
    loadAll();
  }

  /**
   * Get all world events.
   * @return A list of world events, sorted by their identifier.
   */
  public List<WorldEvent> getAll()
  {
    List<WorldEvent> ret=new ArrayList<WorldEvent>(_mapByID.values());
    Collections.sort(ret,new IdentifiableComparator<WorldEvent>());
    return ret;
  }

  private void loadAll()
  {
    File worldEventsFile=LotroCoreConfig.getInstance().getFile(DataFiles.WORLD_EVENTS);
    List<WorldEvent> worldEvents=new WorldEventsXMLParser().parseXML(worldEventsFile);
    for(WorldEvent worldEvent : worldEvents)
    {
      int id=worldEvent.getIdentifier();
      _mapByID.put(Integer.valueOf(id),worldEvent);
    }
  }

  private void resolve()
  {
    WorldEventsResolver resolver=new WorldEventsResolver();
    for(WorldEvent event : _mapByID.values())
    {
      resolver.resolve(event);
    }
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
