package delta.games.lotro.lore.worldEvents;

import delta.games.lotro.lore.worldEvents.io.xml.WorldEventConditionsResolver;

/**
 * Resolver for world events.
 * <p>
 * Resolves all links to world events in world event conditions found in world events.
 * @author DAM
 */
public class WorldEventsResolver
{
  private WorldEventConditionsResolver _resolver;

  /**
   * Constructor.
   */
  public WorldEventsResolver()
  {
    _resolver=new WorldEventConditionsResolver();
  }

  /**
   * Resolve a single world event.
   * @param worldEvent World event to resolve.
   */
  public void resolve(WorldEvent worldEvent)
  {
    if (worldEvent instanceof ConditionWorldEvent)
    {
      ConditionWorldEvent we=(ConditionWorldEvent)worldEvent;
      AbstractWorldEventCondition condition=we.getCondition();
      _resolver.resolve(condition);
    }
    else if (worldEvent instanceof CountedWorldEvent)
    {
      CountedWorldEvent we=(CountedWorldEvent)worldEvent;
      for(AbstractWorldEventCondition condition : we.getConditions())
      {
        _resolver.resolve(condition);
      }
    }
  }
}
