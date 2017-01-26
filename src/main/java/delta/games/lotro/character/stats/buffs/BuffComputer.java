package delta.games.lotro.character.stats.buffs;

import delta.games.lotro.character.CharacterData;
import delta.games.lotro.character.stats.BasicStatsSet;

/**
 * An object that can compute the stats contribution of a buff.
 * @author DAM
 */
public interface BuffComputer
{
  /**
   * Compute the stats contribution of a buff on a character.
   * @param character Targeted character.
   * @param buff Buff to use.
   * @return A stats contribution.
   */
  BasicStatsSet getStats(CharacterData character, BuffInstance buff);
}
