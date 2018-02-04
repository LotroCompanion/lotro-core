package delta.games.lotro.character.events;

import delta.common.utils.misc.TypedProperties;
import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.utils.events.Event;

/**
 * Data for a character event.
 * @author DAM
 */
public class CharacterEvent extends Event
{
  private CharacterEventType _type;
  private CharacterFile _toonFile;
  private CharacterData _toonData;
  private TypedProperties _props;

  /**
   * Constructor.
   * @param type Event type.
   * @param toon Targeted toon.
   * @param data Targeted toon data.
   */
  public CharacterEvent(CharacterEventType type, CharacterFile toon, CharacterData data)
  {
    _type=type;
    _toonFile=toon;
    _toonData=data;
  }

  /**
   * Get the type of this event.
   * @return A character event type.
   */
  public CharacterEventType getType()
  {
    return _type;
  }

  /**
   * Get the targeted character file.
   * @return the targeted character file.
   */
  public CharacterFile getToonFile()
  {
    return _toonFile;
  }

  /**
   * Get the targeted character data.
   * @return the targeted character data (may be <code>null</code>).
   */
  public CharacterData getToonData()
  {
    return _toonData;
  }

  /**
   * Get the properties associated with this event.
   * @return some typed properties.
   */
  public TypedProperties getProperties()
  {
    if (_props==null)
    {
      _props=new TypedProperties();
    }
    return _props;
  }
}
