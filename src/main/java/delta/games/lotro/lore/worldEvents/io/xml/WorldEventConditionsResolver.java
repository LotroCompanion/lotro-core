package delta.games.lotro.lore.worldEvents.io.xml;

import delta.games.lotro.lore.worldEvents.AbstractWorldEventCondition;
import delta.games.lotro.lore.worldEvents.CompoundWorldEventCondition;
import delta.games.lotro.lore.worldEvents.SimpleWorldEventCondition;
import delta.games.lotro.lore.worldEvents.WorldEvent;
import delta.games.lotro.lore.worldEvents.WorldEventConditionsRenderer;
import delta.games.lotro.lore.worldEvents.WorldEventsManager;
import delta.games.lotro.utils.Proxy;

/**
 * Resolver for world event conditions.
 * <p>
 * Resolves all links to world events in world event conditions.
 * @author DAM
 */
public class WorldEventConditionsResolver
{
  private WorldEventConditionsRenderer _renderer=new WorldEventConditionsRenderer();

  /**
   * Resolve a single world event condition.
   * @param condition Condition to resolve.
   */
  public void resolve(AbstractWorldEventCondition condition)
  {
    if (condition instanceof SimpleWorldEventCondition)
    {
      resolveCondition((SimpleWorldEventCondition)condition);
    }
    else if (condition instanceof CompoundWorldEventCondition)
    {
      CompoundWorldEventCondition compoundWEC=(CompoundWorldEventCondition)condition;
      for(AbstractWorldEventCondition childCondition : compoundWEC.getItems())
      {
        resolve(childCondition);
      }
    }
  }

  private void resolveCondition(SimpleWorldEventCondition condition)
  {
    Proxy<WorldEvent> targetWorldEvent=condition.getWorldEvent();
    resolveWorldEventProxy(targetWorldEvent);
    Proxy<WorldEvent> compareToWorldEvent=condition.getCompareToWorldEvent();
    resolveWorldEventProxy(compareToWorldEvent);
    String label=_renderer.renderSimpleWorldEventCondition(condition);
    condition.setLabel(label);
  }

  private void resolveWorldEventProxy(Proxy<WorldEvent> proxy)
  {
    if (proxy==null)
    {
      return;
    }
    WorldEventsManager mgr=WorldEventsManager.getInstance();
    WorldEvent worldEvent=mgr.getWorldEvent(proxy.getId());
    if (worldEvent!=null)
    {
      proxy.setObject(worldEvent);
      proxy.setName(worldEvent.getPropertyName());
    }
  }
}
