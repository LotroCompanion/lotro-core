package delta.games.lotro.character.events;

/**
 * Type of character events.
 * @author DAM
 */
public enum CharacterEventType
{
  /**
   * A new toon was added.
   */
  CHARACTER_ADDED,
  /**
   * The summary of a toon was updated.
   */
  CHARACTER_SUMMARY_UPDATED,
  /**
   * The stash of a toon was updated.
   */
  CHARACTER_STASH_UPDATED,
  /**
   * A toon was removed.
   */
  CHARACTER_REMOVED,
  /**
   * A new character data was added to an existing toon.
   */
  CHARACTER_DATA_ADDED,
  /**
   * A new character data was removed from an existing toon.
   */
  CHARACTER_DATA_REMOVED,
  /**
   * A character data was updated.
   */
  CHARACTER_DATA_UPDATED
}
