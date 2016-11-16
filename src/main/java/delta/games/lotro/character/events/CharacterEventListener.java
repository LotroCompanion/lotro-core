package delta.games.lotro.character.events;

/**
 * Interface of a listener for character events.
 * @author DAM
 */
public interface CharacterEventListener
{
  /**
   * Called when an event occured.
   * @param type Type of event.
   * @param event Event data.
   */
  void eventOccured(CharacterEventType type, CharacterEvent event);
}
