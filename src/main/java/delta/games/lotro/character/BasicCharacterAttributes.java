package delta.games.lotro.character;

import delta.games.lotro.character.classes.ClassDescription;
import delta.games.lotro.character.races.RaceDescription;

/**
 * Provides access to basic character attributes.
 * @author DAM
 */
public interface BasicCharacterAttributes
{
  /**
   * Get the character's class.
   * @return the character's class.
   */
  ClassDescription getCharacterClass();

  /**
   * Get the character's race.
   * @return the character's race.
   */
  RaceDescription getRace();

  /**
   * Get the character's level.
   * @return the character's level.
   */
  int getLevel();
}
