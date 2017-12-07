package delta.games.lotro.character.events;

/**
 * Interface of a listener for character events.
 * @author DAM
 */
public interface CharacterEventListener
{
  /**
   * Called when an event occurred.
   * @param type Type of event.
   * @param event Event data.
   */
  void eventOccurred(CharacterEventType type, CharacterEvent event);
}
