package delta.games.lotro.common;

import java.util.Comparator;

/**
 * Comparator for challenge levels.
 * @author DAM
 */
public class ChallengeLevelComparator implements Comparator<ChallengeLevel>
{
  @Override
  public int compare(ChallengeLevel c1, ChallengeLevel c2)
  {
    return Integer.compare(c1.getCode(),c2.getCode());
  }
}
