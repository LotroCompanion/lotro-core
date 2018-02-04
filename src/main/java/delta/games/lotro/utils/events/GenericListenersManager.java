package delta.games.lotro.utils.events;

import delta.common.utils.ListenersManager;

/**
 * Manager for generic event listeners.
 * @param <T> Type of managed events
 * @author DAM
 */
public class GenericListenersManager<T extends Event> extends ListenersManager<GenericEventsListener<T>>
{
  // Nothing
}
