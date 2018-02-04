package delta.games.lotro.utils.events;

/**
 * Interface of a listener for events.
 * @param <T> Type of managed events
 * @author DAM
 */
public interface GenericEventsListener<T extends Event>
{
  /**
   * Called when an event occurred.
   * @param event Event data.
   */
  void eventOccurred(T event);
}
