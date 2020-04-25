package delta.games.lotro.character;

import delta.games.lotro.common.CharacterClass;
import delta.games.lotro.common.Race;

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
  CharacterClass getCharacterClass();

  /**
   * Get the character's race.
   * @return the character's race.
   */
  Race getRace();

  /**
   * Get the character's level.
   * @return the character's level.
   */
  int getLevel();
}
