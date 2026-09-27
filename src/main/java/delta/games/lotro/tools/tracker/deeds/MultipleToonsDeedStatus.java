package delta.games.lotro.tools.tracker.deeds;

import delta.games.lotro.character.CharacterFile;
import delta.games.lotro.character.status.achievables.AchievablesStatusManager;
import delta.games.lotro.character.status.achievables.io.DeedsStatusIo;
import delta.games.lotro.character.utils.MultipleToonsStats;

/**
 * Deed status for several characters.
 * @author DAM
 */
public class MultipleToonsDeedStatus extends MultipleToonsStats<AchievablesStatusManager>
{
  @Override
  protected AchievablesStatusManager loadToonStats(CharacterFile toon)
  {
    AchievablesStatusManager mgr=DeedsStatusIo.load(toon);
    return mgr;
  }
}
