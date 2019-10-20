package delta.games.lotro.common.global;

import delta.games.lotro.character.stats.ratings.RatingsMgr;

/**
 * Facade to access to combat system data.
 * @author DAM
 */
public class CombatData
{
  private RatingsMgr _ratingsMgr;

  /**
   * Constructor.
   */
  public CombatData()
  {
    _ratingsMgr=new RatingsMgr();
  }

  /**
   * Get the ratings manager
   * @return the ratings manager.
   */
  public RatingsMgr getRatingsMgr()
  {
    return _ratingsMgr;
  }
}
