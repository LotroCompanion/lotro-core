package delta.games.lotro.character.events;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.utils.TypedProperties;

/**
 * Data for a character event.
 * @author DAM
 */
public class CharacterEvent
{
  private CharacterFile _toonFile;
  private CharacterData _toonData;
  private TypedProperties _props;

  /**
   * Constructor.
   * @param toon Targeted toon.
   * @param data Targeted toon data.
   */
  public CharacterEvent(CharacterFile toon, CharacterData data)
  {
    _toonFile=toon;
    _toonData=data;
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
