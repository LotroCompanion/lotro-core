package delta.games.lotro.utils.events;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Global manager for events and associated listeners.
 * @author DAM
 */
public class EventsManager
{
  private static final Logger LOGGER=Logger.getLogger(EventsManager.class);

  private static Map<String,GenericListenersManager<? extends Event>> _listeners=new HashMap<String,GenericListenersManager<? extends Event>>();

  /**
   * Register a new listener.
   * @param clazz Class of managed events.
   * @param listener Listener to add.
   */
  public static <T extends Event> void addListener(Class<T> clazz, GenericEventsListener<T> listener)
  {
    getListenersManager(clazz).addListener(listener);
  }

  /**
   * Unregister a listener.
   * @param clazz Class of managed events.
   * @param listener Listener to remove.
   */
  public static <T extends Event> void removeListener(Class<T> clazz, GenericEventsListener<T> listener)
  {
    getListenersManager(clazz).removeListener(listener);
  }

  @SuppressWarnings("unchecked")
  private static <T extends Event> GenericListenersManager<T> getListenersManager(Class<T> clazz)
  {
    GenericListenersManager<? extends Event> listeners=_listeners.get(clazz.getName());
    if (listeners==null)
    {
      listeners=new GenericListenersManager<T>();
      _listeners.put(clazz.getName(),listeners); 
    }
    return (GenericListenersManager<T>)listeners;
  }

  /**
   * Invoke event.
   * @param event Event data.
   */
  public static <T extends Event> void invokeEvent(T event)
  {
    @SuppressWarnings("unchecked")
    GenericListenersManager<T> listeners=(GenericListenersManager<T>)getListenersManager(event.getClass());
    for(GenericEventsListener<T> listener : listeners)
    {
      try
      {
        listener.eventOccurred(event);
      }
      catch(Exception e)
      {
        LOGGER.error("Exception in an event listener",e);
      }
    }
  }
}
