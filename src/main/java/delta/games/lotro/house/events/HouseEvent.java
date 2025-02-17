package delta.games.lotro.house.events;

import delta.games.lotro.house.HouseEntry;
import delta.games.lotro.utils.events.Event;

/**
 * Data for a house event.
 * @author DAM
 */
public class HouseEvent extends Event
{
  private HouseEventType _type;
  private HouseEntry _house;

  /**
   * Constructor.
   * @param type Event type.
   * @param house Targeted house.
   */
  public HouseEvent(HouseEventType type, HouseEntry house)
  {
    _type=type;
    _house=house;
  }

  /**
   * Get the type of this event.
   * @return An house event type.
   */
  public HouseEventType getType()
  {
    return _type;
  }

  /**
   * Get the targeted house.
   * @return the targeted house.
   */
  public HouseEntry getHouse()
  {
    return _house;
  }
}
