package delta.games.lotro.character.events;

import org.apache.log4j.Logger;

import delta.common.utils.ListenersManager;
import delta.games.lotro.utils.LotroLoggers;

/**
 * Global manager for character events and associated listeners.
 * @author DAM
 */
public class CharacterEventsManager
{
  private static final Logger _logger=LotroLoggers.getLotroLogger();

  private static ListenersManager<CharacterEventListener> _listeners=new ListenersManager<CharacterEventListener>();

  /**
   * Register a new listener.
   * @param listener Listener to add.
   */
  public static void addListener(CharacterEventListener listener)
  {
    _listeners.addListener(listener);
  }

  /**
   * Unregister a listener.
   * @param listener Listener to remove.
   */
  public static void removeListener(CharacterEventListener listener)
  {
    _listeners.removeListener(listener);
  }

  /**
   * Invoke event.
   * @param type Event type.
   * @param event Event data.
   */
  public static void invokeEvent(CharacterEventType type, CharacterEvent event)
  {
    for(CharacterEventListener listener : _listeners)
    {
      try
      {
        listener.eventOccured(type,event);
      }
      catch(Throwable t)
      {
        _logger.error("Error in character event callback",t);
      }
    }
  }
}
