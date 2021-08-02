package delta.games.lotro.character.status.levelling;

import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.utils.MultipleToonsStats;

/**
 * Levelling statistics for several toons.
 * @author DAM
 */
public class MultipleToonsLevellingStats extends MultipleToonsStats<LevelHistory>
{
  @Override
  protected LevelHistory loadToonStats(CharacterFile toon)
  {
    LevelHistory history=toon.getLevelHistory();
    return history;
  }
}
