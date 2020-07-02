package delta.games.lotro.character.classes.traitTree.setup.events;

import delta.games.lotro.character.classes.traitTree.setup.TraitTreeSetup;
import delta.games.lotro.utils.events.Event;

/**
 * Data for a trait tree setup event.
 * @author DAM
 */
public class TraitTreeSetupEvent extends Event
{
  private TraitTreeSetupEventType _type;
  private TraitTreeSetup _setup;

  /**
   * Constructor.
   * @param type Event type.
   * @param data Targeted data.
   */
  public TraitTreeSetupEvent(TraitTreeSetupEventType type, TraitTreeSetup data)
  {
    _type=type;
    _setup=data;
  }

  /**
   * Get the type of this event.
   * @return A character event type.
   */
  public TraitTreeSetupEventType getType()
  {
    return _type;
  }

  /**
   * Get the targeted trait tree setup.
   * @return the targeted trait tree setup.
   */
  public TraitTreeSetup getToonFile()
  {
    return _setup;
  }
}
